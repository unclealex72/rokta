/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Comparator;
import java.util.Date;
import java.util.SortedMap;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;

/**
 * @author alex
 *
 */
public interface LeagueManager {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeague(java.util.Date)
	 */
	public abstract SortedMap<Game, League> generateLeagues();

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getComparator()
	 */
	public abstract Comparator<LeagueRow> getComparator();

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setComparator(java.util.Comparator)
	 */
	public abstract void setComparator(Comparator<LeagueRow> comparator);

	/**
	 * @return the games
	 */
	public abstract SortedSet<Game> getGames();

	/**
	 * @param games the games to set
	 */
	public abstract void setGames(SortedSet<Game> games);

	/**
	 * @return the leagueMilestonePredicate
	 */
	public abstract LeagueMilestonePredicate getLeagueMilestonePredicate();

	/**
	 * @param leagueMilestonePredicate the leagueMilestonePredicate to set
	 */
	public abstract void setLeagueMilestonePredicate(
			LeagueMilestonePredicate leagueMilestonePredicate);

	/**
	 * @return the currentDate
	 */
	public abstract Date getCurrentDate();

	/**
	 * @param currentDate the currentDate to set
	 */
	public abstract void setCurrentDate(Date currentDate);

	public Comparator<LeagueRow> getCompareByLossesPerGame();

}