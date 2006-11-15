/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.FirstGameOfTheWeekPredicate;

/**
 * @author alex
 *
 */
public class FirstGameOfTheWeekLeagueAction extends FilterGamesLeagueAction {

	@Override
	public Predicate<Game> getFilter() {
		return new FirstGameOfTheWeekPredicate(getAllGames());
	}

}
