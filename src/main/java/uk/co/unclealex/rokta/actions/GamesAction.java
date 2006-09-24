package uk.co.unclealex.rokta.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.views.GameView;

public class GamesAction extends BasicAction {

	private List<GameView> i_gameViews;
	
	@Override
	public String executeInternal() {
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
}
