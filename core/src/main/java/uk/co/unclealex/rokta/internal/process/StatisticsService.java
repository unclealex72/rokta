/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.Streak;


/**
 * @author alex
 *
 */
public interface StatisticsService {

	public SortedSet<Streak> getCurrentLosingStreaks(GameFilter gameFilter);

}
