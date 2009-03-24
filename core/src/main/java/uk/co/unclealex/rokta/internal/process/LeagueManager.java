/**
 * 
 */
package uk.co.unclealex.rokta.process;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.filter.GameFilter;
import uk.co.unclealex.rokta.views.LeaguesHolder;

/**
 * @author alex
 *
 */
public interface LeagueManager {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeague(java.util.Date)
	 */
	public LeaguesHolder generateLeague(GameFilter gameFilter, int maximumLeagues, DateTime now);

}