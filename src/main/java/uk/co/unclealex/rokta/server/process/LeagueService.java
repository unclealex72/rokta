/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.shared.model.Leagues;

/**
 * @author alex
 *
 */
public interface LeagueService {

	public Leagues generateLeagues(SortedSet<Game> games, Date now);

}