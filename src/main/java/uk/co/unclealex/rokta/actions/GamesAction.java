package uk.co.unclealex.rokta.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.dao.GameDAO;
import uk.co.unclealex.rokta.views.GameView;

import com.opensymphony.xwork.ActionSupport;

public class GamesAction extends ActionSupport {

	private List<GameView> i_gameViews;
	private GameDAO i_gameDAO;
	
	@Override
	public String execute() {
		List<GameView> gameViews = new ArrayList<GameView>();
		List<Game> games = new ArrayList<Game>();
		games.addAll(getGameDAO().getAllGames());
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

	public GameDAO getGameDAO() {
		return i_gameDAO;
	}

	public void setGameDAO(GameDAO gameDAO) {
		i_gameDAO = gameDAO;
	}
}
