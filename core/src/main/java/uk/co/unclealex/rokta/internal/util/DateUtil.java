/**
 * 
 */
package uk.co.unclealex.rokta.util;

import org.joda.time.DateTime;

/**
 * @author alex
 *
 */
public interface DateUtil {

	public DateTime getStartOfDay(DateTime date);
	public DateTime getEndOfDay(DateTime date);
	public boolean areSameDay(DateTime d1, DateTime d2);
	
}
