/**
 * 
 */
package uk.co.unclealex.rokta.process.league.milestone;

import java.util.Collection;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class GamesLeagueMilestonePredicate extends AbstractLeagueMilestonePredicate {

	private Collection<Game> i_milestoneGames;

	public GamesLeagueMilestonePredicate() {
	}
	
	/**
	 * @param milestoneGames
	 */
	public GamesLeagueMilestonePredicate(Collection<Game> milestoneGames) {
		super();
		i_milestoneGames = milestoneGames;
	}

	public boolean evaluate(Game game) {
		return getMilestoneGames().contains(game);
	}
	/**
	 * @return the milestoneGames
	 */
	public Collection<Game> getMilestoneGames() {
		return i_milestoneGames;
	}

	/**
	 * @param milestoneGames the milestoneGames to set
	 */
	public void setMilestoneGames(Collection<Game> milestoneGames) {
		i_milestoneGames = milestoneGames;
	}
}
