/**
 * 
 */
package uk.co.unclealex.rokta.process.restriction;

/**
 * @author alex
 *
 */
public abstract class GameRestrictionVisitor {

	public abstract void visit(BeforeGameRestriction restriction);
	public abstract void visit(AfterGameRestriction restriction);
	public abstract void visit(BetweenGameRestriction restriction);
	public abstract void visit(AllGameRestriction restriction);
	
	public final void visit(GameRestriction restriction) {
		throw new IllegalArgumentException(restriction.getClass() + " is not a valid Restriction type.");
	}
}
