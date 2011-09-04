package uk.co.unclealex.rokta.client.views;

import java.util.Map;
import java.util.Map.Entry;

import uk.co.unclealex.rokta.client.presenters.HandCountPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HandCount extends Composite implements Display {

  @UiTemplate("HandCount.ui.xml")
	public interface Binder extends UiBinder<Widget, HandCount> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField SimplePanel handCounts;
	
	public HandCount() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(String title, Map<String, Long> countsByHand) {
		JsArrayMixed data = JavaScriptObject.createArray().cast();
		int totalHandCount = 0;
		for (Long count : countsByHand.values()) {
			totalHandCount += count.intValue();
		}
		for (Entry<String, Long> entry : countsByHand.entrySet()) {
			int handCount = entry.getValue().intValue();
			JavaScriptObject dataPoint = createDataPoint(entry.getKey(), (double) handCount / (double) totalHandCount * 100.0);
			data.push(dataPoint);
		}
		draw(getHandCounts().getElement(), title, data);
	}
	
	protected native JavaScriptObject createDataPoint(String name, double data) /*-{
		return { name: name, y: data };
	}-*/;
	
	protected native void draw(Element container, String title, JsArrayMixed data) /*-{
		chart = new $wnd.Highcharts.Chart({
			chart: {
				renderTo: container,
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				text: title
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ this.y.toPrecision(4) +' %';
				}
			},
			legend: {
				align: 'right',
				verticalAlign: 'top',
				layout: 'vertical',
				floating: true
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false,
					},
					showInLegend: true
				}
			},
		    series: [{
				type: 'pie',
				name: 'Hand count',
				data: data
			}]
		});
	}-*/;

	public SimplePanel getHandCounts() {
		return handCounts;
	}

}
