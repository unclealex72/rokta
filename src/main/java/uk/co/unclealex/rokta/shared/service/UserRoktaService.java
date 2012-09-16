package uk.co.unclealex.rokta.shared.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uk.co.unclealex.rokta.shared.model.Colour;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.model.LoggedInUser;

@RemoteServiceRelativePath("user")
public interface UserRoktaService extends RemoteService {
	
	public InitialPlayers getInitialPlayers(Date date);
	public void submitGame(Game game);
	
	public void clearCache();
	
	public void removeLastGame();
	
	public void updateColour(String username, Colour colourView);
	
	public LoggedInUser getCurrentlyLoggedInUser();
	
	public Colour getColourForUser(String username);

	public void forceSignIn();
}
