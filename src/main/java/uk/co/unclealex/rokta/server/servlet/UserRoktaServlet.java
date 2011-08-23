package uk.co.unclealex.rokta.server.servlet;

import java.util.Date;

import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.service.UserRoktaService;

public class UserRoktaServlet extends AbstractRoktaServlet<UserRoktaService> implements UserRoktaService {

	@Override
	protected Class<UserRoktaService> getRoktaServiceClass() {
		return UserRoktaService.class;
	}

	@Override
	public InitialPlayers getInitialPlayers(Date date) {
		return createRoktaService().getInitialPlayers(date);
	}

	@Override
	public void submitGame(Game game) {
		createRoktaService().submitGame(game);
	}
	

}
