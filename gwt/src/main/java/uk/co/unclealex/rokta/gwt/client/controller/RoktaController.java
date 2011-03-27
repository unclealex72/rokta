package uk.co.unclealex.rokta.gwt.client.controller;

import java.util.Date;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.model.DetailPageEnum;
import uk.co.unclealex.rokta.gwt.client.model.HandDistributionChartModel;
import uk.co.unclealex.rokta.gwt.client.model.MainPageEnum;
import uk.co.unclealex.rokta.gwt.client.model.PlayerProfileModel;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;
import uk.co.unclealex.rokta.gwt.client.view.DefaultAsyncCallback;
import uk.co.unclealex.rokta.gwt.client.view.GwtRoktaFacadeAsync;
import uk.co.unclealex.rokta.gwt.client.view.decoration.TitleMessages;
import uk.co.unclealex.rokta.gwt.client.view.main.ProfileMessages;
import uk.co.unclealex.rokta.gwt.client.view.side.NavigationMessages;
import uk.co.unclealex.rokta.gwt.client.view.side.StatisticsMessages;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.ChartView;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RoktaController {

	private static final String AVERAGE_COLOUR_HTML_NAME = "Orange";
	private static final int MAXIMUM_COLUMNS = 20;
	private static final int STREAKS_SIZE = 10;
	
	private RoktaModel i_roktaModel;
	private GwtRoktaFacadeAsync i_gwtRoktaFacadeAsync;
	private GameFilter i_gameFilter;
	private StatisticsMessages i_statisticsMessages = GWT.create(StatisticsMessages.class);
	private NavigationMessages i_navigationMessages = GWT.create(NavigationMessages.class);
	private TitleMessages i_titleMessages = GWT.create(TitleMessages.class);
	private ProfileMessages i_profileMessages = GWT.create(ProfileMessages.class);
	private String i_title;
	private boolean i_titleIncludesGameFilterDescription;
	private String i_gameFilterDescription;
	private RoktaGameController i_roktaGameController;
	
	public RoktaController(RoktaModel roktaModel, GameFilter gameFilter, GwtRoktaFacadeAsync gwtRoktaFacadeAsync) {
		super();
		i_roktaModel = roktaModel;
		i_gameFilter = gameFilter;
		i_gwtRoktaFacadeAsync = gwtRoktaFacadeAsync;
		i_roktaGameController = new RoktaGameController(roktaModel.getGameModel(), roktaModel.getCurrentlyLoadingModel(), gwtRoktaFacadeAsync);
	}

	protected void notYetImplemented() {
		Window.alert("Not yet implemented.");
	}

	public void start() {
		AsyncCallback<String> callback = new DefaultAsyncCallback<String>() {
			public void onSuccess(String gameFilterDescription) {
				setGameFilterDescription(gameFilterDescription);
				getRoktaModel().clear();
				requestLoadLeague();
				showLeague();
			}
		};
		getGwtRoktaFacadeAsync().describeGameFilter(getGameFilter(), callback);
	}
	
	public void startNewGame() {
		showMainPage(MainPageEnum.GAME, getNavigationMessages().newGame(), false);
	}

	public void showPlayerProfile(String playerName) {
		getRoktaModel().getPlayerModel().setValue(playerName);
		showMainPage(MainPageEnum.PROFILE, getProfileMessages().profileTitle(playerName), true);
	}

	public void showLeague() {
		showMainPage(MainPageEnum.LEAGUE, getNavigationMessages().league(), true);
	}

	public void showWinningStreaks() {
		showMainPage(MainPageEnum.WINNING_STREAKS, getStatisticsMessages().winningStreaks(), true);
	}

	public void showLosingStreaks() {
		showMainPage(MainPageEnum.LOSING_STREAKS, getStatisticsMessages().losingStreaks(), true);
	}

	public void showHeadToHeads() {
		notYetImplemented();
	}

	protected void showMainPage(MainPageEnum mainPageEnum, String title, boolean titleIncludesGameFilterDescription) {
		getRoktaModel().getMainPageModel().setValue(mainPageEnum);
		updateTitle(title, titleIncludesGameFilterDescription);
	}

	protected void updateTitle(String title, boolean titleIncludesGameFilterDescription) {
		setTitle(title);
		setTitleIncludesGameFilterDescription(titleIncludesGameFilterDescription);
		String fullTitle =
			titleIncludesGameFilterDescription?getTitleMessages().appendGameFilterDescription(title, getGameFilterDescription()):title;
		getRoktaModel().getTitleModel().setValue(fullTitle);
	}

	protected void refreshMainPage() {
		MainPageEnum visiblePage = getRoktaModel().getMainPageModel().getValue();
		if (visiblePage == null) {
			return;
		}
		switch (visiblePage) {
		case LEAGUE : {
			requestLoadLeague();
			break;
		}
		case LOSING_STREAKS : {
			requestLoadLosingStreaks();
			break;
		}
		case WINNING_STREAKS : {
			requestLoadWinningStreaks();
			break;
		}
		case HEAD_TO_HEADS : {
			requestLoadHeadToHeads();
			break;
		}
		case PROFILE : {
			requestLoadPlayerProfile();
			break;
		}
		}
	}
	
	public void changeGameFilter(GameFilter gameFilter) {
		AsyncCallback<String> callback = new DefaultAsyncCallback<String>() {
			public void onSuccess(String gameFilterDescription) {
				setGameFilterDescription(gameFilterDescription);
				updateTitle(getTitle(), isTitleIncludesGameFilterDescription());
			}
		};
		setGameFilter(gameFilter);
		getRoktaModel().clear();
		refreshMainPage();
		getGwtRoktaFacadeAsync().describeGameFilter(gameFilter, callback);
	}

	public void showFilters() {
		getRoktaModel().getDetailPageModel().setValue(DetailPageEnum.FILTERS);
	}

	public void showStatistics() {
		getRoktaModel().getDetailPageModel().setValue(DetailPageEnum.STATISTICS);
	}

	public void showProfiles() {
		getRoktaModel().getDetailPageModel().setValue(DetailPageEnum.PROFILES);
	}

	public void requestLoadLeague() {
		RoktaModel roktaModel = getRoktaModel();
		if (roktaModel.getLeagueModel().getValue() == null) {
			loadLeague();
		}
		if (roktaModel.getLeagueChartModel().getValue() == null) {
			loadLeagueChart();
		}
	}

	protected void loadLeague() {
		getGwtRoktaFacadeAsync().getLeague(
			getGameFilter(), new Date(), new LoadingModelAsyncCallback<League>(
					getRoktaModel().getCurrentlyLoadingModel(), getRoktaModel().getLeagueModel()));
	}

	protected void loadLeagueChart() {
		getGwtRoktaFacadeAsync().createLeagueChart(
				getGameFilter(), AVERAGE_COLOUR_HTML_NAME, MAXIMUM_COLUMNS, 
				new LoadingModelAsyncCallback<ChartView<Double>>(
						getRoktaModel().getCurrentlyLoadingModel(), getRoktaModel().getLeagueChartModel()));
	}

	public void requestLoadWinningStreaks() {
		RoktaModel roktaModel = getRoktaModel();
		if (roktaModel.getWinningStreaksModel().getValue() == null) {
			loadWinningStreaks();
		}
		if (roktaModel.getCurrentWinningStreaksModel().getValue() == null) {
			loadCurrentWinningStreaks();
		}
	}

	public void requestLoadLosingStreaks() {
		RoktaModel roktaModel = getRoktaModel();
		if (roktaModel.getLosingStreaksModel().getValue() == null) {
			loadLosingStreaks();
		}
		if (roktaModel.getCurrentLosingStreaksModel().getValue() == null) {
			loadCurrentLosingStreaks();
		}
	}

	protected void loadWinningStreaks() {
		getGwtRoktaFacadeAsync().getWinningStreaks(
				getGameFilter(), STREAKS_SIZE, 
				new LoadingModelAsyncCallback<StreaksLeague>(
						getRoktaModel().getCurrentlyLoadingModel(), getRoktaModel().getWinningStreaksModel()));
	}

	protected void loadCurrentWinningStreaks() {
		getGwtRoktaFacadeAsync().getCurrentWinningStreaks(
				getGameFilter(), 
				new LoadingModelAsyncCallback<StreaksLeague>(
						getRoktaModel().getCurrentlyLoadingModel(), getRoktaModel().getCurrentWinningStreaksModel()));
	}

	protected void loadLosingStreaks() {
		getGwtRoktaFacadeAsync().getLosingStreaks(
				getGameFilter(), STREAKS_SIZE, 
				new LoadingModelAsyncCallback<StreaksLeague>(
						getRoktaModel().getCurrentlyLoadingModel(), getRoktaModel().getLosingStreaksModel()));
	}

	protected void loadCurrentLosingStreaks() {
		getGwtRoktaFacadeAsync().getCurrentLosingStreaks(
				getGameFilter(), 
				new LoadingModelAsyncCallback<StreaksLeague>(
						getRoktaModel().getCurrentlyLoadingModel(), getRoktaModel().getCurrentLosingStreaksModel()));
	}

	protected void requestLoadPlayerProfile() {
		requestLoadPlayerProfile(getRoktaModel().getPlayerModel().getValue());
	}

	public void requestLoadPlayerProfile(String playerName) {
		PlayerProfileModel playerProfileModel = getRoktaModel().getPlayerProfileModelsByPlayerName().get(playerName);
		if (playerProfileModel != null) {
			if (playerProfileModel.getAllHandDistributionChartModel().getValue() == null) {
				loadAllHandDistributionChartModel(playerName);
			}
			if (playerProfileModel.getOpeningHandDistributionChartModel().getValue() == null) {
				loadOpeningHandDistributionChartModel(playerName);
			}
		}
	}

	protected void loadAllHandDistributionChartModel(String playerName) {
		HandDistributionChartModel allHandDistributionChartModel = 
			getRoktaModel().getPlayerProfileModelsByPlayerName().get(playerName).getAllHandDistributionChartModel();
		getGwtRoktaFacadeAsync().createHandDistributionGraph(
				getGameFilter(), playerName,
				new LoadingModelAsyncCallback<Map<Hand,Integer>>(
						getRoktaModel().getCurrentlyLoadingModel(), allHandDistributionChartModel));
	}

	protected void loadOpeningHandDistributionChartModel(String playerName) {
		HandDistributionChartModel openingHandDistributionChartModel = 
			getRoktaModel().getPlayerProfileModelsByPlayerName().get(playerName).getOpeningHandDistributionChartModel();
		getGwtRoktaFacadeAsync().createOpeningHandDistributionGraph(
				getGameFilter(), playerName, new LoadingModelAsyncCallback<Map<Hand,Integer>>(
						getRoktaModel().getCurrentlyLoadingModel(), openingHandDistributionChartModel));
	}

	protected void requestLoadHeadToHeads() {
		notYetImplemented();
	}

	public RoktaModel getRoktaModel() {
		return i_roktaModel;
	}

	public GwtRoktaFacadeAsync getGwtRoktaFacadeAsync() {
		return i_gwtRoktaFacadeAsync;
	}

	public GameFilter getGameFilter() {
		return i_gameFilter;
	}

	public void setGameFilter(GameFilter gameFilter) {
		i_gameFilter = gameFilter;
	}

	public StatisticsMessages getStatisticsMessages() {
		return i_statisticsMessages;
	}

	public NavigationMessages getNavigationMessages() {
		return i_navigationMessages;
	}

	public ProfileMessages getProfileMessages() {
		return i_profileMessages;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}
	
	public String getTitle() {
		return i_title;
	}

	public void setTitle(String title) {
		i_title = title;
	}

	public String getGameFilterDescription() {
		return i_gameFilterDescription;
	}

	public void setGameFilterDescription(String gameFilterDescription) {
		i_gameFilterDescription = gameFilterDescription;
	}

	public boolean isTitleIncludesGameFilterDescription() {
		return i_titleIncludesGameFilterDescription;
	}

	public void setTitleIncludesGameFilterDescription(boolean titleIncludesGameFilterDescription) {
		i_titleIncludesGameFilterDescription = titleIncludesGameFilterDescription;
	}

	public RoktaGameController getRoktaGameController() {
		return i_roktaGameController;
	}

	public void setRoktaGameController(RoktaGameController roktaGameController) {
		i_roktaGameController = roktaGameController;
	}
}
