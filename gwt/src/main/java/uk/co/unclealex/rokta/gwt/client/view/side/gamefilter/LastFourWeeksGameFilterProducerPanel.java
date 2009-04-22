package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;

public class LastFourWeeksGameFilterProducerPanel extends SimpleGameFilterProducerPanel {

	private static final long MILLIS_IN_DAY = 1000l * 3600l * 24l;
	
	public LastFourWeeksGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaController, model, gameFilterProducerListeners);
	}

	@Override
	protected GameFilter createGameFilter() {
		Date now = new Date();
		Date fourWeeksAgo = new Date(now.getTime() - MILLIS_IN_DAY * 28l);
		return new SinceGameFilter(fourWeeksAgo);
	}

}
