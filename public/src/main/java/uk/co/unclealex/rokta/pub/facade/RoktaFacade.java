package uk.co.unclealex.rokta.pub.facade;

import uk.co.unclealex.rokta.pub.views.Game;

public interface RoktaFacade extends ReadOnlyRoktaFacade {

	public void submitGame(Game game);
}
