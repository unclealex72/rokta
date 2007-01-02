/**
 * 
 */
package uk.co.unclealex.rokta.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class CalendarFieldComparator implements Comparator<Game> {

	private static Map<Integer,Integer[]> s_fields = new HashMap<Integer, Integer[]>();
	static {
		s_fields.put(Calendar.DAY_OF_YEAR, new Integer[] { Calendar.YEAR });
		s_fields.put(Calendar.WEEK_OF_YEAR, new Integer[] { Calendar.YEAR });
		s_fields.put(Calendar.MONTH, new Integer[] { Calendar.YEAR });
	}
	
	private int i_calendarField;
	
	/**
	 * @param calendarField
	 */
	public CalendarFieldComparator(int calendarField) {
		super();
		i_calendarField = calendarField;
	}
	
	public int compare(Game g1, Game g2) {
		Integer[] extraCompares = s_fields.get(i_calendarField);
		if (extraCompares == null) {
			throw new IllegalArgumentException(
					"Calendar field " + i_calendarField + " is not a valid comparator argument.");
		}
		List<Integer> allCompares = new ArrayList<Integer>(extraCompares.length + 1);
		allCompares.add(i_calendarField);
		allCompares.addAll(Arrays.asList(extraCompares));
		
		Calendar c1 = new GregorianCalendar();
		c1.setTime(g1.getDatePlayed());
		Calendar c2 = new GregorianCalendar();
		c2.setTime(g2.getDatePlayed());
		
		int cmp = 0;
		for (Iterator<Integer> iter = allCompares.iterator(); iter.hasNext() && cmp != 0; ) {
			int cmpField = iter.next();
			Integer datePart1 = c1.get(cmpField);
			Integer datePart2 = c2.get(cmpField);
			cmp = datePart1.compareTo(datePart2);
		}
		return cmp;
	}
}
