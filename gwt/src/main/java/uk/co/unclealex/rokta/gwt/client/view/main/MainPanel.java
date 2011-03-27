package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.MainPageEnum;
import uk.co.unclealex.rokta.gwt.client.model.MainPageModel;
import uk.co.unclealex.rokta.gwt.client.util.MapMaker;
import uk.co.unclealex.rokta.gwt.client.view.main.game.GamePanel;

import com.google.gwt.user.client.ui.Widget;

public class MainPanel extends ModelAwareDeckPanel<MainPageEnum> {

	public MainPanel(
			RoktaController roktaController, MainPageModel mainPageModel, LeaguePanel leaguePanel, GamePanel gamePanel, 
			StreaksPanel winningStreaksPanel, StreaksPanel losingStreaksPanel, PlayerProfilesPanel playerProfilesPanel) {
		super(
			roktaController, mainPageModel, "main",
			new MapMaker<MainPageEnum, Widget>()
				.add(MainPageEnum.GAME, gamePanel)
				.add(MainPageEnum.LEAGUE, leaguePanel)
				.add(MainPageEnum.WINNING_STREAKS, winningStreaksPanel)
				.add(MainPageEnum.LOSING_STREAKS, losingStreaksPanel)
				.add(MainPageEnum.PROFILE, playerProfilesPanel)
				);
	}
}
