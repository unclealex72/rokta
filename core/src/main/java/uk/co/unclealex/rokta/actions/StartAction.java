package uk.co.unclealex.rokta.actions;

import java.util.Arrays;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.GameManager;
import uk.co.unclealex.rokta.spring.ApplicationContextAware;

public class StartAction extends PlayingAction implements ApplicationContextAware, GameStartingAction {

	private Person i_instigator;
	private Person[] i_participants;
	private ApplicationContext i_applicationContext;
	
	@Override
	protected String prepareGameManager() {
		GameManager gameManager = (GameManager) getApplicationContext().getBean("gameManager");
		gameManager.startGame(Arrays.asList(getParticipants()), getInstigator(), new Date());
		setGameManager(gameManager);
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

	public ApplicationContext getApplicationContext() {
		return i_applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		i_applicationContext = applicationContext;
	}

}
