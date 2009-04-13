package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;

import com.google.gwt.user.client.ui.SimplePanel;

public abstract class SimpleGameFilterProducerPanel extends GameFilterProducerComposite {

	protected SimpleGameFilterProducerPanel(RoktaAdaptor roktaAdaptor, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaAdaptor, gameFilterProducerListeners);
		initWidget(new SimplePanel());
	}

	@Override
	public void onVisibilityChange(boolean isVisible) {
		if (isVisible) {
			setGameFilter(createGameFilter());
		}
	}
}
