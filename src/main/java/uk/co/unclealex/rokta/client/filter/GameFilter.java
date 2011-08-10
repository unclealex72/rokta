/**
 * 
 */
package uk.co.unclealex.rokta.client.filter;

import java.io.Serializable;


/**
 * @author alex
 *
 */
public interface GameFilter extends Serializable {

	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor);
	
	public Modifier getModifier();
}
