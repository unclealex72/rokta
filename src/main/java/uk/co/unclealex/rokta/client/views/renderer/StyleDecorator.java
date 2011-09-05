package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.dom.client.Element;

public class StyleDecorator implements CellDecorator {

	private final String i_styleName;
	
	public StyleDecorator(String styleName) {
		super();
		i_styleName = styleName;
	}

	@Override
	public void decorate(Element cellElement, Table table, String typeMarker, int row, int column) {
		cellElement.addClassName(getStyleName());
	}

	public String getStyleName() {
		return i_styleName;
	}
}
