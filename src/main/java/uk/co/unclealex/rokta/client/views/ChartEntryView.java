package uk.co.unclealex.rokta.client.views;

import java.io.Serializable;
import java.util.List;

public class ChartEntryView<N extends Number> implements Serializable {

	private String i_label;
	private ColourView i_colourView;
	private List<N> i_values;
	
	protected ChartEntryView() {
		// Default constructor for serialisation.
	}
	
	public ChartEntryView(String label, ColourView colourView, List<N> values) {
		super();
		i_label = label;
		i_colourView = colourView;
		i_values = values;
	}

	public String getLabel() {
		return i_label;
	}

	public ColourView getColourView() {
		return i_colourView;
	}

	public List<N> getValues() {
		return i_values;
	}
}
