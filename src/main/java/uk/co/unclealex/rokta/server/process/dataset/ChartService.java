package uk.co.unclealex.rokta.server.process.dataset;

import java.util.SortedMap;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.views.ChartView;
import uk.co.unclealex.rokta.client.views.Hand;
import uk.co.unclealex.rokta.server.model.Colour;
import uk.co.unclealex.rokta.server.model.Person;

public interface ChartService {

	public SortedMap<Hand, Integer> createHandDistributionGraph(GameFilter gameFilter, Person person);
	public SortedMap<Hand, Integer> createOpeningHandDistributionGraph(GameFilter gameFilter, Person person);
	
	public ChartView<Double> createLeagueChart(GameFilter gameFilter, Colour averageColour, int maximumColumns);

}
