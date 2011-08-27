package uk.co.unclealex.rokta.client.factories;

import java.util.Map;

import uk.co.unclealex.rokta.client.presenters.HandCountPresenter;
import uk.co.unclealex.rokta.shared.model.Hand;

public interface HandCountPresenterFactory {

	HandCountPresenter createHandCountPresenter(Map<Hand, Long> openingHandCounts);

}
