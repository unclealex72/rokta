package uk.co.unclealex.rokta.client.views.renderer;

import uk.co.unclealex.rokta.client.model.Table;

public abstract class OnlyForClassColumnRenderer<C> implements CellRenderer {

	private final Class<C> i_clazz;
	
	public OnlyForClassColumnRenderer(Class<C> clazz) {
		super();
		i_clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object render(Object cell, Table table, int row, int column) {
		if (cell != null) {
			try {
				return doRender((C) cell, table, row, column);
			}
			catch (ClassCastException e) {
				return cell;
			}
		}
		else {
			return cell;
		}
	}

	protected abstract Object doRender(C cell, Table table, int row, int column);
	
	public Class<C> getClazz() {
		return i_clazz;
	}
}
