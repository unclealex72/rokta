package uk.co.unclealex.rokta.pub.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.ChartView;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

public interface ReadOnlyRoktaFacade {

	public GameFilter getDefaultGameFilter();
	
	public String getTitle(GameFilter gameFilter, String prefix);
	public String getCopyright();

	public League getLeague(GameFilter gameFilter, Date now);

	public StreaksLeague getWinningStreaks(GameFilter gameFilter, int targetSize);
	public StreaksLeague getLosingStreaks(GameFilter gameFilter, int targetSize);
	public StreaksLeague getCurrentWinningStreaks(GameFilter gameFilter);
	public StreaksLeague getCurrentLosingStreaks(GameFilter gameFilter);
	public StreaksLeague getLosingStreaksForPerson(GameFilter gameFilter, String personName, int targetSize);
	public StreaksLeague getWinningStreaksForPerson(GameFilter gameFilter, String personName, int targetSize);

	public Map<Hand, Integer> createHandDistributionGraph(GameFilter gameFilter, String personName);
	public Map<Hand, Integer> createOpeningHandDistributionGraph(GameFilter gameFilter, String personName);	
	public ChartView<Double> createLeagueChart(GameFilter gameFilter, String averageColourHtmlName, int maximumColumns);

	public List<String> getAllUsersNames();
	public List<String> getAllPlayerNames();
	
	public InitialDatesView getInitialDates();
}
