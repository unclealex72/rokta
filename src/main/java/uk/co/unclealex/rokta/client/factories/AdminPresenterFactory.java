package uk.co.unclealex.rokta.client.factories;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.presenters.AdminPresenter;

public interface AdminPresenterFactory {

	public AdminPresenter createAdminPresenter(GameFilter gameFilter);
}
