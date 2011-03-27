package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;

import com.google.gwt.user.client.ui.SimplePanel;

public abstract class SimpleGameFilterProducerPanel extends GameFilterProducerComposite<SimplePanel> {

	public SimpleGameFilterProducerPanel(
		RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaController, model, gameFilterProducerListeners);
	}

	@Override
	protected SimplePanel create() {
		return new SimplePanel();
	}
	
	public void onValueChanged(InitialDatesView value) {
		// Do nothing
	}
	
	@Override
	public void onVisibilityChange(boolean isVisible) {
		if (isVisible) {
			setGameFilter(createGameFilter());
		}
	}
}
