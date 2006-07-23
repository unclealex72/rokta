package uk.co.unclealex.rokta.actions;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.PersonManager;


public class InitialiseAction extends BasicAction {

	private SortedSet<Person> i_availablePlayers;
	private Person i_exempt;
	
	private PersonManager i_personManager;
	
	@Override
	protected String executeInternal() {
		Person exempt = getPersonManager().getExemptPlayer(new Date());
		SortedSet<Person> availablePlayers = new TreeSet<Person>();
		availablePlayers.addAll(getEverybody());
		if (exempt != null) {
			availablePlayers.remove(exempt);
		}
		setExempt(exempt);
		setAvailablePlayers(availablePlayers);
		return SUCCESS;
	}

	/**
	 * @return the availablePlayers
	 */
	public SortedSet<Person> getAvailablePlayers() {
		return i_availablePlayers;
	}

	/**
	 * @param availablePlayers the availablePlayers to set
	 */
	public void setAvailablePlayers(SortedSet<Person> availablePlayers) {
		this.i_availablePlayers = availablePlayers;
	}

	/**
	 * @return the exempt
	 */
	public Person getExempt() {
		return i_exempt;
	}

	/**
	 * @param exempt the exempt to set
	 */
	public void setExempt(Person exempt) {
		i_exempt = exempt;
	}

	/**
	 * @return the personManager
	 */
	public PersonManager getPersonManager() {
		return i_personManager;
	}

	/**
	 * @param personManager the personManager to set
	 */
	public void setPersonManager(PersonManager personManager) {
		i_personManager = personManager;
	}
}
