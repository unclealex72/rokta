/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.dao.PlayDao;
import uk.co.unclealex.rokta.server.dao.RoundDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Round;
import uk.co.unclealex.rokta.shared.model.HeadToHeads;
import uk.co.unclealex.rokta.shared.model.Streak;
import uk.co.unclealex.rokta.shared.model.Streaks;
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
	
	@Override
	public Streaks getStreaks(SortedSet<Game> games, final int targetSize) {
		Map<Boolean, SortedSet<Streak>> allStreaksByIsWinning = new HashMap<Boolean, SortedSet<Streak>>();
		Map<Boolean, SortedSet<Streak>> currentStreaksByIsWinning = new HashMap<Boolean, SortedSet<Streak>>();
		
		for (boolean isWinningStreak : new boolean[] { true, false }) {
			SortedMap<String, List<GameListingStreak>> streakListsByPersonName = getStreakListsByPersonName(games, isWinningStreak);
			List<GameListingStreak> allStreakLists = new ArrayList<GameListingStreak>();
			for (List<GameListingStreak> streakLists : streakListsByPersonName.values()) {
				allStreakLists.addAll(streakLists);
			}

			Predicate<GameListingStreak> currentPredicate = new Predicate<GameListingStreak>() {
				@Override
				public boolean evaluate(GameListingStreak gameListingStreak) {
					return gameListingStreak.isCurrent();
				}
			};
			
			List<GameListingStreak> currentGameStreaks = 
					CollectionUtils.select(allStreakLists, currentPredicate, new ArrayList<GameListingStreak>());
			SortedSet<Streak> currentStreaks = 
				CollectionUtils.collect(
					new TreeSet<GameListingStreak>(currentGameStreaks), new StreakTransformer(), new TreeSet<Streak>());

		
			// Sort the game listing streaks and turn them in to streaks.
			SortedSet<Streak> streaks = new TreeSet<Streak>();
			CollectionUtils.collect(new TreeSet<GameListingStreak>(allStreakLists), new StreakTransformer(), streaks);
			
			Predicate<Streak> tooHighRank = new Predicate<Streak>() {
				@Override
				public boolean evaluate(Streak streak) {
					return streak.getRank() > targetSize;
				}
			};
			streaks.removeAll(CollectionUtils.select(streaks, tooHighRank));
			allStreaksByIsWinning.put(isWinningStreak, streaks);
			currentStreaksByIsWinning.put(isWinningStreak, currentStreaks);
		}
		return new Streaks(
			allStreaksByIsWinning.get(Boolean.TRUE), allStreaksByIsWinning.get(Boolean.FALSE), 
			currentStreaksByIsWinning.get(Boolean.TRUE), currentStreaksByIsWinning.get(Boolean.FALSE));
	}

	protected SortedMap<String, List<GameListingStreak>> getStreakListsByPersonName(SortedSet<Game> games, boolean isWinningStreak) {
		SortedMap<String, List<GameListingStreak>> streaksByPerson = new TreeMap<String, List<GameListingStreak>>();
		SortedMap<String, SortedSet<Game>> currentStreaksByPerson = new TreeMap<String, SortedSet<Game>>();
		
		for (Game game : games) {
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
	public HeadToHeads getHeadToHeadResultsByPerson(SortedSet<Game> games) {
		SortedMap<String, SortedMap<String, WinLoseCounter>> headToHeadResultsByPerson =
			new TreeMap<String, SortedMap<String,WinLoseCounter>>();
		for (Game game : games) {
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
		Predicate<WinLoseCounter> nonEmptyWinLoseCounterPredicate = new Predicate<WinLoseCounter>() {
			@Override
			public boolean evaluate(WinLoseCounter winLoseCounter) {
				return winLoseCounter.getWinCount() != 0 || winLoseCounter.getLossCount() != 0;
			}
		};
		Set<WinLoseCounter> winLoseCounters = new HashSet<WinLoseCounter>();
		for (SortedMap<String, WinLoseCounter> map : headToHeadResultsByPerson.values()) {
			CollectionUtils.select(map.values(), nonEmptyWinLoseCounterPredicate, winLoseCounters);
		}
		Transformer<WinLoseCounter, String> playerTransformer = new Transformer<WinLoseCounter, String>() {
			@Override
			public String transform(WinLoseCounter winLoseCounter) {
				return winLoseCounter.getWinner();
			}
		};
		SortedSet<String> playerNames = CollectionUtils.collect(winLoseCounters, playerTransformer, new TreeSet<String>());
		return new HeadToHeads(playerNames, winLoseCounters);
	}

	/**
	 * @param headToHeadResultsByPerson
	 * @param winner
	 * @param loser
	 * @return
	 */
	protected WinLoseCounter getWinLoseCounter(
			SortedMap<String, SortedMap<String, WinLoseCounter>> headToHeadResultsByPerson, Person first, Person second) {
		String firstName = first.getName();
		String secondName = second.getName();
		if (headToHeadResultsByPerson.get(firstName) == null) {
			headToHeadResultsByPerson.put(firstName, new TreeMap<String, WinLoseCounter>());
		}
		SortedMap<String, WinLoseCounter> results = headToHeadResultsByPerson.get(firstName);
		if (results.get(secondName) == null) {
			results.put(secondName, new WinLoseCounter(firstName, 0, secondName, 0));
		}
		return results.get(secondName);
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
}
