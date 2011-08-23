package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.StreaksTablePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.inject.Provider;

public class StreaksTable extends Composite implements Display {

  @UiTemplate("StreaksTable.ui.xml")
	public interface Binder extends UiBinder<Widget, StreaksTable> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasOneWidget streaks;
	
	public StreaksTable() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(final Provider<DataTable> dataTableProvider) {
		Runnable leagueDrawer = new Runnable() {
			@Override
			public void run() {
				DataTable streaksDataTable = dataTableProvider.get();
				Table streaks = new Table();
				getStreaks().setWidget(streaks);
				streaks.draw(streaksDataTable);
			}			
		};
		VisualizationUtils.loadVisualizationApi(leagueDrawer, Table.PACKAGE);
	}

	public HasOneWidget getStreaks() {
		return streaks;
	}
}
