package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;

public class TodayGameFilterProducerPanel extends SimpleGameFilterProducerPanel {

	public TodayGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaController, model, gameFilterProducerListeners);
	}

	@Override
	protected GameFilter createGameFilter() {
		return new SinceGameFilter(new Date());
	}

}
