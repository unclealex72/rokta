/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.SortedMap;
import java.util.SortedSet;

import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.Streak;
import uk.co.unclealex.rokta.pub.views.WinLoseCounter;


/**
 * @author alex
 *
 */
public interface StatisticsService {

	public SortedSet<Streak> getCurrentLosingStreaks(GameFilter gameFilter);

	public SortedSet<Streak> getCurrentWinningStreaks(GameFilter gameFilter);

	public SortedSet<Streak> getLosingStreaks(GameFilter gameFilter, int targetSize);

	public SortedSet<Streak> getWinningStreaks(GameFilter gameFilter, int targetSize);

	public SortedSet<Streak> getLosingStreaks(GameFilter gameFilter, String personName, int targetSize);

	public SortedSet<Streak> getWinningStreaks(GameFilter gameFilter, String personName, int targetSize);

	public SortedMap<Person, SortedMap<Person, WinLoseCounter>> getHeadToHeadResultsByPerson(GameFilter gameFilter);

	public SortedMap<Person, Integer> countGamesLostByPlayer(GameFilter gameFilter);


}
