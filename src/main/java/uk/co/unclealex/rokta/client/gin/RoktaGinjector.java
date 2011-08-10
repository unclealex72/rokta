package uk.co.unclealex.rokta.client.gin;

import uk.co.unclealex.rokta.client.presenters.MainPresenter;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.place.shared.PlaceHistoryHandler;

@GinModules({ RoktaClientModule.class, RoktaInternalModule.class })
public interface RoktaGinjector {

	MainPresenter getMainPresenter();
	PlaceHistoryHandler getPlaceHistoryHandler();

}
