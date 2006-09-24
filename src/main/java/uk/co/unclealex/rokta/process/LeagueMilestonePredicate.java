/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.SortedSet;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public interface LeagueMilestonePredicate extends Predicate<Game> {

	public SortedSet<Game> getGames();
	
	public void setGames(SortedSet<Game> games);
}
