package uk.co.unclealex.rokta.internal.process.dataset;

import java.util.SortedMap;

import uk.co.unclealex.rokta.internal.model.Colour;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.ChartView;
import uk.co.unclealex.rokta.pub.views.Hand;

public interface ChartService {

	public SortedMap<Hand, Integer> createHandDistributionGraph(GameFilter gameFilter, Person person);
	public SortedMap<Hand, Integer> createOpeningHandDistributionGraph(GameFilter gameFilter, Person person);
	
	public ChartView<Double> createLeagueChart(GameFilter gameFilter, Colour averageColour, int maximumColumns);

}
