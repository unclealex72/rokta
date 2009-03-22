/**
 * 
 */
package uk.co.unclealex.rokta.process.league.milestone;

import java.util.Iterator;
import java.util.List;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class LazyAndLeagueMilestonePredicate extends
		ChainLeagueMilestonePredicate {

	/**
	 * @param leagueMilestonePredicates
	 */
	public LazyAndLeagueMilestonePredicate(List<LeagueMilestonePredicate> leagueMilestonePredicates) {
		super(leagueMilestonePredicates);
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.collections15.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Game game) {
		boolean value = true;
		for (Iterator<LeagueMilestonePredicate> iter = getLeagueMilestonePredicates().iterator(); iter.hasNext() && value; ) {
			LeagueMilestonePredicate predicate = iter.next();
			value = predicate.evaluate(game);
		}
		return value;
	}

}
