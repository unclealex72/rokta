package uk.co.unclealex.rokta.actions;

import java.util.HashMap;
import java.util.Map;

import uk.co.unclealex.rokta.exceptions.InvalidRoundException;
import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.GameManager;

public class RoundAction extends PlayingAction {

	private Person i_loser;
	private Hand[] i_hands;
	
	@Override
	protected String prepareGameManager() throws InvalidRoundException {
		GameManager gameManager = getGameManager();
		if (getHands().length != getParticipants().length) {
			throw new InvalidRoundException();
		}
		
		Map<Person,Hand> plays = new HashMap<Person, Hand>();
		for (int idx = 0; idx < getHands().length; idx++) {
			plays.put(getParticipants()[idx], getHands()[idx]);
		}
		gameManager.nextRound(plays, getCounter(), getCurrentRound());
		if (gameManager.isFinished()) {
			setLoser(gameManager.getGame().getLoser());
			gameManager.finishGame();
			return "finished";
		}
		return gameManager.isFinished()?"finished":"next";
	}

	public Hand[] getHands() {
		return i_hands;
	}

	public void setHands(Hand[] hands) {
		i_hands = hands;
	}

	public Person getLoser() {
		return i_loser;
	}

	public void setLoser(Person loser) {
		i_loser = loser;
	}

}
