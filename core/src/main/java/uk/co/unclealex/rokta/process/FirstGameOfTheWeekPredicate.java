/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SortedSet;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class FirstGameOfTheWeekPredicate extends FirstGameInCalendarQuotientPredicate<Integer> {

	private static final Transformer<GregorianCalendar, Integer> s_transformer = 
		new Transformer<GregorianCalendar, Integer>() {
			public Integer transform(GregorianCalendar c) {
				return c.get(Calendar.YEAR) * 400 + c.get(Calendar.WEEK_OF_YEAR);
			}
	};

	public FirstGameOfTheWeekPredicate(SortedSet<Game> games) {
		super(games);
	}

	@Override
	public Transformer<GregorianCalendar, Integer> getCalendarTransformer() {
		return s_transformer;
	}

}
