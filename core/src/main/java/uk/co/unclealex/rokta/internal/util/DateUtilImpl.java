/**
 * 
 */
package uk.co.unclealex.rokta.internal.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * @author alex
 *
 */
@Service
public class DateUtilImpl implements DateUtil {

	public boolean areSameDay(DateTime d1, DateTime d2) {
		return getStartOfDay(d1).isEqual(getStartOfDay(d2));
	}

	@Override
	public boolean areSameDay(Date d1, Date d2) {
		return areSameDay(new DateTime(d1), new DateTime(d2));
	}
	
	public DateTime getStartOfDay(DateTime date) {
		return date.withTime(0, 0, 0, 0);
	}

	public DateTime getEndOfDay(DateTime date) {
		return getStartOfDay(date).plusDays(1).minusMillis(1);
	}

}
