/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Streak;

/**
 * @author alex
 *
 */
public interface StatisticsManager {

	public Map<Person, Integer> countGamesLostByPlayer();

	public SortedMap<Person, SortedMap<Hand, Integer>> countHandsByPlayer();

	public SortedMap<Person, SortedMap<Hand, Integer>> countOpeningGambitsByPlayer();

	public Map<Person, List<Streak>> getStreaksByPerson();
}
