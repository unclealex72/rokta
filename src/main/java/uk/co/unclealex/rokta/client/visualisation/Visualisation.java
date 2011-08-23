package uk.co.unclealex.rokta.client.visualisation;

import com.google.gwt.visualization.client.DataTable;
import com.google.inject.Provider;

public class Visualisation {

	public void draw(VisualisationDisplay display, final VisualisationProvider visualisationProvider) {
		Provider<DataTable> provider = new Provider<DataTable>() {
			@Override
			public DataTable get() {
				return visualisationProvider.createDataTable();
			}
		};
		display.draw(provider);
	}
}
