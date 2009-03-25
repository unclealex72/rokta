/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.internal.model.Game;

public class DateFilterPredicate implements Predicate<Game> {
	
	private DateFormat i_dateFormat;
	private Date i_selected;

	public DateFilterPredicate(DateFormat dateFormat, Date selected) {
		i_dateFormat = dateFormat;
		i_selected = selected;
	}

	public boolean evaluate(Game game) {
		DateFormat fmt = getDateFormat();
		return fmt.format(game.getDatePlayed()).equals(fmt.format(getSelected()));
	}
	
	public DateFormat getDateFormat() {
		return i_dateFormat;
	}
	public Date getSelected() {
		return i_selected;
	}
}
