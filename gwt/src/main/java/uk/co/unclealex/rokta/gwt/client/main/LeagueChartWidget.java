package uk.co.unclealex.rokta.gwt.client.main;

import java.util.ArrayList;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.views.ChartEntryView;
import uk.co.unclealex.rokta.pub.views.ChartView;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rednels.ofcgwt.client.ChartWidget;
import com.rednels.ofcgwt.client.model.ChartData;
import com.rednels.ofcgwt.client.model.axis.XAxis;
import com.rednels.ofcgwt.client.model.axis.YAxis;
import com.rednels.ofcgwt.client.model.elements.LineChart;
import com.rednels.ofcgwt.client.model.elements.LineChart.LineStyle;

public class LeagueChartWidget extends AbstractChartWidget<ChartView<Double>> {

	public LeagueChartWidget(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
	}

	public void initialiseChart(ChartWidget chart) {
		chart.setSize("600", "300");
	}
	
	protected ChartData createChartData(ChartView<Double> chartView) {
    ChartData cd = new ChartData("League","font-size: 14px; font-family: Verdana; text-align: center;");
    cd.setBackgroundColour("#ffffff");
    for (ChartEntryView<Double> entry : chartView.getChartEntryViews()) {
    	LineChart lineChart = new LineChart(LineStyle.NORMAL);
    	lineChart.setDotSize(1);
    	lineChart.setHaloSize(1);
    	lineChart.setText(entry.getLabel());
    	lineChart.setColour(entry.getColourView().toHexString());
    	lineChart.addValues(new ArrayList<Number>(entry.getValues()));
    	cd.addElements(lineChart);
    }
    YAxis ya = new YAxis();
    int max = (chartView.getMaximumValue().intValue() / 10) * 10 + 10;
    int min = (chartView.getMinimumValue().intValue() / 10) * 10;
    ya.setRange(min, max, 10);
    cd.setYAxis(ya);
    
    XAxis xa = new XAxis();
    xa.setSteps(2);
    for (String label : chartView.getLabels()) {
    	xa.addLabels(label);
    }
		cd.setXAxis(xa);
		return cd;
	}

	@Override
	protected void executeChartCallback(AsyncCallback<ChartView<Double>> callback) {
		getRoktaAdaptor().createLeagueChart("Orange", 40, callback);
	}
	
	
}
