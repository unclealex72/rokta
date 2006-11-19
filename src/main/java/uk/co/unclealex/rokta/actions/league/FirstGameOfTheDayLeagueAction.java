/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.FirstGameOfTheDayPredicate;

/**
 * @author alex
 *
 */
public class FirstGameOfTheDayLeagueAction extends FilterGamesLeagueAction {

	@Override
	public Predicate<Game> getFilter() {
		return new FirstGameOfTheDayPredicate(getAllGames());
	}

	@Override
	public String getGraphTitleInternal() {
		return "First game of the day graph";
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.LeagueAction#getLeagueTitle()
	 */
	@Override
	public String getLeagueTitleInternal() {
		return "First game of the day league";
	}

}
