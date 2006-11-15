/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public abstract class FilterGamesLeagueAction extends LeagueAction {

	private SortedSet<Game> i_allGames;
	
	@Override
	public final SortedSet<Game> getGames() {
		SortedSet<Game> allGames = getGameDao().getAllGames();
		setAllGames(allGames);
		SortedSet<Game> filteredGames = new TreeSet<Game>();
		filteredGames.addAll(CollectionUtils.select(allGames, getFilter()));
		return filteredGames;
	}
	
	public abstract Predicate<Game> getFilter();

	/**
	 * @return the allGames
	 */
	public SortedSet<Game> getAllGames() {
		return i_allGames;
	}

	/**
	 * @param allGames the allGames to set
	 */
	public void setAllGames(SortedSet<Game> allGames) {
		i_allGames = allGames;
	}
}
