package uk.co.unclealex.rokta.actions;

import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.GameDao;

public class GameStartingAction extends RoktaAction {

	private Person i_instigator;
	private Person[] i_participants;
	private Integer i_replacingGameId;
	private GameDao i_gameDao;
	
	public Person getInstigator() {
		return i_instigator;
	}

	public void setInstigator(Person instigator) {
		i_instigator = instigator;
	}

	public Person[] getParticipants() {
		return i_participants;
	}

	public void setParticipants(Person[] participants) {
		i_participants = participants;
	}

	public Integer getReplacingGameId() {
		return i_replacingGameId;
	}

	public void setReplacingGameId(Integer replacingGameId) {
		i_replacingGameId = replacingGameId;
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

}