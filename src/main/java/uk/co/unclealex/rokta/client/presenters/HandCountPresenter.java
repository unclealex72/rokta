package uk.co.unclealex.rokta.client.presenters;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.visualisation.Visualisation;
import uk.co.unclealex.rokta.client.visualisation.VisualisationDisplay;
import uk.co.unclealex.rokta.client.visualisation.VisualisationProvider;
import uk.co.unclealex.rokta.shared.model.Hand;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.inject.assistedinject.Assisted;

public class HandCountPresenter implements Presenter, VisualisationProvider {

	public static interface Display extends VisualisationDisplay {
	}
	
	private final Display i_display;
	private final Map<Hand, Long> i_handCounts;
	private final Visualisation i_visualisation;
	
	@Inject
	public HandCountPresenter(Display display, @Assisted Map<Hand, Long> handCounts, Visualisation visualisation) {
		super();
		i_display = display;
		i_handCounts = handCounts;
		i_visualisation = visualisation;
	}

	@Override
	public void show(AcceptsOneWidget container) {
		Display display = getDisplay();
		container.setWidget(display);
		getVisualisation().draw(display, this);
	}

	@Override
	public DataTable createDataTable() {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Hand");
		dataTable.addColumn(ColumnType.NUMBER, "Count");
		for (Entry<Hand, Long> entry : getHandCounts().entrySet()) {
			int row = dataTable.addRow();
			dataTable.setValue(row, 0, entry.getKey().getDescription());
			dataTable.setValue(row, 1, entry.getValue());
		}
		return dataTable;
	}


	public Display getDisplay() {
		return i_display;
	}


	public Map<Hand, Long> getHandCounts() {
		return i_handCounts;
	}


	public Visualisation getVisualisation() {
		return i_visualisation;
	}
}
