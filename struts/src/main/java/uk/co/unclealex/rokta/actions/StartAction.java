package uk.co.unclealex.rokta.actions;

import java.util.Arrays;
import java.util.Date;

import uk.co.unclealex.rokta.process.GameManager;

public class StartAction extends PlayingAction {

	@Override
	protected String prepareGameManager() {
		GameManager gameManager = (GameManager) getApplicationContext().getBean("gameManager");
		gameManager.startGame(Arrays.asList(getParticipants()), getInstigator(), new Date());
		setGameManager(gameManager);
		return SUCCESS;
	}
}
