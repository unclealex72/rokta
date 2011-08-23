package uk.co.unclealex.rokta.server.service;

import java.util.Date;

import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;

public interface NewGameService {

	public InitialPlayers getInitialPlayers(Date date);

	public void submitGame(Game game);

}
