/**
 * 
 */
package uk.co.unclealex.rokta.process.restriction;

/**
 * @author alex
 *
 */
public class AllGameRestriction implements GameRestriction {

	public void accept(GameRestrictionVisitor visitor) {
		visitor.visit(this);
	}

}
