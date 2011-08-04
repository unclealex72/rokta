package uk.co.unclealex.rokta.client.facade;

import uk.co.unclealex.rokta.client.views.Game;

public interface RoktaFacade extends ReadOnlyRoktaFacade {

	public void submitGame(Game game);
}
