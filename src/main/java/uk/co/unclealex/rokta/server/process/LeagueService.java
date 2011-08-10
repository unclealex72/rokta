/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.League;

/**
 * @author alex
 *
 */
public interface LeagueService {

	public SortedSet<League> generateLeagues(GameFilter gameFilter, Date now);

}