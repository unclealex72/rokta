package uk.co.unclealex.rokta.actions;

import java.util.Arrays;
import java.util.Date;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.GameManager;

public class StartAction extends PlayingAction {

	private Person i_instigator;
	private Person[] i_participants;
	
	@Override
	protected String prepareGameManager() {
		GameManager gameManager = getGameManager();
		gameManager.startGame(Arrays.asList(getParticipants()), getInstigator(), new Date());
		return SUCCESS;
	}

	public Person getInstigator() {
		return i_instigator;
	}

	public void setInstigator(Person instigator) {
		i_instigator = instigator;
	}

	@Override
	public Person[] getParticipants() {
		return i_participants;
	}

	@Override
	public void setParticipants(Person[] participants) {
		i_participants = participants;
	}

}
