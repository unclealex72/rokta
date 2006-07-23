package uk.co.unclealex.rokta.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.views.GameView;

import com.opensymphony.xwork.ActionSupport;

public class GamesAction extends ActionSupport {

	private List<GameView> i_gameViews;
	private GameDao i_gameDao;
	
	@Override
	public String execute() {
		List<GameView> gameViews = new ArrayList<GameView>();
		List<Game> games = new ArrayList<Game>();
		games.addAll(getGameDao().getAllGames());
		Collections.reverse(games);
		
		for (Game game : games) {
			gameViews.add(new GameView(game));
		}
		
		setGameViews(gameViews);
		return SUCCESS;
	}
	
	public List<GameView> getGameViews() {
		return i_gameViews;
	}

	public void setGameViews(List<GameView> gameViews) {
		i_gameViews = gameViews;
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}
}
