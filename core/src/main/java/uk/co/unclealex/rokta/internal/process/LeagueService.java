/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.LeaguesHolder;

/**
 * @author alex
 *
 */
public interface LeagueService {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeague(java.util.Date)
	 */
	public LeaguesHolder generateLeague(GameFilter gameFilter, int maximumLeagues, DateTime now);

}