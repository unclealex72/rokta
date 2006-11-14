/**
 * 
 */
package uk.co.unclealex.rokta.process.league.milestone;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class QuotientLeagueMilestonePredicate implements LeagueMilestonePredicate {

	private Comparator<Game> i_comparator;
	private GamesLeagueMilestonePredicate i_delegate = new GamesLeagueMilestonePredicate();

	/**
	 * @param comparator
	 */
	public QuotientLeagueMilestonePredicate(Comparator<Game> comparator) {
		i_comparator = comparator;
	}
	public boolean evaluate(Game game) {
		if (i_delegate.getMilestoneGames() == null) {
			i_delegate.setMilestoneGames(calculateMilestoneGames());
		}
		return i_delegate.evaluate(game);
	}
	
	/**
	 * @return
	 */
	private Collection<Game> calculateMilestoneGames() {
		List<Game> milestoneGames = new LinkedList<Game>();
		Game previous = null;
		for (Game game : getGames()) {
			if (previous != null && i_comparator.compare(game, previous) == 0) {
				milestoneGames.add(previous);
			}
			previous = game;
		}
		if (previous != null) {
			milestoneGames.add(previous);
		}
		return milestoneGames;
	}
	/**
	 * @return
	 * @see uk.co.unclealex.rokta.process.league.milestone.LeagueMilestonePredicate#getGames()
	 */
	public SortedSet<Game> getGames() {
		return i_delegate.getGames();
	}

	/**
	 * @param games
	 * @see uk.co.unclealex.rokta.process.league.milestone.LeagueMilestonePredicate#setGames(java.util.SortedSet)
	 */
	public void setGames(SortedSet<Game> games) {
		i_delegate.setGames(games);
	}
	
}
