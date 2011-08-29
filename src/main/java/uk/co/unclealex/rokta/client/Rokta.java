package uk.co.unclealex.rokta.client;

import org.danvk.Dygraphs;

import uk.co.unclealex.rokta.client.gin.RoktaGinjector;
import uk.co.unclealex.rokta.client.presenters.MainPresenter;
import uk.co.unclealex.rokta.client.presenters.MainPresenter.Display;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public class Rokta implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		Dygraphs.install();
		final RoktaGinjector injector = GWT.create(RoktaGinjector.class);

		MainPresenter mainPresenter = injector.getMainPresenter();
		SimpleLayoutPanel parent = new SimpleLayoutPanel();
		RootLayoutPanel.get().add(parent);
		mainPresenter.show(parent);
		Display mainDisplay = mainPresenter.getDisplay();
		injector.getTitlePresenter().show(mainDisplay.getTitlePanel());
		injector.getAuthenticationPresenter().show(mainDisplay.getAuthenticationPanel());
		injector.getNavigationPresenter().show(mainDisplay.getNavigationPanel());
		injector.getActivityMapper().setDisplay(mainDisplay.getMainPanel());
		
		injector.getPlaceHistoryHandler().handleCurrentHistory();
	}

}
