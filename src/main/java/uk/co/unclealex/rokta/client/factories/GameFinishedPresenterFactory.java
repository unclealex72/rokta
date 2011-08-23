package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.presenters.GameFinishedPresenter;
import uk.co.unclealex.rokta.shared.model.Game;

public interface GameFinishedPresenterFactory {

	public GameFinishedPresenter createGameFinishedPresenter(Game game);
}
