/**
 * 
 */
package uk.co.unclealex.rokta.process.league.milestone;

import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public abstract class ChainLeagueMilestonePredicate extends
		AbstractLeagueMilestonePredicate implements LeagueMilestonePredicate {

	private List<LeagueMilestonePredicate> i_leagueMilestonePredicates;

	/**
	 * @param leagueMilestonePredicates
	 */
	public ChainLeagueMilestonePredicate(List<LeagueMilestonePredicate> leagueMilestonePredicates) {
		i_leagueMilestonePredicates = leagueMilestonePredicates;
	}

	/**
	 * @return the leagueMilestonePredicates
	 */
	public List<LeagueMilestonePredicate> getLeagueMilestonePredicates() {
		return i_leagueMilestonePredicates;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.league.milestone.AbstractLeagueMilestonePredicate#setGames(java.util.SortedSet)
	 */
	@Override
	public void setGames(SortedSet<Game> games) {
		super.setGames(games);
		for (LeagueMilestonePredicate predicate : getLeagueMilestonePredicates()) {
			predicate.setGames(games);
		}
	}
	
	
}
