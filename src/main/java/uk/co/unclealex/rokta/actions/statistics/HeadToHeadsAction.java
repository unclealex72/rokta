/**
 * 
 */
package uk.co.unclealex.rokta.actions.statistics;

import java.util.SortedMap;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.WinLoseCounter;

/**
 * @author alex
 *
 */
public class HeadToHeadsAction extends StatisticsAction {

	private SortedSet<Person> i_players;
	private SortedMap<Person,SortedMap<Person,WinLoseCounter>> i_headToHeadResultsByPerson;
	
	@Override
	protected String executeInternal() {
		setPlayers(getPersonDao().getPlayers());
		setHeadToHeadResultsByPerson(getStatisticsManager().getHeadToHeadResultsByPerson());
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

	/**
	 * @return the players
	 */
	public SortedSet<Person> getPlayers() {
		return i_players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(SortedSet<Person> players) {
		i_players = players;
	}

}
