package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.AllGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public class AllGameFilterProducerPanel extends SimpleGameFilterProducerPanel {

	private static GameFilter ALL_GAME_FILTER = new AllGameFilter();
	
	public AllGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaController, model, gameFilterProducerListeners);
	}

	@Override
	protected GameFilter createGameFilter() {
		return ALL_GAME_FILTER;
	}

}
