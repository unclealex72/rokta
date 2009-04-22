package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.LoadingNotifier;
import uk.co.unclealex.rokta.gwt.client.view.LoadingAwareComposite;

import com.rednels.ofcgwt.client.ChartWidget;
import com.rednels.ofcgwt.client.model.ChartData;

public abstract class AbstractChartWidget<V> extends LoadingAwareComposite<V> {

	private ChartWidget i_chart;
	
	public AbstractChartWidget(RoktaController roktaController, LoadingNotifier<V> notifier) {
		super(roktaController, notifier);
  	ChartWidget chart = new ChartWidget();
    initialiseChart(chart);
    setChart(chart);
    initWidget(chart);
	}

	public void onValueChanged(V value) {
		getChart().setJsonData(createChartData(value).toString());
	}
	
	protected abstract ChartData createChartData(V value);

	public abstract void initialiseChart(ChartWidget chart);
	
	protected ChartWidget getChart() {
		return i_chart;
	}

	protected void setChart(ChartWidget chart) {
		i_chart = chart;
	}

}
