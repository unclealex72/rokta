/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.ComparatorUtils;
import org.apache.commons.collections15.Predicate;

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
	private List<StreakView> i_topStreakViews;
	private List<StreakView> i_currentStreakViews;
	
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
		setTopStreakViews(streakViews);
		
		SortedMap<Person, StreakView> currentStreakViewsByPerson = new TreeMap<Person, StreakView>();
		Predicate<Streak> isCurrentPredicate = new Predicate<Streak>() {
			public boolean evaluate(Streak streak) {
				return streak.isCurrent();
			}
		};
		for (Map.Entry<Person, List<Streak>> entry : streakListsByPerson.entrySet()) {
			Person person = entry.getKey();
			List<Streak> streaks = entry.getValue();
			Streak currentStreak = CollectionUtils.find(streaks, isCurrentPredicate);
			if (currentStreak != null) {
				currentStreakViewsByPerson.put(person, StreakView.getStreakViewTransformer().transform(currentStreak));
			}
		}
		List<StreakView> currentStreakViews = new LinkedList<StreakView>();
		currentStreakViews.addAll(currentStreakViewsByPerson.values());
		setCurrentStreakViews(currentStreakViews);
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
	public List<StreakView> getTopStreakViews() {
		return i_topStreakViews;
	}

	/**
	 * @param streakViews the streakViews to set
	 */
	public void setTopStreakViews(List<StreakView> streakViews) {
		i_topStreakViews = streakViews;
	}

	/**
	 * @return the currentStreaks
	 */
	public List<StreakView> getCurrentStreakViews() {
		return i_currentStreakViews;
	}

	/**
	 * @param currentStreaks the currentStreaks to set
	 */
	public void setCurrentStreakViews(List<StreakView> currentStreaks) {
		i_currentStreakViews = currentStreaks;
	}

}
