/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class SinceLeagueAction extends LeagueAction {

	private static DateFormat s_titleDateFormat = new SimpleDateFormat("d MMMMM, yyyy");
	
	private DateFormat i_dateFormat;
	private String i_since;
	private Date i_selection;
	
	@Override
	public SortedSet<Game> getGames() throws ParseException {
		Date since = getDateFormat().parse(getSince());
		setSelection(since);
		return getGameDao().getGamesSince(since);
	}

	/**
	 * @return the dateFormat
	 */
	public DateFormat getDateFormat() {
		return i_dateFormat;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(DateFormat dateFormat) {
		i_dateFormat = dateFormat;
	}

	/**
	 * @return the since
	 */
	public String getSince() {
		return i_since;
	}

	/**
	 * @param since the since to set
	 */
	public void setSince(String since) {
		i_since = since;
	}

	/**
	 * @return the selection
	 */
	public Date getSelection() {
		return i_selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(Date selection) {
		i_selection = selection;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.LeagueAction#getGraphTitle()
	 */
	@Override
	public String getGraphTitleInternal() {
		return "Graph for games since " + s_titleDateFormat.format(getSelection());
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.LeagueAction#getLeagueTitle()
	 */
	@Override
	public String getLeagueTitleInternal() {
		return "League for games since " + s_titleDateFormat.format(getSelection());
	}
}
