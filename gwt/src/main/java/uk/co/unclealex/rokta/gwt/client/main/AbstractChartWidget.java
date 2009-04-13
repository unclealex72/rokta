package uk.co.unclealex.rokta.gwt.client.main;

import uk.co.unclealex.rokta.gwt.client.DefaultAsyncCallback;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rednels.ofcgwt.client.ChartWidget;
import com.rednels.ofcgwt.client.model.ChartData;

public abstract class AbstractChartWidget<D> extends RoktaAwareComposite {

	private ChartWidget i_chart;
	
	public AbstractChartWidget(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
  	ChartWidget chart = new ChartWidget();
    initialiseChart(chart);
    setChart(chart);
    initWidget(chart);
	}

	public void onGameFilterChange(GameFilter newGameFilter) {
		AsyncCallback<D> callback = new DefaultAsyncCallback<D>() {
			public void onSuccess(D data) {
				getChart().setJsonData(createChartData(data).toString());
			}
		};
		executeChartCallback(callback);	
	}
	
	protected abstract void executeChartCallback(AsyncCallback<D> callback);

	protected abstract ChartData createChartData(D data);

	public abstract void initialiseChart(ChartWidget chart);
	
	public ChartWidget getChart() {
		return i_chart;
	}

	public void setChart(ChartWidget chart) {
		i_chart = chart;
	}

}
