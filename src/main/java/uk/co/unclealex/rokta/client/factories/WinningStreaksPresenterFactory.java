package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.presenters.WinningStreaksPresenter;

public interface WinningStreaksPresenterFactory {

	WinningStreaksPresenter createWinningStreaksPresenter(GameFilter gameFilter);

}
