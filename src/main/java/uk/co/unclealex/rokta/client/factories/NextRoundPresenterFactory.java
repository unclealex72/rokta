package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.presenters.NextRoundPresenter;
import uk.co.unclealex.rokta.shared.model.Game;

public interface NextRoundPresenterFactory {

	public NextRoundPresenter createNextRoundPresenter(Game game);
}
