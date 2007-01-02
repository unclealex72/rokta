/**
 * 
 */
package uk.co.unclealex.rokta.predicate;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.collections15.Transformer;

/**
 * @author alex
 *
 */
public class FirstGameOfTheDayPredicate extends FirstGameInCalendarQuotientPredicate<Integer> {

	@Override
	protected char getEncodingPrefix() {
		return GamePredicateFactory.FIRST_GAME_OF_DAY_PREFIX;
	}

	private static final Transformer<GregorianCalendar, Integer> s_transformer = 
		new Transformer<GregorianCalendar, Integer>() {
			public Integer transform(GregorianCalendar c) {
				return c.get(Calendar.YEAR) * 400 + c.get(Calendar.DAY_OF_YEAR);
			}
	};

	@Override
	public Transformer<GregorianCalendar, Integer> getCalendarTransformer() {
		return s_transformer;
	}

	public String getDescription() {
		return "for the first games of each day";
	}

	public void accept(GamePredicateVisitor visitor) {
		visitor.visit(this);
	}

}
