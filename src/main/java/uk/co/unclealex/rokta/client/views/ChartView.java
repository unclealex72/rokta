package uk.co.unclealex.rokta.client.views;

import java.io.Serializable;
import java.util.List;

public class ChartView<N extends Number> implements Serializable {

	private List<ChartEntryView<N>> i_chartEntryViews;
	private List<String> i_labels;
	private N i_maximumValue;
	private N i_minimumValue;
	
	protected ChartView() {
		// Default constructor for serialisation
	}
	
	public ChartView(List<ChartEntryView<N>> chartEntryViews, List<String> labels, N maximumValue, N minimumValue) {
		super();
		i_chartEntryViews = chartEntryViews;
		i_labels = labels;
		i_maximumValue = maximumValue;
		i_minimumValue = minimumValue;
	}

	public List<String> getLabels() {
		return i_labels;
	}
	
	public List<ChartEntryView<N>> getChartEntryViews() {
		return i_chartEntryViews;
	}

	public N getMaximumValue() {
		return i_maximumValue;
	}

	public N getMinimumValue() {
		return i_minimumValue;
	}
}
