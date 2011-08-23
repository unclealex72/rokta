package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.presenters.LosingStreaksPresenter;

public interface LosingStreaksPresenterFactory {

	LosingStreaksPresenter createLosingStreaksPresenter(GameFilter gameFilter);

}
