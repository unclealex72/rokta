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

	@Override
	public final SortedSet<Game> getGames() {
		SortedSet<Game> filteredGames = new TreeSet<Game>();
		filteredGames.addAll(CollectionUtils.select(getAllGames(), getFilter()));
		return filteredGames;
	}
	
	public abstract Predicate<Game> getFilter();

}
