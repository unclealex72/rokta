package uk.co.unclealex.rokta.server.servlet;

import java.util.Date;

import uk.co.unclealex.rokta.shared.model.Colour;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.model.LoggedInUser;
import uk.co.unclealex.rokta.shared.service.UserRoktaService;

public class UserRoktaServlet extends AbstractRoktaServlet implements UserRoktaService {

	@Override
	public InitialPlayers getInitialPlayers(Date date) {
		return createRoktaService().getInitialPlayers(date);
	}

	@Override
	public void forceSignIn() {
		createRoktaService().forceSignIn();
	}
	
	@Override
	public void submitGame(Game game) {
		createRoktaService().submitGame(game);
	}
	
	@Override
	public void removeLastGame() {
		createRoktaService().removeLastGame();
	}
	
	@Override
	public void clearCache() {
		createRoktaService().clearCache();
	}

	@Override
	public LoggedInUser getCurrentlyLoggedInUser() {
		return createRoktaService().getCurrentlyLoggedInUser();
	}
	
	@Override
	public Colour getColourForUser(String username) {
		return createRoktaService().getColourForUser(username);
	}
	
	@Override
	public void updateColour(String username, Colour colour) {
		createRoktaService().updateColour(username, colour);
	}
	
	@Override
	public void updatePassword(String username, String newPassword) {
		createRoktaService().updatePassword(username, newPassword);
	}
}
