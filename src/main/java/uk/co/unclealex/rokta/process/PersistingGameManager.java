package uk.co.unclealex.rokta.process;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.dao.GameDao;

public class PersistingGameManager extends DefaultGameManager {

	private transient GameDao i_gameDao;
	
	@Override
	protected void finishGameInternal(Long replacingGameId) {
		GameDao gameDao = getGameDao();
		Game game = getGame();
		if (replacingGameId != null) {
			Game replacingGame = gameDao.getGameById(replacingGameId.longValue());
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
