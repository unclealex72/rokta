package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.HandCountPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;
import com.google.inject.Provider;

public class HandCount extends Composite implements Display, RequiresResize {

  @UiTemplate("HandCount.ui.xml")
	public interface Binder extends UiBinder<Widget, HandCount> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField SimplePanel handCounts;
	private DataTable i_dataTable;
	
	public HandCount() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(final Provider<DataTable> pieChartProvider) {
		Runnable handCountDrawer = new Runnable() {
			@Override
			public void run() {
		    setDataTable(pieChartProvider.get());
				redraw();
			}			
		};
		drawChart(handCountDrawer);
	}

	protected void redraw() {
		DataTable dataTable = getDataTable();
		if (dataTable != null) {
		  SimplePanel handCounts = getHandCounts();
		  int offsetWidth = handCounts.asWidget().getOffsetWidth();
		  int offsetHeight = handCounts.asWidget().getOffsetHeight();
		  int size = offsetWidth > offsetHeight?offsetHeight:offsetWidth;
		  Options options = Options.create();
		  options.set3D(true);
		  options.setWidth(size);
		  options.setHeight(size);
		  PieChart pieChart = new PieChart();
			handCounts.setWidget(pieChart);
			pieChart.draw(dataTable, options);
		}
	}

	@Override
	public void onResize() {
		Runnable handCountDrawer = new Runnable() {
			@Override
			public void run() {
				redraw();
			}			
		};
		drawChart(handCountDrawer);
	}

	protected void drawChart(Runnable handCountDrawer) {
		VisualizationUtils.loadVisualizationApi(handCountDrawer, PieChart.PACKAGE);
	}
	
	public SimplePanel getHandCounts() {
		return handCounts;
	}

	public DataTable getDataTable() {
		return i_dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		i_dataTable = dataTable;
	}

}
