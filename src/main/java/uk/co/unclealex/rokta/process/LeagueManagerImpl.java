package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.collections15.comparators.ComparatorChain;

import uk.co.unclealex.rokta.model.Delta;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.InfiniteInteger;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.util.DateUtil;

public class LeagueManagerImpl implements LeagueManager {

	private GameDao i_gameDao;
	private SortedSet<Game> i_games;
	private Date i_currentDate;
	
	private LeagueMilestonePredicate i_leagueMilestonePredicate;
	private Comparator<LeagueRow> i_comparator;
	
	private DateUtil i_dateUtil;
	private PersonManager i_personManager;
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeague(java.util.Date)
	 */
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeagues()
	 */
	public SortedMap<Game, League> generateLeagues() {
		if (getComparator() == null) {
			setComparator(getCompareByLossesPerGame());
		}
		SortedMap<Game, League> leagues = createLeagues();
		calculateDeltas(leagues);
		decorate(leagues, getGameDao().getLastGame());
		return leagues;
	}
	
	private SortedMap<Game, League> createLeagues() {
		int totalGames = 0;
		int totalPlayers = 0;
		Map<Person, LeagueRow> rowMap = new HashMap<Person, LeagueRow>();
		SortedMap<Game, League> leagues = new TreeMap<Game, League>();
		
		LeagueMilestonePredicate predicate = getLeagueMilestonePredicate();
		predicate.setGames(getGames());
		for (Game game : getGames()) {
			totalGames++;
			Person loser = game.getLoser();
			for (Person participant : game.getParticipants()) {
				totalPlayers++;
				if (!rowMap.containsKey(participant)) {
					LeagueRow newLeagueRow = new LeagueRow();
					newLeagueRow.setPerson(participant);
					newLeagueRow.setDelta(Delta.NONE);
					rowMap.put(participant, newLeagueRow);
				}
				LeagueRow leagueRow = rowMap.get(participant);
				leagueRow.setGamesPlayed(leagueRow.getGamesPlayed() + 1);
				if (participant.equals(loser)) {
					leagueRow.setGamesLost(leagueRow.getGamesLost() + 1);
				}
				leagueRow.setRoundsPlayed(
						leagueRow.getRoundsPlayed() + countPlaysForPersonInGame(participant, game));
			}
			if (predicate.evaluate(game)) {
				League league = createLeague(game, rowMap.values(), totalGames, totalPlayers);
				leagues.put(game, league);
			}
		}
		
		return leagues;
	}

	private int countPlaysForPersonInGame(Person person, Game game) {
		int cnt = 0;
		for (Round round : game.getRounds()) {
			if (round.getParticipants().contains(person)) {
				cnt++;
			}
		}
		return cnt;
	}

	private League createLeague(Game game, Collection<LeagueRow> rows, int totalGames, int totalPlayers) {
		SortedSet<LeagueRow> sortedRows = new TreeSet<LeagueRow>(getComparator());
		for (LeagueRow leagueRow : rows) {
			LeagueRow copy = leagueRow.copy();
			copy.setTotalGamesPlayed(totalGames);
			sortedRows.add(copy);
		}
		
		League league = new League();
		league.setCurrent(false);
		league.setRows(sortedRows);
		league.setTotalGames(totalGames);
		league.setTotalPlayers(totalPlayers);
		return league;
	}
	
	private void calculateDeltas(SortedMap<Game, League> leagues) {
		Map<Person,Integer> oldPositions;
		Map<Person,Integer> newPositions = new HashMap<Person, Integer>();
				
		for (League league : leagues.values()) {
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
	
	private void decorate(SortedMap<Game, League> leagues, Game lastGame) {
		for (Map.Entry<Game, League> entry: leagues.entrySet()) {
			Game game = entry.getKey();
			League league = entry.getValue();
			// Exemptions only occur if the last game played was today.
			// If the last game was played today we can also see who hasn't played today
			if (game.equals(lastGame)) {
				league.setCurrent(true);
				if (getDateUtil().areSameDay(lastGame.getDatePlayed(), getCurrentDate())) {
					Person exempt = getPersonManager().getExemptPlayer(getCurrentDate());
					for (LeagueRow leagueRow : league.getRows()) {
						Person player = leagueRow.getPerson();
						leagueRow.setExempt(player.equals(exempt));
						leagueRow.setPlayingToday(getPersonManager().currentlyPlaying(player, getCurrentDate()));
					}
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
	
	private InfiniteInteger calculateGap(LeagueRow higherRow, LeagueRow lowerRow) {
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
	
	
	private int race(
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


	static Comparator<LeagueRow> s_atomicCompareByLossesPerGame =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Double(o1.getLossesPerGame()).compareTo(o2.getLossesPerGame());
			}
		};
		
	static Comparator<LeagueRow> s_atomicCompareByRoundsPerGame =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Double(o1.getRoundsPerGame()).compareTo(o2.getRoundsPerGame());
			}
		};
					
	static Comparator<LeagueRow> s_atomicCompareByGamesPlayed =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Integer(o2.getGamesPlayed()).compareTo(o1.getGamesPlayed());
			}
		};

	static Comparator<LeagueRow> s_atomicCompareByRoundsPlayed =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Integer(o2.getRoundsPlayed()).compareTo(o1.getRoundsPlayed());
			}
		};

	static Comparator<LeagueRow> s_atomicCompareByPerson =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return o1.getPerson().compareTo(o2.getPerson());
			}
		};
	
	public Comparator<LeagueRow> getCompareByLossesPerGame() {
		ComparatorChain<LeagueRow> chain = new ComparatorChain<LeagueRow>();
		
		chain.addComparator(s_atomicCompareByLossesPerGame);
		chain.addComparator(s_atomicCompareByRoundsPerGame);
		chain.addComparator(s_atomicCompareByGamesPlayed);
		chain.addComparator(s_atomicCompareByRoundsPlayed);
		chain.addComparator(s_atomicCompareByPerson);
		return chain;
	}
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getComparator()
	 */
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getComparator()
	 */
	public Comparator<LeagueRow> getComparator() {
		return i_comparator;
	}
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setComparator(java.util.Comparator)
	 */
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setComparator(java.util.Comparator)
	 */
	public void setComparator(Comparator<LeagueRow> comparator) {
		i_comparator = comparator;
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
	public PersonManager getPersonManager() {
		return i_personManager;
	}

	/**
	 * @param personManager the personManager to set
	 */
	public void setPersonManager(PersonManager personManager) {
		i_personManager = personManager;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getGames()
	 */
	public SortedSet<Game> getGames() {
		return i_games;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setGames(java.util.SortedSet)
	 */
	public void setGames(SortedSet<Game> games) {
		i_games = games;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getLeagueMilestonePredicate()
	 */
	public LeagueMilestonePredicate getLeagueMilestonePredicate() {
		return i_leagueMilestonePredicate;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setLeagueMilestonePredicate(uk.co.unclealex.rokta.process.LeagueMilestonePredicate)
	 */
	public void setLeagueMilestonePredicate(
			LeagueMilestonePredicate leagueMilestonePredicate) {
		i_leagueMilestonePredicate = leagueMilestonePredicate;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getCurrentDate()
	 */
	public Date getCurrentDate() {
		return i_currentDate;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setCurrentDate(java.util.Date)
	 */
	public void setCurrentDate(Date currentDate) {
		i_currentDate = currentDate;
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

}
