package uk.co.unclealex.rokta.server.service;


import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;

public interface NewGameService {

	public InitialPlayers getInitialPlayers(Day day);

	public void submitGame(Game game);

	public void removeLastGame();

	public void updateGames();

}
