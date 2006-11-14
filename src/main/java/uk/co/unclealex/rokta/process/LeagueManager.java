/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.process.league.milestone.LeagueMilestonePredicate;

/**
 * @author alex
 *
 */
public interface LeagueManager {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#generateLeague(java.util.Date)
	 */
	public List<SortedMap<Game, League>> generateLeagues();

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#getComparator()
	 */
	public Comparator<LeagueRow> getComparator();

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.LeagueManager#setComparator(java.util.Comparator)
	 */
	public void setComparator(Comparator<LeagueRow> comparator);

	/**
	 * @return the games
	 */
	public SortedSet<Game> getGames();

	/**
	 * @param games the games to set
	 */
	public void setGames(SortedSet<Game> games);

	/**
	 * @return the leagueMilestonePredicate
	 */
	public List<LeagueMilestonePredicate> getLeagueMilestonePredicates();

	/**
	 * @param leagueMilestonePredicates the leagueMilestonePredicate to set
	 */
	public void setLeagueMilestonePredicates(
			List<LeagueMilestonePredicate> leagueMilestonePredicates);

	/**
	 * @return the currentDate
	 */
	public Date getCurrentDate();

	/**
	 * @param currentDate the currentDate to set
	 */
	public void setCurrentDate(Date currentDate);

	public Comparator<LeagueRow> getCompareByLossesPerGame();

	/**
	 * @return
	 */
	public List<SortedMap<Game, League>> getLeaguesByGameForPredicates();

}