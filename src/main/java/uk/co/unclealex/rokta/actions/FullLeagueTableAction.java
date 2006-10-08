/**
 * 
 */
package uk.co.unclealex.rokta.actions;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;

/**
 * @author alex
 *
 */
public class FullLeagueTableAction extends LeagueTableAction {

	@Override
	public SortedSet<Game> getGames() {
		return getGameDao().getAllGames();
	}
}
