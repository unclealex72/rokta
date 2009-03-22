/**
 * 
 */
package uk.co.unclealex.rokta.filter;

import java.util.SortedSet;

import uk.co.unclealex.rokta.encodable.Encodable;
import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public interface GameFilter extends Encodable<GameFilter> {

	public SortedSet<Game> getGames();
	
	public String getDescription();
	
	public void accept(GameFilterVistor gameFilterVisitor);	
}
