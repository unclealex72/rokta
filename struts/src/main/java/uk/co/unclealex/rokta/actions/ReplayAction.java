package uk.co.unclealex.rokta.actions;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;

public class ReplayAction extends GameStartingAction {

	@Override
	public String executeInternal() {
		Game game = getGameDao().getLastGame();
		setInstigator(game.getInstigator());
		setParticipants(game.getParticipants().toArray(new Person[0]));
		setReplacingGameId(game.getId());
		return SUCCESS;
	}	
}
