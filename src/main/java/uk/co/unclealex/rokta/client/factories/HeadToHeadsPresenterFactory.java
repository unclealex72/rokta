package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.presenters.HeadToHeadsPresenter;

public interface HeadToHeadsPresenterFactory {

	HeadToHeadsPresenter createHeadToHeadsPresenter(GameFilter gameFilter);
}
