package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;

public class LastFourWeeksGameFilterProducerPanel extends SimpleGameFilterProducerPanel {

	private static final long MILLIS_IN_DAY = 1000l * 3600l * 24l;
	protected LastFourWeeksGameFilterProducerPanel(RoktaAdaptor roktaAdaptor,
			GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaAdaptor, gameFilterProducerListeners);
	}

	@Override
	protected GameFilter createGameFilter() {
		Date now = new Date();
		Date fourWeeksAgo = new Date(now.getTime() - MILLIS_IN_DAY * 28l);
		return new SinceGameFilter(fourWeeksAgo);
	}

}
