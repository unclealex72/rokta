package uk.co.unclealex.rokta.shared.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uk.co.unclealex.rokta.shared.model.ColourView;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.model.LoggedInUser;

@RemoteServiceRelativePath("user")
public interface UserRoktaService extends RemoteService {
	
	public InitialPlayers getInitialPlayers(Date date);
	public void submitGame(Game game);
	
	public void clearCache();
	
	public void removeLastGame();
	
	public void updateColour(String username, ColourView colourView);
	
	public void updatePassword(String username, String newPassword);
	
	public LoggedInUser getCurrentlyLoggedInUser();
	
	public ColourView getColourViewForUser(String username);

	public void forceSignIn();
}
