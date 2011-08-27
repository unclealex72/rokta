package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.i18n.client.NumberFormat;

public class NumericColumnRenderer extends OnlyForClassColumnRenderer<Number> {

	private final NumberFormat i_numberFormat;
	
	public NumericColumnRenderer(NumberFormat numberFormat) {
		super(Number.class);
		i_numberFormat = numberFormat;
	}

	@Override
	public String doRender(Number cell, Table table, int row, int column) {
		Number number = (Number) cell;
		if (number instanceof Double && Double.compare(Double.NaN, number.doubleValue()) == 0) {
			return null;
		}
		else {
			return getNumberFormat().format(number);
		}
	}

	public NumberFormat getNumberFormat() {
		return i_numberFormat;
	}

	
}
