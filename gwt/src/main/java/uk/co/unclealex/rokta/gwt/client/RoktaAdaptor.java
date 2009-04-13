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


public interface RoktaAdaptor extends MainPanelChanger, DetailPanelChanger {

	public void populateDefaults();

	public void getDefaultGameFilter(AsyncCallback<GameFilter> callback);

	public void getAllUsersNames(AsyncCallback<List<String>> callback);

	public void getAllPlayerNames(AsyncCallback<List<String>> callback);

	public void getCopyright(AsyncCallback<String> callback);

	public void getLeague(final Date now, final AsyncCallback<League> callback);

	public void getTitle(String prefix, final AsyncCallback<String> callback);
	
	public void getCurrentLosingStreaks(final AsyncCallback<StreaksLeague> callback);
	public void getCurrentWinningStreaks(final AsyncCallback<StreaksLeague> callback);
	public void getLosingStreaks(final int targetSize, final AsyncCallback<StreaksLeague> callback);
	public void getWinningStreaks(final int targetSize, final AsyncCallback<StreaksLeague> callback);
	
	public void createLeagueChart(String string, int i, AsyncCallback<ChartView<Double>> callback);
	public void createHandDistributionGraph(String personName, AsyncCallback<Map<Hand, Integer>> callback);
	public void createOpeningHandDistributionGraph(String personName, AsyncCallback<Map<Hand, Integer>> callback);	
	public void getLosingStreaksForPerson(final String personName, final int targetSize, final AsyncCallback<StreaksLeague> callback);
	public void getWinningStreaksForPerson(final String personName, final int targetSize, final AsyncCallback<StreaksLeague> callback);

	public void addGameFilterListener(GameFilterListener gameFilterListener);
	public void removeGameFilterListener(GameFilterListener gameFilterListener);
	public void changeGameFilter(GameFilter gameFilter);
	
	public void addPlayerListener(PlayerListener playerListener);
	public void removePlayerListener(PlayerListener playerListener);
	public void addPlayer(String playerName);
	
	public void getInitialDates(AsyncCallback<InitialDatesView> callback);
}
