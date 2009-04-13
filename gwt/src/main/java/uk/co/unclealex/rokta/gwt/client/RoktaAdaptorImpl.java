package uk.co.unclealex.rokta.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.ChartView;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class RoktaAdaptorImpl implements RoktaAdaptor {

	GwtReadOnlyRoktaFacadeAsync i_gwtReadOnlyRoktaFacadeAsync;
	private GameFilter i_gameFilter;
	private List<PlayerListener> i_playerListeners = new ArrayList<PlayerListener>();
	private List<GameFilterListener> i_gameFilterListeners = new ArrayList<GameFilterListener>();
	private MainPanelChanger i_mainPanelChanger;
	private DetailPanelChanger i_detailPanelChanger;

	public RoktaAdaptorImpl(MainPanelChanger mainPanelChanger, DetailPanelChanger detailPanelChanger) {
		GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync = 
			(GwtReadOnlyRoktaFacadeAsync) GWT.create(GwtReadOnlyRoktaFacade.class);
		ServiceDefTarget target = (ServiceDefTarget) gwtReadOnlyRoktaFacadeAsync;
		target.setServiceEntryPoint("../handler/rpc");
		i_gwtReadOnlyRoktaFacadeAsync = gwtReadOnlyRoktaFacadeAsync;
		i_mainPanelChanger = mainPanelChanger;
		i_detailPanelChanger = detailPanelChanger;
	}

	public void populateDefaults() {
		populateDefaultPlayers();
	}
	
	protected void populateDefaultPlayers() {
		AsyncCallback<List<String>> playerNameCallback = new DefaultAsyncCallback<List<String>>() {
			public void onSuccess(List<String> playerNames) {
				for (String playerName : playerNames) {
					addPlayer(playerName);
				}
				populateDefaultGameFilter();
			}
		};
		getAllPlayerNames(playerNameCallback);
	}

	protected void populateDefaultGameFilter() {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				// Do nothing
			}
		}.execute();
	}
		
	public void getDefaultGameFilter(AsyncCallback<GameFilter> callback) {
		getGwtReadOnlyRoktaFacadeAsync().getDefaultGameFilter(callback);
	}
	
	public void getAllUsersNames(AsyncCallback<List<String>> callback) {
		getGwtReadOnlyRoktaFacadeAsync().getAllUsersNames(callback);
	}
	public void getAllPlayerNames(AsyncCallback<List<String>> callback) {
		getGwtReadOnlyRoktaFacadeAsync().getAllPlayerNames(callback);
	}
	public void getCopyright(AsyncCallback<String> callback) {
		getGwtReadOnlyRoktaFacadeAsync().getCopyright(callback);
	}

	public void getTitle(final String prefix, final AsyncCallback<String> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getTitle(gameFilter, prefix, callback);
			}
		}.execute();
	}
	
	public void getLeague(final Date now, final AsyncCallback<League> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getLeague(gameFilter, now, callback);
			}
		}.execute();
	}

	public void getCurrentLosingStreaks(final AsyncCallback<StreaksLeague> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getCurrentLosingStreaks(gameFilter, callback);
			}
		}.execute();
	}

	public void getCurrentWinningStreaks(final AsyncCallback<StreaksLeague> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getCurrentWinningStreaks(gameFilter, callback);
			}
		}.execute();
	}

	public void getLosingStreaks(final int targetSize, final AsyncCallback<StreaksLeague> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getLosingStreaks(gameFilter, targetSize, callback);
			}
		}.execute();
	}

	public void getLosingStreaksForPerson(final String personName, final int targetSize, final AsyncCallback<StreaksLeague> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getLosingStreaksForPerson(gameFilter, personName, targetSize, callback);
			}
		}.execute();
	}

	public void getWinningStreaks(final int targetSize, final AsyncCallback<StreaksLeague> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getWinningStreaks(gameFilter, targetSize, callback);
			}
		}.execute();
	}

	public void getWinningStreaksForPerson(final String personName, final int targetSize, final AsyncCallback<StreaksLeague> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.getWinningStreaksForPerson(gameFilter, personName, targetSize, callback);
			}
		}.execute();
	}

	public void createLeagueChart(
			final String averageColourHtmlName, final int maximumColumns, final AsyncCallback<ChartView<Double>> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.createLeagueChart(gameFilter, averageColourHtmlName, maximumColumns, callback);
			}
		}.execute();
	}
	
	public void createHandDistributionGraph(final String personName, final AsyncCallback<Map<Hand, Integer>> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.createHandDistributionGraph(gameFilter, personName, callback);
			}
		}.execute();
	}
	
	public void createOpeningHandDistributionGraph(final String personName, final AsyncCallback<Map<Hand, Integer>> callback) {
		new GameFilterCallback() {
			@Override
			public void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter) {
				gwtReadOnlyRoktaFacadeAsync.createOpeningHandDistributionGraph(gameFilter, personName, callback);
			}
		}.execute();
	}
	
	protected abstract class GameFilterCallback {
		
		public abstract void call(GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync, GameFilter gameFilter);
		
		public void execute() {
			GameFilter gameFilter = getGameFilter();
			final GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync = getGwtReadOnlyRoktaFacadeAsync();
			if (gameFilter == null) {
				AsyncCallback<GameFilter> gameFilterCallback = new DefaultAsyncCallback<GameFilter>() {
					public void onSuccess(GameFilter gameFilter) {
						changeGameFilter(gameFilter);
						call(gwtReadOnlyRoktaFacadeAsync, gameFilter);
					}
				};
				gwtReadOnlyRoktaFacadeAsync.getDefaultGameFilter(gameFilterCallback);
			}
			else {
				call(gwtReadOnlyRoktaFacadeAsync, gameFilter);
			}
		}

	}
	
	public void addGameFilterListener(GameFilterListener gameFilterListener) {
		getGameFilterListeners().add(gameFilterListener);
	}
	
	public void removeGameFilterListener(GameFilterListener gameFilterListener) {
		getGameFilterListeners().remove(gameFilterListener);
	}

	public void changeGameFilter(GameFilter gameFilter) {
		setGameFilter(gameFilter);
		for (GameFilterListener listener : getGameFilterListeners()) {
			listener.onGameFilterChange(gameFilter);
		}
	}

	public void addPlayerListener(PlayerListener playerListener) {
		getPlayerListeners().add(playerListener);
	}
	
	public void removePlayerListener(PlayerListener playerListener) {
		getPlayerListeners().remove(playerListener);
	}
	
	public void addPlayer(String playerName) {
		for (PlayerListener playerListener : getPlayerListeners()) {
			playerListener.playerAdded(playerName);
		}
	}
	
	public String showLeague() {
		return getMainPanelChanger().showLeague();
	}
	
	public String showLosingStreaks() {
		return getMainPanelChanger().showLosingStreaks();
	}
	
	public String showWinningStreaks() {
		return getMainPanelChanger().showWinningStreaks();
	}

	public String showHeadToHeads() {
		return getMainPanelChanger().showHeadToHeads();
	}
	
	public String showProfile(String personName) {
		return getMainPanelChanger().showProfile(personName);
	}

	public void startNewGame() {
		getMainPanelChanger().startNewGame();
	}
	
	public void showFilters() {
		getDetailPanelChanger().showFilters();
	}
	
	public void showProfiles() {
		getDetailPanelChanger().showProfiles();
	}
	
	public void showStatistics() {
		getDetailPanelChanger().showStatistics();
	}
	
	public void getInitialDates(AsyncCallback<InitialDatesView> callback) {
		getGwtReadOnlyRoktaFacadeAsync().getInitialDates(callback);
	}
	
	public GwtReadOnlyRoktaFacadeAsync getGwtReadOnlyRoktaFacadeAsync() {
		return i_gwtReadOnlyRoktaFacadeAsync;
	}

	protected GameFilter getGameFilter() {
		return i_gameFilter;
	}

	protected void setGameFilter(GameFilter gameFilter) {
		i_gameFilter = gameFilter;
	}

	protected List<GameFilterListener> getGameFilterListeners() {
		return i_gameFilterListeners;
	}

	protected void setGameFilterListeners(List<GameFilterListener> gameFilterListeners) {
		i_gameFilterListeners = gameFilterListeners;
	}

	public MainPanelChanger getMainPanelChanger() {
		return i_mainPanelChanger;
	}

	public List<PlayerListener> getPlayerListeners() {
		return i_playerListeners;
	}

	public DetailPanelChanger getDetailPanelChanger() {
		return i_detailPanelChanger;
	}
}
