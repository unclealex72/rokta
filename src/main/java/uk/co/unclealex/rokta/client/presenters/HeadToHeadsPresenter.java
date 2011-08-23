package uk.co.unclealex.rokta.client.presenters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.visualisation.Visualisation;
import uk.co.unclealex.rokta.client.visualisation.VisualisationDisplay;
import uk.co.unclealex.rokta.client.visualisation.VisualisationProvider;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.HeadToHeads;
import uk.co.unclealex.rokta.shared.model.WinLoseCounter;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.inject.assistedinject.Assisted;

public class HeadToHeadsPresenter extends InformationPresenter<HeadToHeads> {

	public static interface Display extends VisualisationDisplay {

		HasOneWidget getHeadToHeads();
	}

	private final Display i_display;
	private final Visualisation i_visualisation;
	
	@Inject
	public HeadToHeadsPresenter(
			@Assisted GameFilter gameFilter, Visualisation visualisation, InformationCache informationCache, Display display) {
		super(gameFilter, informationCache);
		i_display = display;
		i_visualisation = visualisation;
	}

	@Override
	protected HeadToHeads asSpecificInformation(CurrentInformation currentInformation) {
		return currentInformation.getHeadToHeads();
	}

	@Override
	protected void show(GameFilter gameFilter, AcceptsOneWidget panel, final HeadToHeads headToHeads) {
		Display display = getDisplay();
		panel.setWidget(display);
		VisualisationProvider visualisationProvider = new VisualisationProvider() {
			@Override
			public DataTable createDataTable() {
				return createHeadsToHeadsDataTable(headToHeads);
			}
		};
		getVisualisation().draw(display, visualisationProvider );
	}
	
	protected DataTable createHeadsToHeadsDataTable(HeadToHeads headToHeads) {
		DataTable dataTable = DataTable.create();
		Set<String> names = headToHeads.getNames();
		int size = names.size();
		Map<String, Integer> indexByName = new HashMap<String, Integer>();
		int idx = 0;
		dataTable.addColumn(ColumnType.STRING);
		for (String name : names) {
			dataTable.addColumn(ColumnType.NUMBER, name);
			indexByName.put(name, idx++);
		}
		dataTable.addRows(size);
		int row = 0;
		for (String name : names) {
			dataTable.setValue(row++, 0, name);
		}
		for (WinLoseCounter winLoseCounter : headToHeads.getWinLoseCounters()) {
			int rowIdx = indexByName.get(winLoseCounter.getWinner());
			int colIdx = indexByName.get(winLoseCounter.getLoser()) + 1;
			dataTable.setValue(rowIdx, colIdx, 100.0 * winLoseCounter.getWinRatio());
		}
		return dataTable;
	}
	
	public Display getDisplay() {
		return i_display;
	}

	public Visualisation getVisualisation() {
		return i_visualisation;
	}
}
