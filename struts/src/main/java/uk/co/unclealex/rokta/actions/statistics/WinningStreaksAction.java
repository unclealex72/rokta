/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import java.util.List;
import java.util.Map;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.views.Streak;

/**
 * @author alex
 *
 */
public class WinningStreaksAction extends StreaksAction {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.actions.statistics.StreaksAction#getStreakListsByPerson()
	 */
	@Override
	public Map<Person, List<Streak>> getStreakListsByPerson() {
		return getStatisticsManager().getWinningStreakListsByPerson();
	}

}
