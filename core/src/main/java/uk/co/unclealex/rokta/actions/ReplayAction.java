package uk.co.unclealex.rokta.actions;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.GameDao;

import com.opensymphony.xwork.ActionSupport;

public class ReplayAction extends ActionSupport implements GameStartingAction {

	private Person i_instigator;
	private Person[] i_participants;
	private Long i_replacingGameId;
	private GameDao i_gameDao;
	
	@Override
	public String execute() {
		Game game = getGameDao().getLastGame();
		setInstigator(game.getInstigator());
		setParticipants(game.getParticipants().toArray(new Person[0]));
		setReplacingGameId(game.getId());
		return SUCCESS;
	}
	
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

	public Long getReplacingGameId() {
		return i_replacingGameId;
	}

	public void setReplacingGameId(Long replacingGameId) {
		i_replacingGameId = replacingGameId;
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}
}
