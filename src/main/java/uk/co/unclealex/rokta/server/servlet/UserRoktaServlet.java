package uk.co.unclealex.rokta.server.servlet;

import java.util.Date;

import uk.co.unclealex.rokta.shared.model.ColourView;
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
	public ColourView getColourViewForUser(String username) {
		return createRoktaService().getColourViewForUser(username);
	}
	
	@Override
	public void updateColour(String username, ColourView colourView) {
		createRoktaService().updateColour(username, colourView);
	}
	
	@Override
	public void updatePassword(String username, String newPassword) {
		createRoktaService().updatePassword(username, newPassword);
	}
}
