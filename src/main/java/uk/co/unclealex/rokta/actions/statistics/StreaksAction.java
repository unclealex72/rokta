/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.ComparatorUtils;
import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Streak;

/**
 * @author alex
 *
 */
public abstract class StreaksAction extends StatisticsAction {

	private static final int MAX_ITEMS = 10;
	
	private List<Streak> i_topStreaks;
	private List<Streak> i_currentStreaks;
	
	public abstract Map<Person, List<Streak>> getStreakListsByPerson();
	
	@Override
	public String executeInternal() {
		getStatisticsManager().setGames(getGames());
		Map<Person, List<Streak>> streakListsByPerson = getStreakListsByPerson();
		List<Streak> streaks = new ArrayList<Streak>();
		for (List<Streak> streakList : streakListsByPerson.values()) {
			streaks.addAll(streakList);
		}
	
		Streak[] streakArray = streaks.toArray(new Streak[streaks.size()]);
		Arrays.sort(streakArray, ComparatorUtils.NATURAL_COMPARATOR);
	
		int itemCount = Math.min(MAX_ITEMS, streakArray.length);
		streaks = new ArrayList<Streak>(itemCount);
		int previousLength = -1;
		for (
				int idx = 0;
				idx < streakArray.length &&
					(idx < itemCount || streakArray[idx].getLength() == previousLength);
				idx++) {
			streaks.add(streakArray[idx]);
			previousLength = streakArray[idx].getLength();
		}
		setTopStreaks(streaks);
		
		SortedMap<Person, Streak> currentStreaksByPerson = new TreeMap<Person, Streak>();
		Predicate<Streak> isCurrentPredicate = new Predicate<Streak>() {
			public boolean evaluate(Streak streak) {
				return streak.isCurrent();
			}
		};
		for (Map.Entry<Person, List<Streak>> entry : streakListsByPerson.entrySet()) {
			Person person = entry.getKey();
			Streak currentStreak = CollectionUtils.find(entry.getValue(), isCurrentPredicate);
			if (currentStreak != null) {
				currentStreaksByPerson.put(person, currentStreak);
			}
		}
		Collection<Streak> values = currentStreaksByPerson.values();
		List<Streak> currentStreaks = new ArrayList<Streak>(values.size());
		currentStreaks.addAll(values);
		Collections.sort(currentStreaks);
		setCurrentStreaks(currentStreaks);
		return SUCCESS;
	}

	/**
	 * @return the streaks
	 */
	public List<Streak> getTopStreaks() {
		return i_topStreaks;
	}

	/**
	 * @param streaks the streaks to set
	 */
	public void setTopStreaks(List<Streak> streaks) {
		i_topStreaks = streaks;
	}

	/**
	 * @return the currentStreaks
	 */
	public List<Streak> getCurrentStreaks() {
		return i_currentStreaks;
	}

	/**
	 * @param currentStreaks the currentStreaks to set
	 */
	public void setCurrentStreaks(List<Streak> currentStreaks) {
		i_currentStreaks = currentStreaks;
	}

}
