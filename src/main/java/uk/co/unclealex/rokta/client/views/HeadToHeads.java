package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.HeadToHeadsPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.formatters.NumberFormat;
import com.google.gwt.visualization.client.formatters.NumberFormat.Options;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.inject.Provider;

public class HeadToHeads extends Composite implements Display {

  @UiTemplate("HeadToHeads.ui.xml")
	public interface Binder extends UiBinder<Widget, HeadToHeads> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasOneWidget headToHeads;
	
	public HeadToHeads() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(final Provider<DataTable> dataTableProvider) {
		Runnable headToHeadsDrawer = new Runnable() {
			@Override
			public void run() {
				DataTable headToHeadsDataTable = dataTableProvider.get();
				Options options = NumberFormat.Options.create();
				options.setSuffix("%");
				NumberFormat numberFormat = NumberFormat.create(options);
				int columns = headToHeadsDataTable.getNumberOfColumns();
				for (int column = 1; column < columns; column++) {
					numberFormat.format(headToHeadsDataTable, column);
				}
				Table headToHeads = new Table();
				getHeadToHeads().setWidget(headToHeads);
				headToHeads.draw(headToHeadsDataTable);
			}			
		};
		VisualizationUtils.loadVisualizationApi(headToHeadsDrawer, Table.PACKAGE);
	}

	public HasOneWidget getHeadToHeads() {
		return headToHeads;
	}
}
