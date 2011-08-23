package uk.co.unclealex.rokta.client.presenters;

import java.util.SortedSet;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.visualisation.Visualisation;
import uk.co.unclealex.rokta.client.visualisation.VisualisationDisplay;
import uk.co.unclealex.rokta.client.visualisation.VisualisationProvider;
import uk.co.unclealex.rokta.shared.model.Streak;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.inject.assistedinject.Assisted;

public class StreaksTablePresenter implements Presenter, VisualisationProvider {

	public static interface Display extends VisualisationDisplay {
		
	}

	private final Display i_display;
	private final SortedSet<Streak> i_streaks;
	private final Visualisation i_visualisation;
	
	@Inject
	public StreaksTablePresenter(Display display, Visualisation visualisation, @Assisted SortedSet<Streak> streaks) {
		i_display = display;
		i_visualisation = visualisation;
		i_streaks = streaks;
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
		//Rank	Player	Games	From	To
		dataTable.addColumn(ColumnType.NUMBER, "Rank");
		dataTable.addColumn(ColumnType.STRING, "Player");
		dataTable.addColumn(ColumnType.NUMBER, "Games");
		dataTable.addColumn(ColumnType.DATETIME, "From");
		dataTable.addColumn(ColumnType.DATETIME, "To");
		for (Streak streak : getStreaks()) {
			int row = dataTable.addRow();
			int col = 0;
			dataTable.setValue(row, col++, streak.getRank());
			dataTable.setValue(row, col++, streak.getPersonName());
			dataTable.setValue(row, col++, streak.getLength());
			dataTable.setValue(row, col++, streak.getStartDate());
			dataTable.setValue(row, col++, streak.getEndDate());
		}
		return dataTable;
	}
	
	public SortedSet<Streak> getStreaks() {
		return i_streaks;
	}


	public Display getDisplay() {
		return i_display;
	}


	public Visualisation getVisualisation() {
		return i_visualisation;
	}

}
