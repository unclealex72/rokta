/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.internal.dao.PersonDao;
import uk.co.unclealex.rokta.internal.dao.PlayDao;
import uk.co.unclealex.rokta.internal.dao.RoundDao;
import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.internal.model.Round;
import uk.co.unclealex.rokta.pub.views.Streak;
import uk.co.unclealex.rokta.pub.views.WinLoseCounter;

/**
 * @author alex
 *
 */
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

	private PersonDao i_personDao;
	private PlayDao i_playDao;
	private RoundDao i_roundDao;
	
	private SortedSet<Game> i_games;
	
	public SortedMap<Person, Integer> countGamesLostByPlayer() {
		SortedMap<Person,Integer> gamesLostByPerson = new TreeMap<Person, Integer>();
		for (Game game : getGames()) {
			Person loser = game.getLoser();
			Integer gamesLost = gamesLostByPerson.get(loser);
			if (gamesLost == null) {
				gamesLost = 0;
			}
			gamesLostByPerson.put(loser, gamesLost + 1);
		}
		return gamesLostByPerson;
	}

	public SortedMap<Person, List<Streak>> getWinningStreakListsByPerson() {
		return getStreakListsByPerson(true);
	}

	public SortedMap<Person, List<Streak>> getLosingStreakListsByPerson() {
		return getStreakListsByPerson(false);
	}

	protected SortedMap<Person, List<Streak>> getStreakListsByPerson(boolean losingEndsStreak) {
		SortedMap<Person, List<Streak>> streaksByPerson = new TreeMap<Person, List<Streak>>();
		SortedMap<Person, SortedSet<Game>> currentStreaksByPerson = new TreeMap<Person, SortedSet<Game>>();
		
		for (Game game : getGames()) {
			for (Person person : game.getParticipants()) {
				if (person.equals(game.getLoser()) == losingEndsStreak) {
					// If this person is the loser then we add their current streak to the list
					// of streaks
					SortedSet<Game> currentStreak = currentStreaksByPerson.get(person);
					addStreak(streaksByPerson, currentStreaksByPerson, currentStreak, person, false);
					currentStreaksByPerson.remove(person);

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
		
		// Now add the current streaks as they won't have been picked up by checking the losers.
		for (Map.Entry<Person, SortedSet<Game>> entry : currentStreaksByPerson.entrySet()) {
			Person person = entry.getKey();
			SortedSet<Game> currentStreak = entry.getValue();
			addStreak(streaksByPerson, currentStreaksByPerson, currentStreak, person, true);
		}
		return streaksByPerson;
	}

	/**
	 * @param streaksByPerson
	 * @param currentStreaksByPerson 
	 * @param currentStreak
	 * @param person
	 * @param b
	 */
	private void addStreak(
			Map<Person, List<Streak>> streaksByPerson, Map<Person, SortedSet<Game>> currentStreaksByPerson,
			SortedSet<Game> currentStreak, Person person, boolean current) {
		if (currentStreak != null && currentStreak.size() > 1) {
			Streak streak = new Streak(person, currentStreak, current);
			if (streaksByPerson.get(person) == null) {
				streaksByPerson.put(person, new LinkedList<Streak>());
			}
			streaksByPerson.get(person).add(streak);
		}
	}

	public SortedMap<Person, SortedMap<Person, WinLoseCounter>> getHeadToHeadResultsByPerson() {
		SortedMap<Person, SortedMap<Person, WinLoseCounter>> headToHeadResultsByPerson =
			new TreeMap<Person, SortedMap<Person,WinLoseCounter>>();
		for (Game game : getGames()) {
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
	private WinLoseCounter getWinLoseCounter(
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

	/**
	 * @return the games
	 */
	public SortedSet<Game> getGames() {
		return i_games;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(SortedSet<Game> games) {
		i_games = games;
	}

}
