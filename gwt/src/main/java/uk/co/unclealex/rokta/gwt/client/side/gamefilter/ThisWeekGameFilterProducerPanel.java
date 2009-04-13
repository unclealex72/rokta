package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.WeekGameFilter;

public class ThisWeekGameFilterProducerPanel extends SimpleGameFilterProducerPanel {

	protected ThisWeekGameFilterProducerPanel(RoktaAdaptor roktaAdaptor,
			GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaAdaptor, gameFilterProducerListeners);
	}

	@Override
	protected GameFilter createGameFilter() {
		return new WeekGameFilter(new Date());
	}

}
