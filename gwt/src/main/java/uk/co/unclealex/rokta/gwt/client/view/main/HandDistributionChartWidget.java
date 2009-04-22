package uk.co.unclealex.rokta.gwt.client.view.main;

import java.util.Map;
import java.util.TreeSet;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.HandDistributionChartModel;
import uk.co.unclealex.rokta.pub.views.Hand;

import com.rednels.ofcgwt.client.ChartWidget;
import com.rednels.ofcgwt.client.model.ChartData;
import com.rednels.ofcgwt.client.model.elements.PieChart;

public class HandDistributionChartWidget extends AbstractChartWidget<Map<Hand,Integer>> {

	private String i_chartTitle;
	

	public HandDistributionChartWidget(RoktaController roktaController, HandDistributionChartModel handDistributionChartModel,
			String chartTitle) {
		super(roktaController, handDistributionChartModel);
		i_chartTitle = chartTitle;
	}

	@Override
	public void initialiseChart(ChartWidget chart) {
		chart.setSize("300", "300");
	}
	
	@Override
	protected ChartData createChartData(Map<Hand, Integer> data) {
    ChartData cd = new ChartData(getChartTitle(), "font-size: 14px; font-family: Verdana; text-align: center;");
    cd.setBackgroundColour("#ffffff");
    PieChart pie = new PieChart();
    pie.setAlpha(0.3f);
    pie.setNoLabels(false);
    pie.setTooltip("#label# #val#<br>#percent#");
    pie.setAnimate(true);
    pie.setAlphaHighlight(true);
    pie.setGradientFill(true);
    pie.setColours("#ff0000","#00ff00","#0000ff");
    for (Hand hand : new TreeSet<Hand>(data.keySet())) {
      pie.addSlices(new PieChart.Slice(data.get(hand), hand.getDescription()));    	
    }
    cd.addElements(pie);
    return cd;
	}
	
	public String getChartTitle() {
		return i_chartTitle;
	}
}
