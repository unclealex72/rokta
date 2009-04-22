package uk.co.unclealex.rokta.gwt.client.listener;

import java.io.Serializable;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

public interface GameFilterListener extends Serializable {

	public void onGameFilterChange(GameFilter gameFilter);
}
