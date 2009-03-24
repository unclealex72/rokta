package uk.co.unclealex.rokta.actions;

import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.PersonManager;


public class InitialiseAction extends RoktaAction {

	private SortedSet<Person> i_availablePlayers;
  private SortedSet<Person> i_everybody;
	private Person i_exempt;
	
	private PersonManager i_personManager;
	
	@Override
	protected String executeInternal() {
		Person exempt = getPersonManager().getExemptPlayer(new Date());
		SortedSet<Person> availablePlayers = new TreeSet<Person>(new PlayersFirstComparator());
		availablePlayers.addAll(getPersonDao().getAll());
		if (exempt != null) {
			availablePlayers.remove(exempt);
		}
		setExempt(exempt);
		setAvailablePlayers(availablePlayers);
    setEverybody(getPersonDao().getAll());
		return SUCCESS;
	}

	private class PlayersFirstComparator implements Comparator<Person> {
		public int compare(Person person1, Person person2) {
			SortedSet<Person> players = getPlayers();
			boolean isPlayer1 = players.contains(person1);
			boolean isPlayer2 = players.contains(person2);
			if (isPlayer1 == isPlayer2) {
				return person1.compareTo(person2);
			}
			else if (isPlayer1 && !isPlayer2) {
				return -1;
			}
			else {
				return 1;
			}
		}
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

  /**
   * @return the everybody
   */
  public SortedSet<Person> getEverybody() {
    return i_everybody;
  }

  /**
   * @param everybody the everybody to set
   */
  public void setEverybody(SortedSet<Person> everybody) {
    i_everybody = everybody;
  }
}
