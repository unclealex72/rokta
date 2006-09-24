/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class QuotientLeagueMilestonePredicate<E> implements
		LeagueMilestonePredicate {

	private Transformer<Game, E> i_transformer;
	private GamesLeagueMilestonePredicate i_delegate = new GamesLeagueMilestonePredicate();

	/**
	 * @param transformer
	 */
	public QuotientLeagueMilestonePredicate(Transformer<Game, E> transformer) {
		i_transformer = transformer;
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
		E previousTransformed = null;
		for (Game game : getGames()) {
			E transformed = i_transformer.transform(game);
			if (previous != null && !previousTransformed.equals(transformed)) {
				milestoneGames.add(previous);
			}
			previous = game;
			previousTransformed = transformed;
		}
		if (previous != null) {
			milestoneGames.add(previous);
		}
		return milestoneGames;
	}
	/**
	 * @return
	 * @see uk.co.unclealex.rokta.process.LeagueMilestonePredicate#getGames()
	 */
	public SortedSet<Game> getGames() {
		return i_delegate.getGames();
	}

	/**
	 * @param games
	 * @see uk.co.unclealex.rokta.process.LeagueMilestonePredicate#setGames(java.util.SortedSet)
	 */
	public void setGames(SortedSet<Game> games) {
		i_delegate.setGames(games);
	}
	
}
