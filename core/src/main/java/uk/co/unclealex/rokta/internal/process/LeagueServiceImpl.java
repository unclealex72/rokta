package uk.co.unclealex.rokta.internal.process;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.ComparatorUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.comparators.ComparableComparator;
import org.apache.commons.collections15.comparators.ComparatorChain;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.internal.dao.GameDao;
import uk.co.unclealex.rokta.internal.quotient.DatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.DayDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.InstantDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.MonthDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.QuotientPredicate;
import uk.co.unclealex.rokta.internal.quotient.WeekDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.YearDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.util.DateUtil;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.model.Game;
import uk.co.unclealex.rokta.pub.model.Person;
import uk.co.unclealex.rokta.pub.model.Round;
import uk.co.unclealex.rokta.pub.views.Delta;
import uk.co.unclealex.rokta.pub.views.InfiniteInteger;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.LeagueRow;
import uk.co.unclealex.rokta.pub.views.LeaguesHolder;

@Service
@Transactional
public class LeagueServiceImpl implements LeagueService {

	private GameDao i_gameDao;
	private DateUtil i_dateUtil;
	private PersonService i_personService;
	private Comparator<LeagueRow> i_leagueRowComparator;
	
	@PostConstruct
	public void initialise() {
		setLeagueRowComparator(createLeagueRowComparator());
	}
	
	@Override
	public LeaguesHolder generateLeague(GameFilter gameFilter, int maximumLeagues, DateTime now) {
		SortedSet<Game> games = getGameDao().getGamesByFilter(gameFilter);
		SortedMap<Game, League> leaguesForAllGames = generateLeagues(games);
		decorateWithDeltas(leaguesForAllGames);
		decorateWithExemptionAndAbsence(leaguesForAllGames, now);
		return filterGranularity(leaguesForAllGames, maximumLeagues);
	}
	
	protected SortedMap<Game, League> generateLeagues(SortedSet<Game> games) {
		int totalGames = 0;
		int totalPlayers = 0;
		Map<Person, LeagueRow> rowMap = new HashMap<Person, LeagueRow>();
		SortedMap<Game, League> leaguesByGame = new TreeMap<Game, League>();
		for (Game game : games) {
			totalGames++;
			Person loser = game.getLoser();
			SortedSet<Person> participants = game.getParticipants();
			int participantCount = participants.size();
			totalPlayers += participantCount;
			for (Person participant : participants) {
				if (!rowMap.containsKey(participant)) {
					LeagueRow newLeagueRow = new LeagueRow();
					newLeagueRow.setPerson(participant);
					newLeagueRow.setDelta(Delta.NONE);
					rowMap.put(participant, newLeagueRow);
				}
				LeagueRow leagueRow = rowMap.get(participant);
				leagueRow.setTotalParticipants(leagueRow.getTotalParticipants() + participantCount);
				int playsForPersonInGame = countPlaysForPersonInGame(participant, game);
				if (participant.equals(loser)) {
					leagueRow.setGamesLost(leagueRow.getGamesLost() + 1);
					leagueRow.setRoundsPlayedInLostGames(leagueRow.getRoundsPlayedInLostGames() + playsForPersonInGame);
				}
				else {
					leagueRow.setRoundsPlayedInWonGames(leagueRow.getRoundsPlayedInWonGames() + playsForPersonInGame);					
					leagueRow.setGamesWon(leagueRow.getGamesWon() + 1);
				}
			}
			League league = createLeague(rowMap.values(), totalGames, totalPlayers);
			leaguesByGame.put(game, league);
			
		}		
		return leaguesByGame;
	}

	protected int countPlaysForPersonInGame(Person person, Game game) {
		int cnt = 0;
		for (Round round : game.getRounds()) {
			if (round.getParticipants().contains(person)) {
				cnt++;
			}
		}
		return cnt;
	}

	protected League createLeague(Collection<LeagueRow> rows, int totalGames, int totalPlayers) {
		League league = new League();
		league.setCurrent(false);
		league.setTotalGames(totalGames);
		league.setTotalPlayers(totalPlayers);

		SortedSet<LeagueRow> sortedRows = new TreeSet<LeagueRow>(getLeagueRowComparator());
		for (LeagueRow leagueRow : rows) {
			LeagueRow copy = leagueRow.copy();
			copy.setLeague(league);
			sortedRows.add(copy);
		}		
		league.setRows(sortedRows);
		return league;
	}
	
	protected void decorateWithDeltas(SortedMap<Game, League> leaguesByGame) {
		// We only decorate the last league and only if there is something to compare it against.
		if (leaguesByGame.size() < 2) {
			return;
		}
		
		Game lastGame = leaguesByGame.lastKey();
		Game penultimateGame = leaguesByGame.headMap(lastGame).lastKey();
		Map<Person,Integer> oldPositions;
		Map<Person,Integer> newPositions = new HashMap<Person, Integer>();
		League[] leagues = new League[] { leaguesByGame.get(penultimateGame), leaguesByGame.get(lastGame) };
		for (League league : leagues) {
			oldPositions = newPositions;
			newPositions = new HashMap<Person, Integer>();
			for (
					IndexingIterator<LeagueRow> iter = new IndexingIterator<LeagueRow>(league.getRows().iterator());
					iter.hasNext(); ) {
				LeagueRow row = iter.next();
				newPositions.put(row.getPerson(), iter.getIndex());
			}
			
			for (LeagueRow row : league.getRows()) {
				Person person = row.getPerson();
				Integer currentPosition = newPositions.get(person);
				Integer previousPosition = oldPositions.get(person);
				if (previousPosition != null) {
					if (currentPosition < previousPosition) {
						row.setDelta(Delta.UP);
					}
					if (previousPosition < currentPosition) {
						row.setDelta(Delta.DOWN);
					}
				}
			}
		}		
	}	
	
	protected void decorateWithExemptionAndAbsence(SortedMap<Game, League> leaguesByGame, DateTime currentDate) {
		if (leaguesByGame.isEmpty()) {
			return;
		}
		Game lastGamePlayed = getGameDao().getLastGame();
		Game lastLeagueGame = leaguesByGame.lastKey();
		// Exemptions only occur if the last game played was today.
		// If the last game was played today we can also see who hasn't played today
		if (lastLeagueGame.equals(lastGamePlayed)) {
			League league = leaguesByGame.get(lastLeagueGame);
			league.setCurrent(true);
			if (getDateUtil().areSameDay(lastGamePlayed.getDatePlayed(), currentDate)) {
				Person exempt = getPersonService().getExemptPlayer(currentDate);
				for (LeagueRow leagueRow : league.getRows()) {
					Person player = leagueRow.getPerson();
					leagueRow.setExempt(player.equals(exempt));
					leagueRow.setPlayingToday(getPersonService().currentlyPlaying(player, currentDate));
				}
			}
			LeagueRow previousRow = null;
			for (LeagueRow currentRow : league.getRows()) {
				if (previousRow != null) {
					currentRow.setGap(calculateGap(previousRow, currentRow));
				}
				previousRow = currentRow;
			}
		}		
	}
	
	protected InfiniteInteger calculateGap(LeagueRow higherRow, LeagueRow lowerRow) {
		int playedTop = higherRow.getGamesPlayed();
		int lostTop = higherRow.getGamesLost();
		boolean exemptTop = higherRow.isExempt();
		boolean playingTop = higherRow.isPlayingToday();
		int playedBottom = lowerRow.getGamesPlayed();
		int lostBottom = lowerRow.getGamesLost();
		boolean exemptBottom = lowerRow.isExempt();
		boolean playingBottom = lowerRow.isPlayingToday();
		
		InfiniteInteger gap;
		if (!playingTop && !playingBottom) {
			playingTop = playingBottom = true;
		}
		if (!playingTop && lostTop == 0) {
			gap = InfiniteInteger.INFINITY;
		}
		else {
			gap =
				new InfiniteInteger(
						race(playingTop, playedTop, lostTop, exemptTop, playingBottom, playedBottom, lostBottom, exemptBottom));
		}
		return gap;
	}
		
	protected int race(
			boolean playingTop, int playedTop, int lostTop, boolean exemptTop,
			boolean playingBottom, int playedBottom, int lostBottom, boolean exemptBottom) {
		int gap = 0;
		do {
			if (playingTop) {
				if (!exemptTop) {
					playedTop++;
					lostTop++;
				}
				exemptTop = !exemptTop;
			}
			if (playingBottom) {
				if (!exemptBottom) {
					playedBottom++;
				}
				exemptBottom = false;
			}
			gap++;
		} while (lostBottom * playedTop > lostTop * playedBottom);
		return gap;
	}

	protected LeaguesHolder filterGranularity(SortedMap<Game, League> leaguesForAllGames, int maximumLeagues) {
		// We need to reverse the order of the games so we find the last game of a quotient.
		SortedSet<Game> reversedGames = new TreeSet<Game>(ComparatorUtils.reversedComparator(ComparableComparator.getInstance()));
		reversedGames.addAll(leaguesForAllGames.keySet());
		List<DatePlayedQuotientTransformer> quotientTransformers =
			Arrays.asList(new DatePlayedQuotientTransformer[] {
				new InstantDatePlayedQuotientTransformer(),
				new DayDatePlayedQuotientTransformer(),
				new WeekDatePlayedQuotientTransformer(),
				new MonthDatePlayedQuotientTransformer(),
				new YearDatePlayedQuotientTransformer()
			});
		DatePlayedQuotientTransformer quotientTransformer = null;
		SortedSet<Game> filteredGames = null;
		
		// Find which quotient to use.
		for (Iterator<DatePlayedQuotientTransformer> iter = quotientTransformers.iterator(); filteredGames == null; ) {
			quotientTransformer = iter.next();
			SortedSet<Game> filteredGamesForQuotient = new TreeSet<Game>();
			Predicate<Game> quotientPredicate = new QuotientPredicate<Game, Long>(quotientTransformer);
			CollectionUtils.select(reversedGames, quotientPredicate, filteredGamesForQuotient);
			if (!iter.hasNext() || filteredGamesForQuotient.size() <= maximumLeagues) {
				filteredGames = filteredGamesForQuotient;
			}
		}
		
		// Replace the last filtered game with the actual last game
		filteredGames.remove(filteredGames.last());
		filteredGames.add(reversedGames.first());
		
		SortedMap<Game, League> leaguesByFilteredGames = new TreeMap<Game, League>();
		for (Game game : filteredGames) {
			leaguesByFilteredGames.put(game, leaguesForAllGames.get(game));
		}
		return new LeaguesHolder(quotientTransformer, leaguesByFilteredGames);
	}


	// Comparators for leagues - a smaller value on the LHS will mean a higher league placing.
		
	protected Comparator<LeagueRow> createLeagueRowComparator() {
		Comparator<LeagueRow> atomicCompareByLossesPerGame =
			new Comparator<LeagueRow>() {
				public int compare(LeagueRow o1, LeagueRow o2) {
					return new Double(o1.getLossesPerGame()).compareTo(o2.getLossesPerGame());
				}
			};
			
		Comparator<LeagueRow> atomicCompareByRoundsPerWonGame =
			new Comparator<LeagueRow>() {
				public int compare(LeagueRow o1, LeagueRow o2) {
					return new Double(o1.getRoundsPerWonGames()).compareTo(o2.getRoundsPerWonGames());
				}
			};
						
			Comparator<LeagueRow> atomicCompareByRoundsPerLostGame =
				new Comparator<LeagueRow>() {
					public int compare(LeagueRow o1, LeagueRow o2) {
						return new Double(o2.getRoundsPerLostGames()).compareTo(o2.getRoundsPerLostGames());
					}
				};
							
		Comparator<LeagueRow> atomicCompareByGamesPlayed =
			new Comparator<LeagueRow>() {
				public int compare(LeagueRow o1, LeagueRow o2) {
					return new Integer(o2.getGamesPlayed()).compareTo(o1.getGamesPlayed());
				}
			};

		Comparator<LeagueRow> atomicCompareByRoundsPlayed =
			new Comparator<LeagueRow>() {
				public int compare(LeagueRow o1, LeagueRow o2) {
					return new Integer(o2.getRoundsPlayed()).compareTo(o1.getRoundsPlayed());
				}
			};

		Comparator<LeagueRow> atomicCompareByPerson =
			new Comparator<LeagueRow>() {
				public int compare(LeagueRow o1, LeagueRow o2) {
					return o1.getPerson().compareTo(o2.getPerson());
				}
			};
		ComparatorChain<LeagueRow> chain = new ComparatorChain<LeagueRow>();		
		chain.addComparator(atomicCompareByLossesPerGame);
		chain.addComparator(atomicCompareByRoundsPerWonGame);
		chain.addComparator(atomicCompareByRoundsPerLostGame);
		chain.addComparator(atomicCompareByGamesPlayed);
		chain.addComparator(atomicCompareByRoundsPlayed);
		chain.addComparator(atomicCompareByPerson);
		return chain;
	}

	/**
	 * @return the dateUtil
	 */
	public DateUtil getDateUtil() {
		return i_dateUtil;
	}

	/**
	 * @param dateUtil the dateUtil to set
	 */
	public void setDateUtil(DateUtil dateUtil) {
		i_dateUtil = dateUtil;
	}

	/**
	 * @return the personManager
	 */
	public PersonService getPersonService() {
		return i_personService;
	}

	/**
	 * @param personManager the personManager to set
	 */
	public void setPersonService(PersonService personManager) {
		i_personService = personManager;
	}

	/**
	 * @return the gameDao
	 */
	public GameDao getGameDao() {
		return i_gameDao;
	}

	/**
	 * @param gameDao the gameDao to set
	 */
	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

	public Comparator<LeagueRow> getLeagueRowComparator() {
		return i_leagueRowComparator;
	}

	public void setLeagueRowComparator(Comparator<LeagueRow> leagueRowComparator) {
		i_leagueRowComparator = leagueRowComparator;
	}
}
