package uk.co.unclealex.rokta.gwt.client.view.factory;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.controller.RoktaControllerCallback;
import uk.co.unclealex.rokta.gwt.client.model.GameModel;
import uk.co.unclealex.rokta.gwt.client.model.PlayerProfileModel;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;
import uk.co.unclealex.rokta.gwt.client.model.StreaksModel;
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
import uk.co.unclealex.rokta.gwt.client.view.main.game.GamePanel;
import uk.co.unclealex.rokta.gwt.client.view.main.game.RoundPanel;
import uk.co.unclealex.rokta.gwt.client.view.main.game.SelectPlayersPanel;
import uk.co.unclealex.rokta.gwt.client.view.main.game.SubmitPanel;

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
		leagueWidget.initialise();
		LeagueChartWidget leagueChartWidget = new LeagueChartWidget(roktaController, roktaModel.getLeagueChartModel());
		leagueChartWidget.initialise();
		LeaguePanel leaguePanel = new LeaguePanel(roktaController, leagueWidget, leagueChartWidget);
		leaguePanel.initialise();
		GameModel gameModel = roktaModel.getGameModel();
		SelectPlayersPanel selectPlayersPanel = new SelectPlayersPanel(roktaController, gameModel);
		selectPlayersPanel.initialise();
		RoundPanel roundPanel = new RoundPanel(roktaController, gameModel);
		roundPanel.initialise();
		SubmitPanel submitPanel = new SubmitPanel(roktaController, gameModel);
		submitPanel.initialise();
		GamePanel gamePanel = new GamePanel(roktaController, gameModel, selectPlayersPanel, roundPanel, submitPanel);
		gamePanel.initialise();
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
		MainPanel mainPanel = new MainPanel(
				roktaController, roktaModel.getMainPageModel(), leaguePanel, gamePanel,
				winningStreaksPanel, losingStreaksPanel, playerProfilesPanel);
		mainPanel.initialise();
		return mainPanel;
	}

	protected StreaksPanel createStreaksPanel(
			StreaksModel streaksModel, StreaksModel currentStreaksModel, String currentStreaksTitle, RoktaControllerCallback callback) {
		RoktaController roktaController = getRoktaController();
		StreaksWidget streaksWidget = new StreaksWidget(roktaController, streaksModel);
		streaksWidget.initialise();
		StreaksWidget currentStreaksWidget = new StreaksWidget(roktaController, currentStreaksModel);
		currentStreaksWidget.initialise();
		StreaksPanel streaksPanel = new StreaksPanel(roktaController, callback, streaksWidget, currentStreaksWidget, currentStreaksTitle);
		streaksPanel.initialise();
		return streaksPanel;
	}

	protected PlayerProfilesPanel createPlayerProfilesPanel() {
		PlayerProfilesPanel playerProfilesPanel =
			new PlayerProfilesPanel(getRoktaController(), getRoktaModel().getPlayerModel(), createPlayerProfilePanelsByPlayerName());
		playerProfilesPanel.initialise();
		return playerProfilesPanel;
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
					roktaController, playerProfileModel.getAllHandDistributionChartModel());
		allHandDistributionChartWidget.initialise();
		HandDistributionChartWidget openingHandDistributionChartWidget = 
			new HandDistributionChartWidget(
					roktaController, playerProfileModel.getOpeningHandDistributionChartModel());
		openingHandDistributionChartWidget.initialise();
		PlayerProfilePanel playerProfilePanel = 
			new PlayerProfilePanel(roktaController, playerName, allHandDistributionChartWidget, openingHandDistributionChartWidget);
		playerProfilePanel.initialise();
		return playerProfilePanel;
	}

}
