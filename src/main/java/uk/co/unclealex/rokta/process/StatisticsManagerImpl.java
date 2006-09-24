/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Streak;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;
import uk.co.unclealex.rokta.model.dao.PlayDao;
import uk.co.unclealex.rokta.model.dao.RoundDao;

/**
 * @author alex
 *
 */
public class StatisticsManagerImpl implements StatisticsManager {

	private GameDao i_gameDao;
	private PersonDao i_personDao;
	private PlayDao i_playDao;
	private RoundDao i_roundDao;
	
	public Map<Person, Integer> countGamesLostByPlayer() {
		Collection<Game> allGames = getGameDao().getAllGames();
		Map<Person,Integer> gamesLostByPerson = new HashMap<Person, Integer>();
		for (Game game : allGames) {
			Person loser = game.getLoser();
			Integer gamesLost = gamesLostByPerson.get(loser);
			if (gamesLost == null) {
				gamesLost = 0;
			}
			gamesLostByPerson.put(loser, gamesLost + 1);
		}
		return gamesLostByPerson;
	}

	public SortedMap<Person, SortedMap<Hand, Integer>> countHandsByPlayer() {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public int count(Person person, Hand hand) {
				return getPlayDao().countByPersonAndHand(person, hand);
			}
		};
		return countOperation(operation);
	}
	
	public SortedMap<Person, SortedMap<Hand, Integer>> countOpeningGambitsByPlayer() {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public int count(Person person, Hand hand) {
				return getRoundDao().countOpeningGambitsByPersonAndHand(person, hand);
			}			
		};
		return countOperation(operation);
	}

	protected interface CountHandPersonOperation {
		public int count(Person person, Hand hand);
	}
	
	protected SortedMap<Person, SortedMap<Hand, Integer>> countOperation(CountHandPersonOperation operation) {
		SortedMap<Person, SortedMap<Hand, Integer>> personMap = new TreeMap<Person, SortedMap<Hand,Integer>>();
		for (Person person : getPersonDao().getPlayers()) {
			SortedMap<Hand, Integer> handMap = new TreeMap<Hand , Integer>();
			personMap.put(person, handMap);
			for (Hand hand : Hand.getAllHands()) {
				handMap.put(hand, operation.count(person, hand));
			}
		}
		return personMap;
	}
	public Map<Person, List<Streak>> getStreaksByPerson() {
		Map<Person, List<Streak>> streaksByPerson = new HashMap<Person, List<Streak>>();
		Map<Person, SortedSet<Game>> currentStreaksByPerson = new HashMap<Person, SortedSet<Game>>();
		
		for (Game game : getGameDao().getAllGames()) {
			for (Person person : game.getParticipants()) {
				if (person.equals(game.getLoser())) {
					// If this person is the loser then we add their current streak to the list
					// of streaks
					SortedSet<Game> currentStreak = currentStreaksByPerson.get(person);
					if (currentStreak != null && currentStreak.size() > 1) {
						Streak streak = new Streak(person, currentStreak);
						if (streaksByPerson.get(person) == null) {
							streaksByPerson.put(person, new LinkedList<Streak>());
						}
						streaksByPerson.get(person).add(streak);
						currentStreaksByPerson.remove(person);
					}
				}
				else {
					// Otherwise, add this game to the current streak.
					if (currentStreaksByPerson.get(person) == null) {
						currentStreaksByPerson.put(person, new TreeSet<Game>());
					}
					currentStreaksByPerson.get(person).add(game);
				}
			}
		}
		return streaksByPerson;
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
