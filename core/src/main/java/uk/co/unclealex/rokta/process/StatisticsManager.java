/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Streak;
import uk.co.unclealex.rokta.model.WinLoseCounter;

/**
 * @author alex
 *
 */
public interface StatisticsManager {

	public SortedMap<Person, Integer> countGamesLostByPlayer();

	public SortedMap<Person, List<Streak>> getWinningStreakListsByPerson();
	
	public SortedMap<Person, List<Streak>> getLosingStreakListsByPerson();

	public SortedMap<Person, SortedMap<Person, WinLoseCounter>> getHeadToHeadResultsByPerson();
	
	public SortedSet<Game> getGames();

	public void setGames(SortedSet<Game> games);
}
