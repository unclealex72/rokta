/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.dao.PlayDao;
import uk.co.unclealex.rokta.server.dao.RoundDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Round;
import uk.co.unclealex.rokta.shared.model.Streak;
import uk.co.unclealex.rokta.shared.model.WinLoseCounter;

/**
 * @author alex
 *
 */
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

	private PersonDao i_personDao;
	private PlayDao i_playDao;
	private RoundDao i_roundDao;
	private GameDao i_gameDao;
	
	@Override
	public SortedMap<Person, Integer> countGamesLostByPlayer(GameFilter gameFilter) {
		SortedMap<Person,Integer> gamesLostByPerson = new TreeMap<Person, Integer>();
		for (Game game : getGameDao().getGamesByFilter(gameFilter)) {
			Person loser = game.getLoser();
			Integer gamesLost = gamesLostByPerson.get(loser);
			if (gamesLost == null) {
				gamesLost = 0;
			}
			gamesLostByPerson.put(loser, gamesLost + 1);
		}
		return gamesLostByPerson;
	}

	@Override
	public SortedSet<Streak> getCurrentWinningStreaks(GameFilter gameFilter) {
		return getStreaks(gameFilter, null, true, true, null); 
	}

	@Override
	public SortedSet<Streak> getCurrentLosingStreaks(GameFilter gameFilter) {
		return getStreaks(gameFilter, null, false, true, null); 
	}

	@Override
	public SortedSet<Streak> getWinningStreaks(GameFilter gameFilter, int targetSize) {
		return getStreaks(gameFilter, null, true, false, targetSize); 
	}
	
	@Override
	public SortedSet<Streak> getLosingStreaks(GameFilter gameFilter, int targetSize) {
		return getStreaks(gameFilter, null, false, false, targetSize); 
	}

	@Override
	public SortedSet<Streak> getWinningStreaks(GameFilter gameFilter, String personName, int targetSize) {
		return getStreaks(gameFilter, personName, true, false, targetSize); 
	}
	
	@Override
	public SortedSet<Streak> getLosingStreaks(GameFilter gameFilter, String personName, int targetSize) {
		return getStreaks(gameFilter, personName, false, false, targetSize); 
	}
	
	protected SortedSet<Streak> getStreaks(
			GameFilter gameFilter, String personName, boolean isWinningStreak, boolean isCurrentOnly, final Integer targetSize) {
		SortedMap<String, List<GameListingStreak>> streakListsByPersonName = getStreakListsByPersonName(gameFilter, isWinningStreak);
		
		// Filter by person name
		List<GameListingStreak> streakListsForRequiredPersons;		
		if (personName != null) {
			streakListsForRequiredPersons = streakListsByPersonName.get(personName);
			if (streakListsForRequiredPersons == null) {
				streakListsForRequiredPersons = new LinkedList<GameListingStreak>();
			}
		}
		else {
			streakListsForRequiredPersons = new ArrayList<GameListingStreak>();
			for (List<GameListingStreak> streakLists : streakListsByPersonName.values()) {
				streakListsForRequiredPersons.addAll(streakLists);
			}
		}
		
		// Filter if only current streaks are needed.
		if (isCurrentOnly) {
			Predicate<GameListingStreak> nonCurrentPredicate = new Predicate<GameListingStreak>() {
				@Override
				public boolean evaluate(GameListingStreak gameListingStreak) {
					return !gameListingStreak.isCurrent();
				}
			};
			streakListsForRequiredPersons.removeAll(CollectionUtils.select(streakListsForRequiredPersons, nonCurrentPredicate));
		}
		
		// Sort the game listing streaks and turn them in to streaks.
		SortedSet<Streak> streaks = new TreeSet<Streak>();
		CollectionUtils.collect(new TreeSet<GameListingStreak>(streakListsForRequiredPersons), new StreakTransformer(), streaks);
		
		if (targetSize != null) {
			Predicate<Streak> tooHighRank = new Predicate<Streak>() {
				@Override
				public boolean evaluate(Streak streak) {
					return streak.getRank() > targetSize;
				}
			};
			streaks.removeAll(CollectionUtils.select(streaks, tooHighRank));
		}
		return streaks;
	}

	protected SortedMap<String, List<GameListingStreak>> getStreakListsByPersonName(GameFilter gameFilter, boolean isWinningStreak) {
		SortedMap<String, List<GameListingStreak>> streaksByPerson = new TreeMap<String, List<GameListingStreak>>();
		SortedMap<String, SortedSet<Game>> currentStreaksByPerson = new TreeMap<String, SortedSet<Game>>();
		
		for (Game game : getGameDao().getGamesByFilter(gameFilter)) {
			for (Person person : game.getParticipants()) {
				String personName = person.getName();
				if (person.equals(game.getLoser()) == isWinningStreak) {
					// If this person is the loser then we add their current streak to the list
					// of streaks
					SortedSet<Game> currentStreak = currentStreaksByPerson.get(personName);
					addStreak(streaksByPerson, currentStreaksByPerson, currentStreak, personName, false);
					currentStreaksByPerson.remove(personName);

				}
				else {
					// Otherwise, add this game to the current streak.
					if (currentStreaksByPerson.get(personName) == null) {
						currentStreaksByPerson.put(personName, new TreeSet<Game>());
					}
					currentStreaksByPerson.get(personName).add(game);
				}
			}
		}
		
		// Now add the current streaks as they won't have been picked up by checking the losers.
		for (Map.Entry<String, SortedSet<Game>> entry : currentStreaksByPerson.entrySet()) {
			String personName = entry.getKey();
			SortedSet<Game> currentStreak = entry.getValue();
			addStreak(streaksByPerson, currentStreaksByPerson, currentStreak, personName, true);
		}
		return streaksByPerson;
	}

	/**
	 * @param streaksByPerson
	 * @param currentStreaksByPersonName 
	 * @param currentStreak
	 * @param person
	 * @param b
	 */
	protected void addStreak(
			Map<String, List<GameListingStreak>> streaksByPerson, Map<String, SortedSet<Game>> currentStreaksByPersonName,
			SortedSet<Game> currentStreak, String personName, boolean current) {
		if (currentStreak != null && currentStreak.size() > 1) {
			GameListingStreak streak = new GameListingStreak(personName, currentStreak, current);
			if (streaksByPerson.get(personName) == null) {
				streaksByPerson.put(personName, new LinkedList<GameListingStreak>());
			}
			streaksByPerson.get(personName).add(streak);
		}
	}

	@Override
	public SortedMap<Person, SortedMap<Person, WinLoseCounter>> getHeadToHeadResultsByPerson(GameFilter gameFilter) {
		SortedMap<Person, SortedMap<Person, WinLoseCounter>> headToHeadResultsByPerson =
			new TreeMap<Person, SortedMap<Person,WinLoseCounter>>();
		for (Game game : getGameDao().getGamesByFilter(gameFilter)) {
      Round round = game.getRounds().last();
			Set<Person> participants = round.getParticipants();
			if (participants.size() == 2) {
				Person first = round.getParticipants().first();
				Person second = round.getParticipants().last();
				Person loser = round.getLosers().first();
				Person winner = first.equals(loser)?second:first;
				getWinLoseCounter(headToHeadResultsByPerson, winner, loser).addWin();
				getWinLoseCounter(headToHeadResultsByPerson, loser, winner).addLoss();
			}
		}
		return headToHeadResultsByPerson;
	}

	/**
	 * @param headToHeadResultsByPerson
	 * @param winner
	 * @param loser
	 * @return
	 */
	protected WinLoseCounter getWinLoseCounter(
			SortedMap<Person, SortedMap<Person, WinLoseCounter>> headToHeadResultsByPerson, Person first, Person second) {
		if (headToHeadResultsByPerson.get(first) == null) {
			headToHeadResultsByPerson.put(first, new TreeMap<Person, WinLoseCounter>());
		}
		SortedMap<Person, WinLoseCounter> results = headToHeadResultsByPerson.get(first);
		if (results.get(second) == null) {
			results.put(second, new WinLoseCounter());
		}
		return results.get(second);
	}

	protected class StreakTransformer implements Transformer<GameListingStreak, Streak> {
		
		private int i_previousRank;
		private int i_previousPosition;
		private int i_previousLength;
		
		@Override
		public Streak transform(GameListingStreak gameListingStreak) {
			int previousLength = getPreviousLength();
			int previousRank = getPreviousRank();
			int previousPosition = getPreviousPosition();
			
			int currentPosition = previousPosition + 1;
			int currentLength = gameListingStreak.getLength();
			int currentRank = (currentLength == previousLength)?previousRank:currentPosition;
			boolean rankSameAsPreviousRank = currentRank == previousRank;
			setPreviousLength(currentLength);
			setPreviousRank(currentRank);
			setPreviousPosition(currentPosition);
			
			return 
				new Streak(
						currentPosition, currentRank, gameListingStreak.getPersonName(),
						gameListingStreak.getFirstGame().getDatePlayed(), gameListingStreak.getLastGame().getDatePlayed(),
						gameListingStreak.getLength(), rankSameAsPreviousRank, gameListingStreak.isCurrent());
		}

		public int getPreviousRank() {
			return i_previousRank;
		}

		public void setPreviousRank(int previousRank) {
			i_previousRank = previousRank;
		}

		public int getPreviousPosition() {
			return i_previousPosition;
		}

		public void setPreviousPosition(int previousPosition) {
			i_previousPosition = previousPosition;
		}

		public int getPreviousLength() {
			return i_previousLength;
		}

		public void setPreviousLength(int previousLength) {
			i_previousLength = previousLength;
		}
		
	}
	/**
	 * @return the personDao
	 */
	public PersonDao getPersonDao() {
		return i_personDao;
	}

	/**
	 * @param personDao the personDao to set
	 */
	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

	/**
	 * @return the playDao
	 */
	public PlayDao getPlayDao() {
		return i_playDao;
	}

	/**
	 * @param playDao the playDao to set
	 */
	public void setPlayDao(PlayDao playDao) {
		i_playDao = playDao;
	}

	/**
	 * @return the roundDao
	 */
	public RoundDao getRoundDao() {
		return i_roundDao;
	}

	/**
	 * @param roundDao the roundDao to set
	 */
	public void setRoundDao(RoundDao roundDao) {
		i_roundDao = roundDao;
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}
}
