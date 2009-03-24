package uk.co.unclealex.rokta.process.dataset;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Locale;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import uk.co.unclealex.rokta.model.Colour;

@Service
@Transactional
public class GenericChartFactoryImpl implements GenericChartFactory {

	public <P extends PieDataset> void drawPieChart(
			final String title, DatasetProducer<P> datasetProducer, Dimension dimensions, Writer writer) throws IOException {
		new SvgDrawer<P>(datasetProducer) {
			@Override
			protected JFreeChart createChart(P dataset, boolean legend, boolean tooltips, Locale locale) {
				return ChartFactory.createPieChart3D(title, datasetProducer.produceDataset(), 
						legend, tooltips, locale);
			}
		}.draw(dimensions, writer);
	}
	
	public <C extends CategoryDataset> void drawLineGraph(
			final String title, DatasetProducer<C> datasetProducer, Dimension dimensions, Writer writer) throws IOException {
		new SvgDrawer<C>(datasetProducer) {
			protected JFreeChart createChart(C dataset, boolean legend, boolean tooltips, Locale locale) {
				return ChartFactory.createLineChart3D(
						title, null, null, dataset, PlotOrientation.HORIZONTAL, legend, tooltips, false);
			}
		}.draw(dimensions, writer);
	}
	
	protected abstract class SvgDrawer<D extends Dataset> {
		
		protected DatasetProducer<D> datasetProducer;
		
		protected SvgDrawer(DatasetProducer<D> datasetProducer) {
			this.datasetProducer = datasetProducer;
		}
		
		public void draw(Dimension dimensions, Writer writer) throws SVGGraphics2DIOException {
			D dataset = datasetProducer.produceDataset();
			JFreeChart chart = createChart(dataset, false, true, Locale.getDefault());
			if (dataset instanceof ColourDataset) {
				List<Colour> colours = ((ColourDataset) dataset).getColours(); 
				CategoryPlot categoryPlot = chart.getCategoryPlot();
				CategoryItemRenderer renderer = categoryPlot.getRenderer();
				int colourCount = Math.min(colours.size(), categoryPlot.getCategories().size());
				for (int idx = 0; idx < colourCount; idx++) {
					renderer.setSeriesPaint(idx, colours.get(idx).toColor());
				}
			}
	    DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
	    Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
		  SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		  chart.draw(svgGenerator, new Rectangle(dimensions));	
		  svgGenerator.stream(writer, true);			
		}
		
		protected abstract JFreeChart createChart(D dataset, boolean legend, boolean tooltips, Locale locale);
	}
}
