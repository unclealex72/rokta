package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.presenters.GraphPresenter;

public interface GraphPresenterFactory {

	GraphPresenter createGraphPresenter(GameFilter gameFilter);

}
