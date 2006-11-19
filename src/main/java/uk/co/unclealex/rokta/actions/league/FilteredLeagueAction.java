/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.DateFilterPredicate;

/**
 * @author alex
 *
 */
public class FilteredLeagueAction extends FilterGamesLeagueAction {

	private String i_filterType;
	private DateFormat i_titleDateFormat;
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.FilterGamesLeagueAction#getFilter()
	 */
	@Override
	public Predicate<Game> getFilter() {
		String type = getFilterType();
		String format;
		
		if ("week".equals(type)) {
			format = DATE_FORMAT_WEEK;
		}
		else if ("month".equals(type)) {
			format = DATE_FORMAT_MONTH;
		}
		else if ("year".equals(type)) {
			format = DATE_FORMAT_YEAR;
		}
		else {
			throw new IllegalArgumentException("You must supply either a week, month or year.");
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		setTitleDateFormat(dateFormat);
		return new DateFilterPredicate(dateFormat, getSelectedDate());
	}

	/**
	 * @return the filterType
	 */
	public String getFilterType() {
		return i_filterType;
	}

	/**
	 * @param filterType the filterType to set
	 */
	public void setFilterType(String filterType) {
		i_filterType = filterType;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.LeagueAction#getGraphTitle()
	 */
	@Override
	public String getGraphTitleInternal() {
		return "Graph for " + getTitleDateFormat().format(getSelectedDate());
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.LeagueAction#getLeagueTitle()
	 */
	@Override
	public String getLeagueTitleInternal() {
		return "League for " + getTitleDateFormat().format(getSelectedDate());
	}

	/**
	 * @return the titleDateFormat
	 */
	public DateFormat getTitleDateFormat() {
		return i_titleDateFormat;
	}

	/**
	 * @param titleDateFormat the titleDateFormat to set
	 */
	public void setTitleDateFormat(DateFormat titleDateFormat) {
		i_titleDateFormat = titleDateFormat;
	}
}
