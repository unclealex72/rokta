package uk.co.unclealex.rokta.gwt.client.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class RoktaModel extends CompositeModel {

	private DetailPageModel i_detailPageModel = new DetailPageModel();
	private LeagueChartModel i_leagueChartModel = new LeagueChartModel();
	private LeagueModel i_leagueModel = new LeagueModel();
	private MainPageModel i_mainPageModel = new MainPageModel();
	private StreaksModel i_winningStreaksModel = new StreaksModel();
	private StreaksModel i_currentWinningStreaksModel = new StreaksModel();
	private StreaksModel i_losingStreaksModel = new StreaksModel();
	private StreaksModel i_currentLosingStreaksModel = new StreaksModel();
	private InitialDatesModel i_initialDatesModel = new InitialDatesModel();
	private TitleModel i_titleModel = new TitleModel();
	private PlayerModel i_playerModel = new PlayerModel();
	private Map<String, PlayerProfileModel> i_playerProfileModelsByPlayerName = new HashMap<String, PlayerProfileModel>();
	
	public RoktaModel(Collection<String> playerNames) {
		add(i_detailPageModel);
		add(i_leagueChartModel);
		add(i_leagueModel);
		add(i_mainPageModel);
		add(i_winningStreaksModel);
		add(i_currentWinningStreaksModel);
		add(i_losingStreaksModel);
		add(i_currentLosingStreaksModel);
		add(i_initialDatesModel);
		add(i_titleModel);
		add(i_playerModel);
		Map<String, PlayerProfileModel> playerProfileModelsByPlayerName = getPlayerProfileModelsByPlayerName();
		for (String playerName : playerNames) {
			PlayerProfileModel playerProfileModel = new PlayerProfileModel();
			playerProfileModelsByPlayerName.put(playerName, playerProfileModel);
			add(playerProfileModel);
		}
	}

	public DetailPageModel getDetailPageModel() {
		return i_detailPageModel;
	}

	public LeagueChartModel getLeagueChartModel() {
		return i_leagueChartModel;
	}

	public LeagueModel getLeagueModel() {
		return i_leagueModel;
	}

	public MainPageModel getMainPageModel() {
		return i_mainPageModel;
	}

	public StreaksModel getWinningStreaksModel() {
		return i_winningStreaksModel;
	}

	public StreaksModel getCurrentWinningStreaksModel() {
		return i_currentWinningStreaksModel;
	}

	public StreaksModel getLosingStreaksModel() {
		return i_losingStreaksModel;
	}

	public StreaksModel getCurrentLosingStreaksModel() {
		return i_currentLosingStreaksModel;
	}

	public InitialDatesModel getInitialDatesModel() {
		return i_initialDatesModel;
	}

	public TitleModel getTitleModel() {
		return i_titleModel;
	}

	public PlayerModel getPlayerModel() {
		return i_playerModel;
	}

	public Map<String, PlayerProfileModel> getPlayerProfileModelsByPlayerName() {
		return i_playerProfileModelsByPlayerName;
	}
}
