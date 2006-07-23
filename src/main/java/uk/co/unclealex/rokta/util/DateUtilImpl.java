/**
 * 
 */
package uk.co.unclealex.rokta.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author alex
 *
 */
public class DateUtilImpl implements DateUtil {

	private DateFormat i_dayOnlyFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public boolean areSameDay(Date d1, Date d2) {
		String s1 = i_dayOnlyFormat.format(d1);
		String s2 = i_dayOnlyFormat.format(d2);
		return s1.equals(s2);
	}

	public Date getStartOfDay(Date date) {
		String sDate = i_dayOnlyFormat.format(date);
		Date result;
		try {
			result = i_dayOnlyFormat.parse(sDate);
		} catch (ParseException e) {
			result = null;
		}
		return result;
	}

	public Date getEndOfDay(Date date) {
		Date startOfDay = getStartOfDay(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startOfDay);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

}
