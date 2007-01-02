package uk.co.unclealex.rokta.process;

import uk.co.unclealex.rokta.model.dao.GameDao;

public class PersistingGameManager extends DefaultGameManager {

	private transient GameDao i_gameDao;
	
	@Override
	protected void finishGameInternal() {
		getGameDao().store(getGame());
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}
}
