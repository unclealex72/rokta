/**
 * 
 */
package uk.co.unclealex.rokta.util;

import java.util.Date;

/**
 * @author alex
 *
 */
public interface DateUtil {

	public Date getStartOfDay(Date date);
	public Date getEndOfDay(Date date);
	public boolean areSameDay(Date d1, Date d2);
	
}
