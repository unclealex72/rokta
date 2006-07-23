package uk.co.unclealex.rokta.process;

import uk.co.unclealex.rokta.model.dao.GameDao;

public class PersistingGameManager extends DefaultGameManager {

	private GameDao i_GameDao;
	
	@Override
	protected void finishGameInternal() {
		getGameDao().store(getGame());
	}

	public GameDao getGameDao() {
		return i_GameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_GameDao = gameDao;
	}
}
