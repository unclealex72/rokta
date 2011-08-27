package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

public class SubstituteRepeatColumnRenderer implements CellRenderer {

	private final String i_substituteToken;

	public SubstituteRepeatColumnRenderer(String substituteToken) {
		super();
		i_substituteToken = substituteToken;
	}
	
	@Override
	public Object render(Object cell, Table table, int row, int column) {
		if (cell != null && cell.equals(table.getCellAt(row - 1, column))) {
			return getSubstituteToken();
		}
		else {
			return cell;
		}
	}
	
	public String getSubstituteToken() {
		return i_substituteToken;
	}
}
