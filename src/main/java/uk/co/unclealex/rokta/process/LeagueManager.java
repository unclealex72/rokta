/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;

/**
 * @author alex
 *
 */
public interface LeagueManager {

	public League generateLeague(Date now);

	public Comparator<LeagueRow> getComparator();

	public void setComparator(Comparator<LeagueRow> comparator);

	public Collection<Game> getCurrentGames();

	public void setCurrentGames(Collection<Game> currentGames);

	public Collection<Game> getPreviousGames();

	public void setPreviousGames(Collection<Game> previousGames);

	public Comparator<LeagueRow> getCompareByLossesPerGame();

}