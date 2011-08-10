package uk.co.unclealex.rokta.server.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.commons.collections15.comparators.ComparatorChain;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Round;
import uk.co.unclealex.rokta.server.util.DateUtil;
import uk.co.unclealex.rokta.shared.model.Delta;
import uk.co.unclealex.rokta.shared.model.InfiniteInteger;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;

import com.google.common.collect.Sets;

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
	public SortedSet<League> generateLeagues(GameFilter gameFilter, Date now) {
		SortedSet<Game> games = getGameDao().getGamesByFilter(gameFilter);
		SortedMap<Game, League> leaguesForAllGames = generateLeagues(games);
		decorateWithDeltas(leaguesForAllGames);
		decorateWithExemptionAndAbsence(leaguesForAllGames, now);
		return Sets.newTreeSet(leaguesForAllGames.values());
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
					newLeagueRow.setPersonName(participant.getName());
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
			League league = createLeague(rowMap.values(), totalGames, totalPlayers, game);
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

	protected League createLeague(Collection<LeagueRow> rows, int totalGames, int totalPlayers, Game game) {
		League league = new League();
		game.setDatePlayed(game.getDatePlayed());
		league.setCurrent(false);
		league.setTotalGames(totalGames);
		league.setTotalPlayers(totalPlayers);

		SortedSet<LeagueRow> sortedRows = new TreeSet<LeagueRow>(getLeagueRowComparator());
		for (LeagueRow leagueRow : rows) {
			LeagueRow copy = leagueRow.copy();
			copy.setLeague(league);
			sortedRows.add(copy);
		}		
		league.setRows(new ArrayList<LeagueRow>(sortedRows));
		return league;
	}
	
	protected void decorateWithDeltas(SortedMap<Game, League> leaguesByGame) {
		// We only decorate the last league and only if there is something to compare it against.
		if (leaguesByGame.size() < 2) {
			return;
		}
		
		Game lastGame = leaguesByGame.lastKey();
		Game penultimateGame = leaguesByGame.headMap(lastGame).lastKey();
		Map<String,Integer> oldPositions;
		Map<String,Integer> newPositions = new HashMap<String, Integer>();
		League[] leagues = new League[] { leaguesByGame.get(penultimateGame), leaguesByGame.get(lastGame) };
		for (League league : leagues) {
			oldPositions = newPositions;
			newPositions = new HashMap<String, Integer>();
			for (
					IndexingIterator<LeagueRow> iter = new IndexingIterator<LeagueRow>(league.getRows().iterator());
					iter.hasNext(); ) {
				LeagueRow row = iter.next();
				newPositions.put(row.getPersonName(), iter.getIndex());
			}
			
			for (LeagueRow row : league.getRows()) {
				String personName = row.getPersonName();
				Integer currentPosition = newPositions.get(personName);
				Integer previousPosition = oldPositions.get(personName);
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
	
	protected void decorateWithExemptionAndAbsence(SortedMap<Game, League> leaguesByGame, Date currentDate) {
		if (leaguesByGame.isEmpty()) {
			return;
		}
		SortedSet<String> currentPlayerNames = getPersonService().getCurrentPlayerNames(currentDate);
		Game lastGamePlayed = getGameDao().getLastGame();
		Game lastLeagueGame = leaguesByGame.lastKey();
		// Exemptions only occur if the last game played was today.
		// If the last game was played today we can also see who hasn't played today
		if (lastLeagueGame.equals(lastGamePlayed)) {
			League league = leaguesByGame.get(lastLeagueGame);
			league.setCurrent(true);
			if (getDateUtil().areSameDay(lastGamePlayed.getDatePlayed(), currentDate)) {
				Person exempt = getPersonService().getExemptPlayer(currentDate);
				String exemptName = exempt == null?null:exempt.getName();
				for (LeagueRow leagueRow : league.getRows()) {
					String playerName = leagueRow.getPersonName();
					leagueRow.setExempt(playerName.equals(exemptName));
					leagueRow.setPlayingToday(currentPlayerNames.contains(playerName));
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
					return o1.getPersonName().compareTo(o2.getPersonName());
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
