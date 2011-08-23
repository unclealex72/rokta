package uk.co.unclealex.rokta.client.visualisation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.visualization.client.DataTable;
import com.google.inject.Provider;

public interface VisualisationDisplay extends IsWidget {

	void draw(Provider<DataTable> provider);

}
