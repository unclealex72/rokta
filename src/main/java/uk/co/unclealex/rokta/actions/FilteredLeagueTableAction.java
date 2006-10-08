/**
 * 
 */
package uk.co.unclealex.rokta.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.SortedSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.DateFilterPredicate;

/**
 * @author alex
 *
 */
public class FilteredLeagueTableAction extends LeagueTableAction {

	private String i_selection;
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.LeagueTableAction#getGames()
	 */
	@Override
	public SortedSet<Game> getGames() {
		SortedSet<Game> games = getGameDao().getAllGames();
		
		DateFormat dfByWeek = new SimpleDateFormat(DATE_FORMAT_WEEK);
		DateFormat dfByMonth = new SimpleDateFormat(DATE_FORMAT_MONTH);
		DateFormat dfByYear = new SimpleDateFormat(DATE_FORMAT_YEAR);
		
		if (!StringUtils.isEmpty(getSelectedWeek())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByWeek, getSelectedWeek()));
			setSelection(getSelectedWeek());
		}
		else if (!StringUtils.isEmpty(getSelectedMonth())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByMonth, getSelectedMonth()));
			setSelection(getSelectedMonth());
		}
		else if (!StringUtils.isEmpty(getSelectedYear())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByYear, getSelectedYear()));
			setSelection(getSelectedYear());
		}
		return games;
	}

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

}
