package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

public interface CellRenderer {

	public Object render(Object cell, Table table, int row, int column);

}
