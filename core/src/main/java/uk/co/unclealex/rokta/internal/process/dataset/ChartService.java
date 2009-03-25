package uk.co.unclealex.rokta.internal.process.dataset;

import java.awt.Dimension;
import java.io.Writer;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.model.Person;

public interface ChartService {

	public void drawHandDistributionGraph(GameFilter gameFilter, Person whois, Dimension size, Writer writer);

	public void drawOpeningHandDistributionGraph(GameFilter gameFilter, Person whois, Dimension size, Writer writer);

	public void drawLeagueGraph(GameFilter gameFilter, Dimension size, int maximumColumns, Writer writer);

}
