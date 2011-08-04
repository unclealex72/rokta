/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.Date;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.League;

/**
 * @author alex
 *
 */
public interface LeagueService {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeague(java.util.Date)
	 */
	public LeaguesHolder generateLeagues(GameFilter gameFilter, int maximumLeagues, Date now);

	public League generateLeague(GameFilter gameFilter, Date now);

}