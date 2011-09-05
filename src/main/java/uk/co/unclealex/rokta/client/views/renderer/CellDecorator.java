package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.dom.client.Element;

public interface CellDecorator {

	public void decorate(Element cellElement, Table table, String typeMarker, int row, int column);

}
