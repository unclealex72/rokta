package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.comparators.ComparatorChain;

import uk.co.unclealex.rokta.model.Delta;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.util.DateUtil;

public class LeagueManagerImpl implements LeagueManager {

	private Collection<Game> i_currentGames;
	private Collection<Game> i_previousGames;
	private Comparator<LeagueRow> i_comparator;
	
	private GameDao i_gameDao;
	private DateUtil i_dateUtil;
	private PersonManager i_personManager;
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeague(java.util.Date)
	 */
	public League generateLeague(Date now) {
		SortedSet<LeagueRow> currentLeague = generateLeague(getCurrentGames());
		SortedSet<LeagueRow> previousLeague = generateLeague(getPreviousGames());
		
		Map<Person,Integer> oldPositions = new HashMap<Person, Integer>();
		Map<Person,Integer> newPositions = new HashMap<Person, Integer>();
				
		for (
				IndexingIterator<LeagueRow> iter = new IndexingIterator<LeagueRow>(currentLeague.iterator());
				iter.hasNext(); ) {
			LeagueRow row = iter.next();
			newPositions.put(row.getPerson(), iter.getIndex());
		}
		for (
				IndexingIterator<LeagueRow> iter = new IndexingIterator<LeagueRow>(previousLeague.iterator());
				iter.hasNext(); ) {
			LeagueRow row = iter.next();
			oldPositions.put(row.getPerson(), iter.getIndex());
		}
		
		for (LeagueRow row : currentLeague) {
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
		
		// If the collection of current games contains the last game then we can show
		// who is currently exempt, who is not playing today and the game gap between players.
		
		League league = new League();
		league.setRows(currentLeague);
		Game lastGame = getGameDao().getLastGame();
		if (getCurrentGames().contains(lastGame)) {
			league.setCurrent(true);
			decorateCurrentLeague(currentLeague, lastGame, now);
		}
		return league;
	}
	
	private SortedSet<LeagueRow> generateLeague(Collection<Game> games) {
		int totalGames = 0;
		Map<Person, LeagueRow> rowMap = new HashMap<Person, LeagueRow>();
		
		for (Game game : games) {
			totalGames++;
			Person loser = game.getLoser();
			for (Person participant : game.getParticipants()) {
				if (!rowMap.containsKey(participant)) {
					LeagueRow newLeagueRow = new LeagueRow();
					newLeagueRow.setPerson(participant);
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
		}
		
		// Now enter the total number of games ever played and initialise the deltas.
		for (LeagueRow leagueRow : rowMap.values()) {
			leagueRow.setTotalGamesPlayed(totalGames);
			leagueRow.setDelta(Delta.NONE);
		}
		
		// Populate the number of rounds played by each person
		
		SortedSet<LeagueRow> league = new TreeSet<LeagueRow>(getComparator());
		league.addAll(rowMap.values());
		return league;
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
	
	private void decorateCurrentLeague(SortedSet<LeagueRow> currentLeague, Game lastGame, Date now) {
		// Exemptions only occur if the last game played was today.
		// If the last game was played today we can also see who hasn't played today
		if (getDateUtil().areSameDay(lastGame.getDatePlayed(), now)) {
			Person exempt = getPersonManager().getExemptPlayer(now);
			for (LeagueRow leagueRow : currentLeague) {
				Person player = leagueRow.getPerson();
				leagueRow.setExempt(player.equals(exempt));
				leagueRow.setPlayingToday(getPersonManager().currentlyPlaying(player, now));
			}
		}
		
		LeagueRow previousRow = null;
		for (LeagueRow currentRow : currentLeague) {
			if (previousRow != null) {
				int gap = calculateGap(previousRow, currentRow);
				currentRow.setGap(gap);
			}
			previousRow = currentRow;
		}
	}
	
	private Integer calculateGap(LeagueRow higherRow, LeagueRow lowerRow) {
		int playedTop = higherRow.getGamesPlayed();
		int lostTop = higherRow.getGamesLost();
		boolean exemptTop = higherRow.isExempt();
		boolean playingTop = higherRow.isPlayingToday();
		int playedBottom = lowerRow.getGamesPlayed();
		int lostBottom = lowerRow.getGamesLost();
		boolean exemptBottom = lowerRow.isExempt();
		boolean playingBottom = lowerRow.isPlayingToday();
		
		Integer gap;
		if (!playingTop && !playingBottom) {
			playingTop = playingBottom = true;
		}
		if (!playingTop && lostTop == 0) {
			gap = null;
		}
		else {
			gap = race(playingTop, playedTop, lostTop, exemptTop, playingBottom, playedBottom, lostBottom, exemptBottom);
		}
		return gap;
	}
	
	
	private int race(boolean playingTop, int playedTop, int lostTop, boolean exemptTop, boolean playingBottom, int playedBottom, int lostBottom, boolean exemptBottom) {
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


	private static Comparator<LeagueRow> s_atomicCompareByLossesPerGame =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Double(o1.getLossesPerGame()).compareTo(o2.getLossesPerGame());
			}
		};
		
	private static Comparator<LeagueRow> s_atomicCompareByRoundsPerGame =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Double(o1.getRoundsPerGame()).compareTo(o2.getRoundsPerGame());
			}
		};
					
	private static Comparator<LeagueRow> s_atomicCompareByGamesPlayed =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Integer(o2.getGamesPlayed()).compareTo(o1.getGamesPlayed());
			}
		};

	private static Comparator<LeagueRow> s_atomicCompareByRoundsPlayed =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Integer(o2.getRoundsPlayed()).compareTo(o1.getRoundsPlayed());
			}
		};

	private static Comparator<LeagueRow> s_atomicCompareByPerson =
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
	public Comparator<LeagueRow> getComparator() {
		return i_comparator;
	}
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setComparator(java.util.Comparator)
	 */
	public void setComparator(Comparator<LeagueRow> comparator) {
		i_comparator = comparator;
	}
	public Collection<Game> getCurrentGames() {
		return i_currentGames;
	}
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setCurrentGames(java.util.Collection)
	 */
	public void setCurrentGames(Collection<Game> currentGames) {
		i_currentGames = currentGames;
	}
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getPreviousGames()
	 */
	public Collection<Game> getPreviousGames() {
		return i_previousGames;
	}
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setPreviousGames(java.util.Collection)
	 */
	public void setPreviousGames(Collection<Game> previousGames) {
		i_previousGames = previousGames;
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

}
