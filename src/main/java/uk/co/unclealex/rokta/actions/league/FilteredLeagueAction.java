/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.lang.StringUtils;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.DateFilterPredicate;

/**
 * @author alex
 *
 */
public class FilteredLeagueAction extends FilterGamesLeagueAction {

	private String i_selection;
	
	/**
	 * @return the selection
	 */
	public String getSelection() {
		return i_selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(String selection) {
		i_selection = selection;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.FilterGamesLeagueAction#getFilter()
	 */
	@Override
	public Predicate<Game> getFilter() {
		DateFormat dfByWeek = new SimpleDateFormat(DATE_FORMAT_WEEK);
		DateFormat dfByMonth = new SimpleDateFormat(DATE_FORMAT_MONTH);
		DateFormat dfByYear = new SimpleDateFormat(DATE_FORMAT_YEAR);
		
		Predicate<Game> filter;
		if (!StringUtils.isEmpty(getSelectedWeek())) {
			filter = new DateFilterPredicate(dfByWeek, getSelectedWeek());
			setSelection(getSelectedWeek());
		}
		else if (!StringUtils.isEmpty(getSelectedMonth())) {
			filter = new DateFilterPredicate(dfByMonth, getSelectedMonth());
			setSelection(getSelectedMonth());
		}
		else if (!StringUtils.isEmpty(getSelectedYear())) {
			filter = new DateFilterPredicate(dfByYear, getSelectedYear());
			setSelection(getSelectedYear());
		}
		else {
			throw new IllegalArgumentException("You must supply either a week, month or year.");
		}
		return filter;
	}
}
