/**
 * 
 */
package uk.co.unclealex.rokta.process.restriction;

/**
 * @author alex
 *
 */
public interface GameRestriction {

	public void accept(GameRestrictionVisitor visitor);
}
