package uk.co.unclealex.rokta.shared.service;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;

@RemoteServiceRelativePath("user")
public interface UserRoktaService extends RemoteService {
	
	public InitialPlayers getInitialPlayers(Date date);
	public void submitGame(Game game);
}
