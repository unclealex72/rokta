package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

public interface GameFilterProducer {

	public GameFilter getGameFilter();
	
	public void addGameFilterProducerListener(GameFilterProducerListener gameFilterProducerListener);
	
	public void removeGameFilterProducerListener(GameFilterProducerListener gameFilterProducerListener);
}
