package uk.co.unclealex.rokta.client.views.renderer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import uk.co.unclealex.rokta.client.factories.ClickHandlerFactory;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.model.Row;
import uk.co.unclealex.rokta.client.model.Table;

import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class TableRenderer {

	private final Table i_table;
	private final TitleFactory i_titleFactory;
	private final ClickHandlerFactory i_clickHandlerFactory;
	
	private final Multimap<Integer, CellRenderer> i_columnRenderers = 
		Multimaps.newListMultimap(
			new HashMap<Integer, Collection<CellRenderer>>(),
			new Supplier<List<CellRenderer>>() {
				public List<CellRenderer> get() { return Lists.newArrayList(); }
			});
	
	private final Multimap<String, CellDecorator> i_rowDecorators = 
			Multimaps.newListMultimap(
				new HashMap<String, Collection<CellDecorator>>(),
				new Supplier<List<CellDecorator>>() {
					public List<CellDecorator> get() { return Lists.newArrayList(); }
				});

	private final Multimap<Integer, CellDecorator> i_columnDecorators = 
			Multimaps.newListMultimap(
				new HashMap<Integer, Collection<CellDecorator>>(),
				new Supplier<List<CellDecorator>>() {
					public List<CellDecorator> get() { return Lists.newArrayList(); }
				});

	public TableRenderer(Table table, TitleFactory titleFactory, ClickHandlerFactory clickHandler) {
		super();
		i_table = table;
		i_titleFactory = titleFactory;
		i_clickHandlerFactory = clickHandler;
	}

	public TableRenderer addColumnRenderer(int column, CellRenderer columnRenderer) {
		getColumnRenderers().put(column, columnRenderer);
		return this;
	}
	
	public TableRenderer addRowDecorator(String rowMarker, CellDecorator rowDecorator) {
		getRowDecorators().put(rowMarker, rowDecorator);
		return this;
	}

	public TableRenderer addColumnDecorator(int column, CellDecorator rowDecorator) {
		getColumnDecorators().put(column, rowDecorator);
		return this;
	}

	public void draw(Grid grid) {
		Table table = getTable();
		List<Row> rows = table.getRows();
		if (!rows.isEmpty()) {
			grid.resize(rows.size(), rows.get(0).getCells().size());			
		}
		TitleFactory titleFactory = getTitleFactory();
		ClickHandlerFactory clickHandlerFactory = getClickHandlerFactory();
		int rowIdx = 0;
		for (Row row : rows) {
			Collection<CellDecorator> rowDecorators;
			String typeMarker = row.getTypeMarker();
			if (typeMarker == null) {
				rowDecorators = Collections.emptySet();
			}
			else {
				rowDecorators = getRowDecorators().get(typeMarker);
				if (rowDecorators == null) {
					rowDecorators = Collections.emptySet();
				}
			}
			int colIdx = 0;
			for (Object cell : row.getCells()) {
				Collection<CellRenderer> columnRenderers = getColumnRenderers().get(colIdx);
				if (columnRenderers != null) {
					for (CellRenderer columnRenderer : columnRenderers) {
						cell = columnRenderer.render(cell, table, rowIdx, colIdx);
					}
				}
				Widget cellWidget;
				if (cell == null) {
					cellWidget = new HTMLPanel("&nbsp;");
				}
				else {
					cellWidget = (cell instanceof IsWidget)?((IsWidget) cell).asWidget():new HTMLPanel(cell.toString());
				}
				Iterable<CellDecorator> cellDecorators = rowDecorators;
				Collection<CellDecorator> columnDecorators = getColumnDecorators().get(colIdx);
				if (columnDecorators != null) {
					cellDecorators = Iterables.concat(cellDecorators, columnDecorators);
				}
				grid.setWidget(rowIdx, colIdx, cellWidget);
				for (CellDecorator cellDecorator : cellDecorators) {
					cellDecorator.decorate(cellWidget.getElement().getParentElement(), table, typeMarker, rowIdx, colIdx);
				}
				if (titleFactory != null) {
					String title = titleFactory.createTitle(rowIdx, colIdx);
					if (title != null) {
						cellWidget.setTitle(title);
					}
				}
				if (clickHandlerFactory != null && cellWidget instanceof HasClickHandlers) {
					ClickHandler clickHandler = clickHandlerFactory.createClickHandler(rowIdx, colIdx);
					((HasClickHandlers) cellWidget).addClickHandler(clickHandler);
				}
				colIdx++;
			}
			rowIdx++;
		}
	}
	
	public Table getTable() {
		return i_table;
	}

	public Multimap<Integer, CellRenderer> getColumnRenderers() {
		return i_columnRenderers;
	}

	public Multimap<String, CellDecorator> getRowDecorators() {
		return i_rowDecorators;
	}

	public Multimap<Integer, CellDecorator> getColumnDecorators() {
		return i_columnDecorators;
	}

	public TitleFactory getTitleFactory() {
		return i_titleFactory;
	}

	public ClickHandlerFactory getClickHandlerFactory() {
		return i_clickHandlerFactory;
	}
	
	
}
