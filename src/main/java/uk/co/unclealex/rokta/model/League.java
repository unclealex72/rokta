/**
 * 
 */
package uk.co.unclealex.rokta.model;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author alex
 *
 */
public class League {

	private boolean i_current;
	private SortedSet<LeagueRow> i_rows = new TreeSet<LeagueRow>();
	private int i_totalGames;
	private int i_totalPlayers;
	private String i_description;
	
	/**
	 * @return the totalGames
	 */
	public int getTotalGames() {
		return i_totalGames;
	}
	/**
	 * @param totalGames the totalGames to set
	 */
	public void setTotalGames(int totalGames) {
		i_totalGames = totalGames;
	}
	/**
	 * @return the totalPlayers
	 */
	public int getTotalPlayers() {
		return i_totalPlayers;
	}
	/**
	 * @param totalPlayers the totalPlayers to set
	 */
	public void setTotalPlayers(int totalPlayers) {
		i_totalPlayers = totalPlayers;
	}
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
	/**
	 * @return the description
	 */
	public String getDescription() {
		return i_description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		i_description = description;
	}
}
