package uk.co.unclealex.rokta.internal.process.dataset;

import java.awt.Dimension;
import java.io.IOException;
import java.io.Writer;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

public interface JFreeChartService {

	public void drawHandDistributionGraph(GameFilter gameFilter, String personName, String rockHtmlColour, 
			String scissorsHtmlColour, String paperHtmlColour, String outlineHtmlColour, Dimension size, Writer writer) throws IOException;

	public void drawOpeningHandDistributionGraph(GameFilter gameFilter, String personName, String rockHtmlColour, 
			String scissorsHtmlColour, String paperHtmlColour, String outlineHtmlColour, Dimension size, Writer writer) throws IOException;

	public void drawLeagueGraph(GameFilter gameFilter, String averageColourHtmlName, int maximumColumns, Dimension size, Writer writer) throws IOException;

}
