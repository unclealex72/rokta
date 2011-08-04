/**
 * 
 */
package uk.co.unclealex.rokta.server.util;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * @author alex
 *
 */
public interface DateUtil {

	public DateTime getStartOfDay(DateTime date);
	public DateTime getEndOfDay(DateTime date);
	public boolean areSameDay(DateTime d1, DateTime d2);
	public boolean areSameDay(Date d1, Date d2);
	
}
