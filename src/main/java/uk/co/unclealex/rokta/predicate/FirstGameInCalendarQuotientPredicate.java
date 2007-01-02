/**
 * 
 */
package uk.co.unclealex.rokta.predicate;

import java.util.Comparator;
import java.util.GregorianCalendar;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public abstract class FirstGameInCalendarQuotientPredicate<E extends Comparable> extends
		FirstGameInQuotientPredicate {
	
	@Override
	public Comparator<Game> getComparator() {
		return new Comparator<Game>() {
			public int compare(Game o1, Game o2) {
				GregorianCalendar c1 = new GregorianCalendar();
				c1.setTime(o1.getDatePlayed());
				GregorianCalendar c2 = new GregorianCalendar();
				c2.setTime(o2.getDatePlayed());
				Transformer<GregorianCalendar, E> transformer = getCalendarTransformer();
				return transformer.transform(c1).compareTo(transformer.transform(c2));
			}
		};
	}

	public abstract Transformer<GregorianCalendar, E> getCalendarTransformer();
}
