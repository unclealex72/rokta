package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.presenters.GamePresenter;
import uk.co.unclealex.rokta.shared.model.Game;

public interface GamePresenterFactory {

	public GamePresenter createGamePresenter(Game game);
}
