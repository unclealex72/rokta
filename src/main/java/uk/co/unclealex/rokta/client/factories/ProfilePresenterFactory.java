package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.presenters.ProfilePresenter;

public interface ProfilePresenterFactory {

	public ProfilePresenter createProfilePresenter(GameFilter gameFilter, String username);
}
