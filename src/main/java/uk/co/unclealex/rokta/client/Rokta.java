package uk.co.unclealex.rokta.client;

import uk.co.unclealex.rokta.client.gin.RoktaGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Rokta implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		final RoktaGinjector injector = GWT.create(RoktaGinjector.class);

		injector.getMainPresenter().show(RootLayoutPanel.get());
		injector.getPlaceHistoryHandler().handleCurrentHistory();
	}

}
