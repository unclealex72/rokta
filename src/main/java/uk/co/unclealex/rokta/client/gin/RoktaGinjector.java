package uk.co.unclealex.rokta.client.gin;

import uk.co.unclealex.rokta.client.presenters.AuthenticationPresenter;
import uk.co.unclealex.rokta.client.presenters.MainPresenter;
import uk.co.unclealex.rokta.client.presenters.NavigationPresenter;
import uk.co.unclealex.rokta.client.presenters.NewsPresenter;
import uk.co.unclealex.rokta.client.presenters.TitlePresenter;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;

@GinModules({ RoktaClientModule.class, RoktaInternalModule.class })
public interface RoktaGinjector extends Ginjector {

	MainPresenter getMainPresenter();
	TitlePresenter getTitlePresenter();
	PlaceHistoryHandler getPlaceHistoryHandler();
	ActivityManager getActivityMapper();
	NavigationPresenter getNavigationPresenter();
	AuthenticationPresenter getAuthenticationPresenter();
	NewsPresenter getNewsPresenter();
}
