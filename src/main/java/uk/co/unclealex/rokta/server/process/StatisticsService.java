/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.shared.model.HeadToHeads;
import uk.co.unclealex.rokta.shared.model.Streaks;


/**
 * @author alex
 *
 */
public interface StatisticsService {

	public Streaks getStreaks(SortedSet<Game> games, int targetSize);
	
	public HeadToHeads getHeadToHeadResultsByPerson(SortedSet<Game> games);

}
