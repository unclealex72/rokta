/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.List;
import java.util.SortedMap;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.views.Streak;
import uk.co.unclealex.rokta.views.WinLoseCounter;

/**
 * @author alex
 *
 */
public interface StatisticsManager {

	public SortedMap<Person, Integer> countGamesLostByPlayer();

	public SortedMap<Person, List<Streak>> getWinningStreakListsByPerson();
	
	public SortedMap<Person, List<Streak>> getLosingStreakListsByPerson();

	public SortedMap<Person, SortedMap<Person, WinLoseCounter>> getHeadToHeadResultsByPerson();	
}
