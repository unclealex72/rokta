package uk.co.unclealex.rokta.client.factories;

import com.google.gwt.event.dom.client.ClickHandler;

public interface ClickHandlerFactory {

	public ClickHandler createClickHandler(int row, int column);

}
