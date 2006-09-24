/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public abstract class AbstractLeagueMilestonePredicate implements
		LeagueMilestonePredicate {

	private SortedSet<Game> i_games;

	/**
	 * @return the games
	 */
	public SortedSet<Game> getGames() {
		return i_games;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(SortedSet<Game> games) {
		i_games = games;
	}

	
}
