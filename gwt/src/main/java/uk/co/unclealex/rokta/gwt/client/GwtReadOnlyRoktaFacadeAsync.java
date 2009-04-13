package uk.co.unclealex.rokta.gwt.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.ChartView;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GwtReadOnlyRoktaFacadeAsync {

	public void getDefaultGameFilter(AsyncCallback<GameFilter> callback);
	
	public void getAllUsersNames(AsyncCallback<List<String>> callback);
	public void getAllPlayerNames(AsyncCallback<List<String>> callback);
	
	public void getTitle(GameFilter gameFilter, String prefix, AsyncCallback<String> callback);
	public void getCopyright(AsyncCallback<String> callback);
	
	public void getLeague(GameFilter gameFilter, Date now, AsyncCallback<League> callback);

	public void getCurrentLosingStreaks(
			GameFilter gameFilter, AsyncCallback<StreaksLeague> callback);
	public void getCurrentWinningStreaks(
			GameFilter gameFilter, AsyncCallback<StreaksLeague> callback);
	public void getLosingStreaks(
			GameFilter gameFilter, int targetSize, AsyncCallback<StreaksLeague> callback);
	public void getLosingStreaksForPerson(
			GameFilter gameFilter, String personName, int targetSize, AsyncCallback<StreaksLeague> callback);
	public void getWinningStreaks(
			GameFilter gameFilter, int targetSize, AsyncCallback<StreaksLeague> callback);
	public void getWinningStreaksForPerson(
			GameFilter gameFilter, String personName, int targetSize, AsyncCallback<StreaksLeague> callback);
	
	public void createHandDistributionGraph(
			GameFilter gameFilter, String personName, AsyncCallback<Map<Hand, Integer>> callback);
	public void createOpeningHandDistributionGraph(
			GameFilter gameFilter, String personName, AsyncCallback<Map<Hand, Integer>> callback);	
	public void createLeagueChart(
			GameFilter gameFilter, String averageColourHtmlName, int maximumColumns, AsyncCallback<ChartView<Double>> callback);

	public void getInitialDates(AsyncCallback<InitialDatesView> callback);

}
