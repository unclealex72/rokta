package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.i18n.client.NumberFormat;

public class PercentageColumnRenderer extends NumericColumnRenderer implements CellRenderer {

	public PercentageColumnRenderer(NumberFormat numberFormat) {
		super(numberFormat);
	}

	@Override
	public String doRender(Number cell, Table table, int row, int column) {
		return super.doRender(cell.doubleValue() * 100.0, table, row, column) + "%";
	}
}
