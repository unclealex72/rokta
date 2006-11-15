/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public abstract class FirstGameInQuotientPredicate implements Predicate<Game> {

	private SpecificGamesPredicate i_specificGamesPredicate;
	
	public FirstGameInQuotientPredicate(SortedSet<Game> games) {
		Comparator<Game> comparator = getComparator();
		SortedSet<Game> specificGames = new TreeSet<Game>(games.comparator());
		Game previous = null;
		for (Game current : games) {
			if (previous == null || comparator.compare(previous, current) != 0) {
				specificGames.add(current);
			}
			previous = current;
		}
		i_specificGamesPredicate = new SpecificGamesPredicate(specificGames);
	}
	
	public boolean evaluate(Game game) {
		return i_specificGamesPredicate.evaluate(game);
	}

	public abstract Comparator<Game> getComparator();
}
