/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.Collection;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.internal.model.Game;

/**
 * @author alex
 *
 */
public class SpecificGamesPredicate implements Predicate<Game> {

	private Collection<Game> i_specificGames;
	
	public SpecificGamesPredicate() {
	}
	
	public SpecificGamesPredicate(Collection<Game> specificGames) {
		super();
		i_specificGames = specificGames;
	}

	public boolean evaluate(Game game) {
		return getSpecificGames().contains(game);
	}

	/**
	 * @return the specificGames
	 */
	public Collection<Game> getSpecificGames() {
		return i_specificGames;
	}

	/**
	 * @param specificGames the specificGames to set
	 */
	protected void setSpecificGames(Collection<Game> specificGames) {
		i_specificGames = specificGames;
	}

}
