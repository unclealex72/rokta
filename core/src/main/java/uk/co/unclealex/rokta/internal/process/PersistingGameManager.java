package uk.co.unclealex.rokta.internal.process;

import uk.co.unclealex.rokta.internal.dao.GameDao;
import uk.co.unclealex.rokta.pub.model.Game;

public class PersistingGameManager extends DefaultGameManager {

	private transient GameDao i_gameDao;
	
	@Override
	protected void finishGameInternal(Integer replacingGameId) {
		GameDao gameDao = getGameDao();
		Game game = getGame();
		if (replacingGameId != null) {
			Game replacingGame = gameDao.findById(replacingGameId.intValue());
			game.setDatePlayed(replacingGame.getDatePlayed());
			gameDao.remove(replacingGame);
		}
		gameDao.store(game);
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}
}
