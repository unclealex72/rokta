package uk.co.unclealex.rokta.actions;

import java.util.SortedSet;

import uk.co.unclealex.rokta.exceptions.InvalidRoundException;
import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.GameManager;

public abstract class PlayingAction extends RoktaAction {

	private GameManager i_gameManager;
	private Person[] i_participants;
	private Person i_counter;
	private int i_currentRound;
	private SortedSet<Person> i_everybody;
  
	@Override
	protected String executeInternal() throws InvalidRoundException {
    setEverybody(getPersonDao().getEverybody());
		String retval = prepareGameManager();
		GameManager gameManager = getGameManager();
		setParticipants(gameManager.getParticipants().toArray(new Person[0]));
		setCurrentRound(gameManager.getRounds() + 1);
		setCounter(gameManager.getCounter());
		return retval;
	}

	protected abstract String prepareGameManager() throws InvalidRoundException;

	public Hand[] getAllHands() {
		return Hand.values();
	}
	
	public GameManager getGameManager() {
		return i_gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		i_gameManager = gameManager;
	}

	public Person getCounter() {
		return i_counter;
	}

	public void setCounter(Person counter) {
		i_counter = counter;
	}

	public int getCurrentRound() {
		return i_currentRound;
	}

	public void setCurrentRound(int currentRound) {
		i_currentRound = currentRound;
	}

	public Person[] getParticipants() {
		return i_participants;
	}

	public void setParticipants(Person[] participants) {
		i_participants = participants;
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
