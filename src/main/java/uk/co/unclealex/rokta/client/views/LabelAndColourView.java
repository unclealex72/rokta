package uk.co.unclealex.rokta.client.views;

import java.io.Serializable;

public class LabelAndColourView implements Serializable, Comparable<LabelAndColourView> {

	private String i_label;
	private ColourView i_colourView;
	
	protected LabelAndColourView() {
		// Default constructor for serialisation.
	}
	
	public LabelAndColourView(String label, ColourView colourView) {
		super();
		i_label = label;
		i_colourView = colourView;
	}

	public int compareTo(LabelAndColourView o) {
		int cmp = getLabel().compareTo(o.getLabel());
		return cmp!=0?cmp:getColourView().compareTo(o.getColourView());
	}
	
	public String getLabel() {
		return i_label;
	}
	
	public ColourView getColourView() {
		return i_colourView;
	}
}
