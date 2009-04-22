package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

public class GameFilterProducerEvent {

	private GameFilterProducer i_source;
	private GameFilter i_gameFilter;
	
	public GameFilterProducerEvent(GameFilterProducer source, GameFilter gameFilter) {
		super();
		i_source = source;
		i_gameFilter = gameFilter;
	}
	
	public GameFilterProducer getSource() {
		return i_source;
	}
	public GameFilter getGameFilter() {
		return i_gameFilter;
	}
}
