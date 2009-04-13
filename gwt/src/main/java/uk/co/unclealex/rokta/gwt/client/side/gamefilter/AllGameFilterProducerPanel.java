package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.AllGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public class AllGameFilterProducerPanel extends SimpleGameFilterProducerPanel {

	private static GameFilter ALL_GAME_FILTER = new AllGameFilter();
	
	protected AllGameFilterProducerPanel(RoktaAdaptor roktaAdaptor, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaAdaptor, gameFilterProducerListeners);
	}

	@Override
	protected GameFilter createGameFilter() {
		return ALL_GAME_FILTER;
	}

}
