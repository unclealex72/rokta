package uk.co.unclealex.rokta.gwt.client.view.factory;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.controller.RoktaControllerCallback;
import uk.co.unclealex.rokta.gwt.client.model.PlayerProfileModel;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;
import uk.co.unclealex.rokta.gwt.client.model.StreaksModel;
import uk.co.unclealex.rokta.gwt.client.view.main.GamePanel;
import uk.co.unclealex.rokta.gwt.client.view.main.HandDistributionChartWidget;
import uk.co.unclealex.rokta.gwt.client.view.main.LeagueChartWidget;
import uk.co.unclealex.rokta.gwt.client.view.main.LeaguePanel;
import uk.co.unclealex.rokta.gwt.client.view.main.LeagueWidget;
import uk.co.unclealex.rokta.gwt.client.view.main.MainPanel;
import uk.co.unclealex.rokta.gwt.client.view.main.PlayerProfilePanel;
import uk.co.unclealex.rokta.gwt.client.view.main.PlayerProfilesPanel;
import uk.co.unclealex.rokta.gwt.client.view.main.ProfileMessages;
import uk.co.unclealex.rokta.gwt.client.view.main.StreaksMessages;
import uk.co.unclealex.rokta.gwt.client.view.main.StreaksPanel;
import uk.co.unclealex.rokta.gwt.client.view.main.StreaksWidget;

import com.google.gwt.core.client.GWT;

public class MainPanelFactory extends ViewFactory {

	public MainPanelFactory(RoktaController roktaController, RoktaModel roktaModel) {
		super(roktaController, roktaModel);
	}

	public MainPanel createMainPanel() {
		StreaksMessages streaksMessages = GWT.create(StreaksMessages.class);
		RoktaController roktaController = getRoktaController();
		RoktaModel roktaModel = getRoktaModel();
		LeagueWidget leagueWidget = new LeagueWidget(roktaController, roktaModel.getLeagueModel());
		LeagueChartWidget leagueChartWidget = new LeagueChartWidget(roktaController, roktaModel.getLeagueChartModel());
		LeaguePanel leaguePanel = new LeaguePanel(roktaController, leagueWidget, leagueChartWidget);
		GamePanel gamePanel = new GamePanel();
		StreaksPanel winningStreaksPanel =
			createStreaksPanel(
					roktaModel.getWinningStreaksModel(), roktaModel.getCurrentWinningStreaksModel(), streaksMessages.currentWinningStreaks(),
					new RoktaControllerCallback() {
						public void execute(RoktaController roktaController) {
							roktaController.requestLoadWinningStreaks();
						}
					});
		StreaksPanel losingStreaksPanel =
			createStreaksPanel(
					roktaModel.getLosingStreaksModel(), roktaModel.getCurrentLosingStreaksModel(), streaksMessages.currentLosingStreaks(),
					new RoktaControllerCallback() {
						public void execute(RoktaController roktaController) {
							roktaController.requestLoadLosingStreaks();
						}
					});
		PlayerProfilesPanel playerProfilesPanel = createPlayerProfilesPanel();
		return new MainPanel(
				roktaController, roktaModel.getMainPageModel(), leaguePanel, gamePanel,
				winningStreaksPanel, losingStreaksPanel, playerProfilesPanel);
	}

	protected StreaksPanel createStreaksPanel(
			StreaksModel streaksModel, StreaksModel currentStreaksModel, String currentStreaksTitle, RoktaControllerCallback callback) {
		RoktaController roktaController = getRoktaController();
		StreaksWidget streaksWidget = new StreaksWidget(roktaController, streaksModel);
		StreaksWidget currentStreaksWidget = new StreaksWidget(roktaController, currentStreaksModel);
		return new StreaksPanel(roktaController, callback, streaksWidget, currentStreaksWidget, currentStreaksTitle);
	}

	protected PlayerProfilesPanel createPlayerProfilesPanel() {
		return new PlayerProfilesPanel(getRoktaController(), getRoktaModel().getPlayerModel(), createPlayerProfilePanelsByPlayerName());
	}

	protected SortedMap<String, PlayerProfilePanel> createPlayerProfilePanelsByPlayerName() {
		SortedMap<String, PlayerProfilePanel> playerProfilePanelsByPlayerName = new TreeMap<String, PlayerProfilePanel>();
		ProfileMessages messages = GWT.create(ProfileMessages.class);
		for (Map.Entry<String, PlayerProfileModel> entry : getRoktaModel().getPlayerProfileModelsByPlayerName().entrySet()) {
			String playerName = entry.getKey();
			PlayerProfileModel playerProfileModel = entry.getValue();
			playerProfilePanelsByPlayerName.put(playerName, createPlayerProfilePanel(playerName, playerProfileModel, messages));
		}
		return playerProfilePanelsByPlayerName;
	}

	protected PlayerProfilePanel createPlayerProfilePanel(
			String playerName, PlayerProfileModel playerProfileModel, ProfileMessages messages) {
		RoktaController roktaController = getRoktaController();
		HandDistributionChartWidget allHandDistributionChartWidget = 
			new HandDistributionChartWidget(
					roktaController, playerProfileModel.getAllHandDistributionChartModel(), messages.playersHands(playerName));
		HandDistributionChartWidget openingHandDistributionChartWidget = 
			new HandDistributionChartWidget(
					roktaController, playerProfileModel.getOpeningHandDistributionChartModel(), messages.playersOpeningHands(playerName));
		return new PlayerProfilePanel(roktaController, playerName, allHandDistributionChartWidget, openingHandDistributionChartWidget);
	}

}
