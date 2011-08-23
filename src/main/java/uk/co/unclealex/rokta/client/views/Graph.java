package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.GraphPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.inject.Provider;

public class Graph extends Composite implements Display {

  @UiTemplate("Graph.ui.xml")
	public interface Binder extends UiBinder<Widget, Graph> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField SimplePanel graph;
	
	private Iterable<String> i_colours;
	
	public Graph() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(final Provider<DataTable> dataTableProvider) {
		final Runnable graphDrawer = new Runnable() {
			@Override
			public void run() {
				DataTable graphDataTable = dataTableProvider.get();
				Element element = getGraph().getElement();
				drawDygraph(element, graphDataTable, colours(getColours()));
			}
		};
		VisualizationUtils.loadVisualizationApi(graphDrawer);
	}
	
	protected JsArrayString colours(Iterable<String> colours) {
		JsArrayString array = (JsArrayString) JsArrayString.createArray();
		int idx = 0;
		for (String colour : colours) {
			array.set(idx, colour);
			idx++;
		}
		return array;
	}
	
	protected void drawDygraph(Element graphElement, DataTable graphDataTable, JsArrayString colours) {
		drawDygraph(graphElement, graphDataTable, "100%", "100%", colours);
	}

	protected native JavaScriptObject drawDygraph(
	    Element graphElement, DataTable dataTable, String width, String height, JsArrayString colours) /*-{
	  var chart = new $wnd.Dygraph.GVizChart(graphElement);
	  chart.draw(dataTable,
	    {
	      colors: colours,
	      legend: "always",
	      width: width,
	      height: height
	    });
	  return chart;
	}-*/;

	public Iterable<String> getColours() {
		return i_colours;
	}

	public void setColours(Iterable<String> colours) {
		i_colours = colours;
	}

	public SimplePanel getGraph() {
		return graph;
	}
}
