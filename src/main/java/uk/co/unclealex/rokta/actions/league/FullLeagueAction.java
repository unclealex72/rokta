/**
 * 
 */
package uk.co.unclealex.rokta.actions.league;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.league.milestone.LazyAndLeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.league.milestone.LeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.league.milestone.SinceLeagueMilestonePredicate;

/**
 * @author alex
 *
 */
public class FullLeagueAction extends LeagueAction {

	@Override
	public SortedSet<Game> getGames() {
		return getAllGames();
	}

	/**
	 * Take the default predicate but only show games less than a year ago.
	 */
	@Override
	public LeagueMilestonePredicate produceGraphLeagueMilestonePredicate(SortedSet<Game> games) {
		List<LeagueMilestonePredicate> predicates = new ArrayList<LeagueMilestonePredicate>(2);
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.YEAR, -1);
		predicates.add(new SinceLeagueMilestonePredicate(calendar.getTime()));
		predicates.add(super.produceGraphLeagueMilestonePredicate(games));
		return new LazyAndLeagueMilestonePredicate(predicates);
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.LeagueAction#getGraphTitle()
	 */
	@Override
	public String getGraphTitleInternal() {
		return "Full graph";
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.league.LeagueAction#getLeagueTitle()
	 */
	@Override
	public String getLeagueTitleInternal() {
		return "Full league";
	}
}
