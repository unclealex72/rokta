package uk.co.unclealex.rokta.internal.process.dataset;

import java.awt.Dimension;
import java.io.IOException;
import java.io.Writer;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

public interface GenericChartFactory {

	public <P extends PieDataset> void drawPieChart(final String title, DatasetProducer<P> datasetProducer, Dimension dimensions, Writer writer)
			throws IOException;

	public <C extends CategoryDataset> void drawLineGraph(final String title, DatasetProducer<C> datasetProducer, Dimension dimensions, Writer writer)
			throws IOException;

}
