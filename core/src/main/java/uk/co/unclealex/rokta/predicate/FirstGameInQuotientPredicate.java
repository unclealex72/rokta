/**
 * 
 */
package uk.co.unclealex.rokta.predicate;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.encodable.AbstractEmptyEncodable;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.SpecificGamesPredicate;

/**
 * @author alex
 *
 */
public abstract class FirstGameInQuotientPredicate
extends AbstractEmptyEncodable<GamePredicate> implements GamePredicate {

	public Predicate<Game> createPredicate(SortedSet<Game> games) {
		Comparator<Game> comparator = getComparator();
		SortedSet<Game> specificGames = new TreeSet<Game>(games.comparator());
		Game previous = null;
		for (Game current : games) {
			if (previous == null || comparator.compare(previous, current) != 0) {
				specificGames.add(current);
			}
			previous = current;
		}
		return new SpecificGamesPredicate(specificGames);
	}
	
	public abstract Comparator<Game> getComparator();
}
