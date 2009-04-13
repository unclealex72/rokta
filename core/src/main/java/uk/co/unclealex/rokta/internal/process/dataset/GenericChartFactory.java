package uk.co.unclealex.rokta.internal.process.dataset;

import java.awt.Dimension;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import uk.co.unclealex.rokta.internal.model.Colour;

public interface GenericChartFactory {

	public void drawPieChart(Map<Comparable<?>, Colour> colours, Colour outlineColour, PieDataset pieDataset, Dimension dimensions, Writer writer)
			throws IOException;

	public void drawLineGraph(CategoryDataset categoryDataset, Colour axesColour, Dimension dimensions, Writer writer)
			throws IOException;

}
