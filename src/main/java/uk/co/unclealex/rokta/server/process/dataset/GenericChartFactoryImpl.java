package uk.co.unclealex.rokta.server.process.dataset;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import uk.co.unclealex.rokta.server.model.Colour;

@Service
@Transactional
public class GenericChartFactoryImpl implements GenericChartFactory {

	public static void main(String[] args) throws IOException {
		Map<Comparable<?>, Colour> colours = new HashMap<Comparable<?>, Colour>();
		class DummyColour extends Colour {
			private Color color;
			protected DummyColour(Color color) {
				this.color = color;
			}
			@Override
			public Color toColor() {
				return color;
			}
		}
		colours.put("Rock", new DummyColour(Color.CYAN));
		colours.put("Scissors", new DummyColour(Color.MAGENTA));
		colours.put("Paper", new DummyColour(Color.YELLOW));
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("Rock", 1);
		pieDataset.setValue("Scissors", 2);
		pieDataset.setValue("Paper", 3);
		FileWriter writer = new FileWriter("/home/alex/graph.svg");
		new GenericChartFactoryImpl().drawPieChart(colours, new DummyColour(Color.BLACK), pieDataset, new Dimension(800,400), writer);
		writer.close();
	}
	
	
	public void drawPieChart(
			final Map<Comparable<?>, Colour> colours, Colour outlineColour, PieDataset pieDataset, Dimension dimensions, Writer writer) throws IOException {
		final Color outlineColor = outlineColour.toColor();
		new SvgDrawer<PieDataset>(pieDataset) {
			@Override
			protected Plot createPlot(PieDataset dataset, Locale locale) {
				PiePlot plot = new PiePlot3D(dataset);
				plot.setLabelGenerator(null);
				for (Map.Entry<Comparable<?>, Colour> entry : colours.entrySet()) {
					Colour colour = entry.getValue();
					Comparable<?> key = entry.getKey();
					plot.setSectionPaint(key, colour.toColor());
					plot.setSectionOutlinePaint(key, outlineColor);
				}
				return plot;
			}
		}.draw(dimensions, writer);
	}
	
	public void drawLineGraph(
			CategoryDataset categoryDataset, Colour axesColour, Dimension dimensions, Writer writer) throws IOException {
		new SvgDrawer<CategoryDataset>(categoryDataset) {
			protected Plot createPlot(CategoryDataset dataset, Locale locale) {
        CategoryPlot plot = new CategoryPlot(dataset, new CategoryAxis(), new NumberAxis(),
        		new LineAndShapeRenderer(true, false));
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeCrosshairPaint(Color.PINK);
        plot.setRangeGridlinePaint(Color.BLACK);
        return plot;
			}
		}.draw(dimensions, writer);
	}
	
	protected abstract class SvgDrawer<D extends Dataset> {
		
		protected D dataset;
		
		protected SvgDrawer(D dataset) {
			this.dataset = dataset;
		}
		
		public void draw(Dimension dimensions, Writer writer) throws SVGGraphics2DIOException {
			Plot plot = createPlot(dataset, Locale.getDefault());
			if (dataset instanceof ColourDataset && plot instanceof CategoryPlot) {
				List<Colour> colours = ((ColourDataset) dataset).getColours(); 
				CategoryPlot categoryPlot = (CategoryPlot) plot;
				CategoryItemRenderer renderer = categoryPlot.getRenderer();
				int colourCount = Math.min(colours.size(), categoryPlot.getCategories().size());
				for (int idx = 0; idx < colourCount; idx++) {
					renderer.setSeriesPaint(idx, colours.get(idx).toColor());
				}
			}
			plot.setOutlineVisible(false);
	    DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
	    Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
		  SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		  plot.draw(svgGenerator, new Rectangle(dimensions), null, null, null);	
		  svgGenerator.stream(writer, false);
		}
		
		protected abstract Plot createPlot(D dataset, Locale locale);
	}
}
