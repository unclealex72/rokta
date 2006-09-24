/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class CalendarFieldTransformer implements Transformer<Game, Integer> {

	private int i_calendarField;
	private Calendar i_calendar = new GregorianCalendar();
	
	/**
	 * @param calendarField
	 */
	public CalendarFieldTransformer(int calendarField) {
		super();
		i_calendarField = calendarField;
	}
	
	public Integer transform(Game game) {
		i_calendar.setTime(game.getDatePlayed());
		return i_calendar.get(i_calendarField);
	}
}
