/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.text.DateFormat;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Game;

public class DateFilterPredicate implements Predicate<Game> {
	
	private DateFormat i_dateFormat;
	private String i_selected;

	public DateFilterPredicate(DateFormat dateFormat, String selected) {
		i_dateFormat = dateFormat;
		i_selected = selected;
	}

	public boolean evaluate(Game game) {
		return getDateFormat().format(game.getDatePlayed()).equals(getSelected());
	}
	
	public DateFormat getDateFormat() {
		return i_dateFormat;
	}
	public String getSelected() {
		return i_selected;
	}
}
