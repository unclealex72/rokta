/**
 * 
 */
package uk.co.unclealex.rokta.filter;

import java.io.Serializable;


/**
 * @author alex
 *
 */
public interface GameFilter extends Serializable {

	public <T> T accept(GameFilterVistor<T> gameFilterVisitor);
	
	public boolean isContinuous();
}
