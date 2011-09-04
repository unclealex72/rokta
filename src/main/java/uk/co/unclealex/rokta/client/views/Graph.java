package uk.co.unclealex.rokta.client.views;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import uk.co.unclealex.rokta.client.presenters.GraphPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Graph extends Composite implements Display {

  @UiTemplate("Graph.ui.xml")
	public interface Binder extends UiBinder<Widget, Graph> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField SimplePanel graph;
	
	public Graph() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void drawGraph(Map<String, String> coloursByName, Map<String, SortedMap<Date, Double>> percentagesByDateByName) {
		JsArrayMixed seriesArray = createSeries(percentagesByDateByName);
		JsArrayString coloursArray = createColours(percentagesByDateByName.keySet(), coloursByName);
		drawGraph(graph.getElement(), coloursArray, seriesArray);		
	}

	protected JsArrayString createColours(Set<String> names, Map<String, String> coloursByName) {
		JsArrayString coloursArray = JavaScriptObject.createArray().cast();
		for (String name : names) {
			coloursArray.push(coloursByName.get(name));
		}
		return coloursArray;
	}

	protected JsArrayMixed createSeries(Map<String, SortedMap<Date, Double>> series) {
		JsArrayMixed seriesArray = JavaScriptObject.createArray().cast();
		for (Entry<String, SortedMap<Date, Double>> entry : series.entrySet()) {
			seriesArray.push(createSeries(entry.getKey(), entry.getValue()));
		}
		return seriesArray;
	}
	
	protected JavaScriptObject createSeries(String name, SortedMap<Date, Double> data) {
		JsArrayMixed dataArray = JavaScriptObject.createArray().cast();
		for (Entry<Date, Double> entry : data.entrySet()) {
			Date date = entry.getKey();
			@SuppressWarnings("deprecation")
			JsArrayMixed point = createPoint(
					date.getYear() + 1900, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), entry.getValue());
			dataArray.push(point);
		}
		return createSeries(name, dataArray);
	}
	
	protected native JsArrayMixed createPoint(int year, int month, int day, int hours, int minutes, double value) /*-{
		return [Date.UTC(year, month, day,hours, minutes, 0, 0), value];
	}-*/;
	
	protected native JavaScriptObject createSeries(String name, JsArrayMixed data) /*-{
		return {name: name, data: data};
	}-*/;
	
	protected native void drawGraph(Element element, JsArrayString colours, JsArrayMixed series) /*-{
		var chart = new $wnd.Highcharts.Chart({
			chart: {
				renderTo: element,
        zoomType: 'xy',				
				type: 'spline'
			},
			colors: colours,
			title: {
				text: 'Loss percentages by date'
			},
			xAxis: {
				type: 'datetime',
				dateTimeLabelFormats: { // don't display the dummy year
					month: '%e. %b',
					year: '%b'
				}
			},
			yAxis: {
				title: {
					text: 'Losses (%)'
				},
				min: 0
			},
			tooltip: {
				formatter: function() {
					return $wnd.Highcharts.dateFormat('%a, %e %B %Y, %H:%M', this.x) + '<br/>' +
								 '<b>'+ this.series.name +'</b> ' + this.y.toPrecision(4) + '%';
				}
			},
	    plotOptions: {
        series: {
          marker: {
            enabled: false,
            states: {
              hover: {
                enabled: true
              }
            }
          }
        }
  		},
			series: series
		});
	}-*/;
	
	public SimplePanel getGraph() {
		return graph;
	}
}
