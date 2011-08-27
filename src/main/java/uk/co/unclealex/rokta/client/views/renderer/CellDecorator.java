package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.user.client.ui.Widget;

public interface CellDecorator {

	public void decorate(Widget cellWidget, Table table, String typeMarker, int row, int column);

}
