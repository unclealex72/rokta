package uk.co.unclealex.rokta.gwt.client.controller;

import java.util.Date;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.model.DetailPageEnum;
import uk.co.unclealex.rokta.gwt.client.model.HandDistributionChartModel;
import uk.co.unclealex.rokta.gwt.client.model.MainPageEnum;
import uk.co.unclealex.rokta.gwt.client.model.PlayerProfileModel;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;
import uk.co.unclealex.rokta.gwt.client.view.GwtReadOnlyRoktaFacadeAsync;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.ChartView;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

import com.google.gwt.user.client.Window;

public class RoktaController {

	private static final String AVERAGE_COLOUR_HTML_NAME = "Orange";
	private static final int MAXIMUM_COLUMNS = 20;
	private static final int STREAKS_SIZE = 10;
	
	private RoktaModel i_roktaModel;
	private GwtReadOnlyRoktaFacadeAsync i_gwtReadOnlyRoktaFacadeAsync;
	private GameFilter i_gameFilter;
	
	public RoktaController(RoktaModel roktaModel, GameFilter gameFilter, GwtReadOnlyRoktaFacadeAsync gwtReadOnlyRoktaFacadeAsync) {
		super();
		i_roktaModel = roktaModel;
		i_gameFilter = gameFilter;
		i_gwtReadOnlyRoktaFacadeAsync = gwtReadOnlyRoktaFacadeAsync;

	}

	protected void notYetImplemented() {
		Window.alert("Not yet implemented.");
	}

	public void start() {
		getRoktaModel().clear();
		requestLoadLeague();
		showLeague();
	}
	
	public void startNewGame() {
		notYetImplemented();
	}

	public void showPlayerProfile(String playerName) {
		getRoktaModel().getPlayerModel().setValue(playerName);
		showMainPage(MainPageEnum.PROFILE);
	}

	public void showLeague() {
		showMainPage(MainPageEnum.LEAGUE);
	}

	public void showWinningStreaks() {
		showMainPage(MainPageEnum.WINNING_STREAKS);
	}

	public void showLosingStreaks() {
		showMainPage(MainPageEnum.LOSING_STREAKS);
	}

	public void showHeadToHeads() {
		notYetImplemented();
	}

	protected void showMainPage(MainPageEnum mainPageEnum) {
		getRoktaModel().getMainPageModel().setValue(mainPageEnum);
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
		setGameFilter(gameFilter);
		getRoktaModel().clear();
		refreshMainPage();
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
		getGwtReadOnlyRoktaFacadeAsync().getLeague(
			getGameFilter(), new Date(), new LoadingModelAsyncCallback<League>(getRoktaModel().getLeagueModel()));
	}

	protected void loadLeagueChart() {
		getGwtReadOnlyRoktaFacadeAsync().createLeagueChart(
				getGameFilter(), AVERAGE_COLOUR_HTML_NAME, MAXIMUM_COLUMNS, 
				new LoadingModelAsyncCallback<ChartView<Double>>(getRoktaModel().getLeagueChartModel()));
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
		getGwtReadOnlyRoktaFacadeAsync().getWinningStreaks(
				getGameFilter(), STREAKS_SIZE, 
				new LoadingModelAsyncCallback<StreaksLeague>(getRoktaModel().getWinningStreaksModel()));
	}

	protected void loadCurrentWinningStreaks() {
		getGwtReadOnlyRoktaFacadeAsync().getCurrentWinningStreaks(
				getGameFilter(), 
				new LoadingModelAsyncCallback<StreaksLeague>(getRoktaModel().getCurrentWinningStreaksModel()));
	}

	protected void loadLosingStreaks() {
		getGwtReadOnlyRoktaFacadeAsync().getLosingStreaks(
				getGameFilter(), STREAKS_SIZE, 
				new LoadingModelAsyncCallback<StreaksLeague>(getRoktaModel().getLosingStreaksModel()));
	}

	protected void loadCurrentLosingStreaks() {
		getGwtReadOnlyRoktaFacadeAsync().getCurrentLosingStreaks(
				getGameFilter(), 
				new LoadingModelAsyncCallback<StreaksLeague>(getRoktaModel().getCurrentLosingStreaksModel()));
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
		getGwtReadOnlyRoktaFacadeAsync().createHandDistributionGraph(
				getGameFilter(), playerName, new LoadingModelAsyncCallback<Map<Hand,Integer>>(allHandDistributionChartModel));
	}

	protected void loadOpeningHandDistributionChartModel(String playerName) {
		HandDistributionChartModel openingHandDistributionChartModel = 
			getRoktaModel().getPlayerProfileModelsByPlayerName().get(playerName).getOpeningHandDistributionChartModel();
		getGwtReadOnlyRoktaFacadeAsync().createOpeningHandDistributionGraph(
				getGameFilter(), playerName, new LoadingModelAsyncCallback<Map<Hand,Integer>>(openingHandDistributionChartModel));
	}

	protected void requestLoadHeadToHeads() {
		notYetImplemented();
	}

	public RoktaModel getRoktaModel() {
		return i_roktaModel;
	}

	public GwtReadOnlyRoktaFacadeAsync getGwtReadOnlyRoktaFacadeAsync() {
		return i_gwtReadOnlyRoktaFacadeAsync;
	}

	public GameFilter getGameFilter() {
		return i_gameFilter;
	}

	public void setGameFilter(GameFilter gameFilter) {
		i_gameFilter = gameFilter;
	}
}
