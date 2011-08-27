package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.user.client.ui.Widget;

public class StyleDecorator implements CellDecorator {

	private final String i_styleName;
	
	public StyleDecorator(String styleName) {
		super();
		i_styleName = styleName;
	}

	@Override
	public void decorate(Widget cellWidget, Table table, String typeMarker, int row, int column) {
		cellWidget.addStyleName(getStyleName());
	}

	public String getStyleName() {
		return i_styleName;
	}
}
