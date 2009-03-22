/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import java.util.SortedMap;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.WinLoseCounter;
import uk.co.unclealex.rokta.process.StatisticsManager;

/**
 * @author alex
 *
 */
public class HeadToHeadsAction extends StatisticsAction {

	private SortedMap<Person,SortedMap<Person,WinLoseCounter>> i_headToHeadResultsByPerson;
	
	@Override
	protected String executeInternal() {
    StatisticsManager manager = getStatisticsManager();
    manager.setGames(getGames());
		setHeadToHeadResultsByPerson(manager.getHeadToHeadResultsByPerson());
		return SUCCESS;
	}

	/**
	 * @return the headToHeadResultsByPerson
	 */
	public SortedMap<Person, SortedMap<Person, WinLoseCounter>> getHeadToHeadResultsByPerson() {
		return i_headToHeadResultsByPerson;
	}

	/**
	 * @param headToHeadResultsByPerson the headToHeadResultsByPerson to set
	 */
	public void setHeadToHeadResultsByPerson(
			SortedMap<Person, SortedMap<Person, WinLoseCounter>> headToHeadResultsByPerson) {
		i_headToHeadResultsByPerson = headToHeadResultsByPerson;
	}
}
