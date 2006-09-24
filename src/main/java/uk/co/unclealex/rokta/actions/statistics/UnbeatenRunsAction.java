/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.ComparatorUtils;

import uk.co.unclealex.rokta.actions.BasicAction;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Streak;
import uk.co.unclealex.rokta.process.StatisticsManager;
import uk.co.unclealex.rokta.views.StreakView;

/**
 * @author alex
 *
 */
public class UnbeatenRunsAction extends BasicAction {

	private static final int MAX_ITEMS = 10;
	
	private StatisticsManager i_statisticsManager;
	private List<StreakView> i_streakViews;
	
	@Override
	public String executeInternal() {
		Map<Person, List<Streak>> streakListsByPerson = getStatisticsManager().getStreaksByPerson();
		List<StreakView> streakViews = new ArrayList<StreakView>();
		for (List<Streak> streakList : streakListsByPerson.values()) {
			CollectionUtils.collect(streakList, StreakView.getStreakViewTransformer(), streakViews);
		}
	
		StreakView[] streakViewArray = streakViews.toArray(new StreakView[streakViews.size()]);
		Arrays.sort(streakViewArray, ComparatorUtils.NATURAL_COMPARATOR);
	
		int itemCount = Math.min(MAX_ITEMS, streakViewArray.length);
		streakViews = new ArrayList<StreakView>(itemCount);
		for (int idx = 0; idx < itemCount; idx++) {
			streakViews.add(streakViewArray[idx]);
		}
		setStreakViews(streakViews);
		return SUCCESS;
	}

	/**
	 * @return the statisticsManager
	 */
	public StatisticsManager getStatisticsManager() {
		return i_statisticsManager;
	}

	/**
	 * @param statisticsManager the statisticsManager to set
	 */
	public void setStatisticsManager(StatisticsManager statisticsManager) {
		i_statisticsManager = statisticsManager;
	}

	/**
	 * @return the streakViews
	 */
	public List<StreakView> getStreakViews() {
		return i_streakViews;
	}

	/**
	 * @param streakViews the streakViews to set
	 */
	public void setStreakViews(List<StreakView> streakViews) {
		i_streakViews = streakViews;
	}
}
