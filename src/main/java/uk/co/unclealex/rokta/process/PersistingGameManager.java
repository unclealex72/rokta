package uk.co.unclealex.rokta.process;

import uk.co.unclealex.rokta.model.dao.GameDAO;

public class PersistingGameManager extends DefaultGameManager {

	private GameDAO i_GameDAO;
	
	@Override
	protected void finishGameInternal() {
		getGameDAO().store(getGame());
	}

	public GameDAO getGameDAO() {
		return i_GameDAO;
	}

	public void setGameDAO(GameDAO gameDAO) {
		i_GameDAO = gameDAO;
	}
}
