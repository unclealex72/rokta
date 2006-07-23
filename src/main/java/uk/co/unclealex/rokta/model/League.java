/**
 * 
 */
package uk.co.unclealex.rokta.model;

import java.util.SortedSet;

/**
 * @author alex
 *
 */
public class League {

	private boolean i_current;
	private SortedSet<LeagueRow> i_rows;
	
	/**
	 * @return the current
	 */
	public boolean isCurrent() {
		return i_current;
	}
	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		i_current = current;
	}
	/**
	 * @return the rows
	 */
	public SortedSet<LeagueRow> getRows() {
		return i_rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(SortedSet<LeagueRow> rows) {
		i_rows = rows;
	}
}
