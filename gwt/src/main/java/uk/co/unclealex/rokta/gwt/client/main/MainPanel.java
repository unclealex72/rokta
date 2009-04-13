package uk.co.unclealex.rokta.gwt.client.main;

import java.util.HashMap;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.MainPanelChanger;
import uk.co.unclealex.rokta.gwt.client.PlayerListener;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;

public class MainPanel extends RoktaAwareComposite implements MainPanelChanger, PlayerListener {

	private DeckPanel i_panel = new DeckPanel();
	private LeaguePanel i_leaguePanel;
	private WinningStreaks i_winningStreaks;
	private LosingStreaks i_losingStreaks;
	
	private Map<String, PlayerProfilePanel> i_playerProfilePanelsByPlayerName = new HashMap<String, PlayerProfilePanel>();
	
	public MainPanel(String id, RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		DeckPanel panel = getPanel();
		panel.setAnimationEnabled(true);
		LeaguePanel leaguePanel = new LeaguePanel(roktaAdaptor);
		setLeaguePanel(leaguePanel);
		panel.add(leaguePanel);
		WinningStreaks winningStreaks = new WinningStreaks(roktaAdaptor);
		panel.add(winningStreaks);
		setWinningStreaks(winningStreaks);
		LosingStreaks losingStreaks = new LosingStreaks(roktaAdaptor);
		panel.add(losingStreaks);
		setLosingStreaks(losingStreaks);
		roktaAdaptor.addPlayerListener(this);
		initWidget(id, panel);
	}

	public void playerAdded(String playerName) {
		PlayerProfilePanel playerProfilePanel = new PlayerProfilePanel(getRoktaAdaptor(), playerName);
		getPlayerProfilePanelsByPlayerName().put(playerName, playerProfilePanel);
		getPanel().add(playerProfilePanel);
	}
	
	public String showLeague() {
		getPanel().showWidget(0);
		return getLeaguePanel().createTitle();
	}
	
	public String showWinningStreaks() {
		getPanel().showWidget(1);
		return getWinningStreaks().createTitle();
	}

	public String showLosingStreaks() {
		getPanel().showWidget(2);
		return getLosingStreaks().createTitle();
	}
	
	public String showProfile(String personName) {
		PlayerProfilePanel playerProfilePanel = getPlayerProfilePanelsByPlayerName().get(personName);
		if (playerProfilePanel != null) {
			DeckPanel panel = getPanel();
			panel.showWidget(panel.getWidgetIndex(playerProfilePanel));
			return playerProfilePanel.createTitle();
		}
		else {
			return null;
		}
	}
	
	public void startNewGame() {
		Window.alert("Not yet implemented.");
	}
	
	public String showHeadToHeads() {
		Window.alert("Not yet implemented.");
		return null;
	}
	
	public DeckPanel getPanel() {
		return i_panel;
	}

	public Map<String, PlayerProfilePanel> getPlayerProfilePanelsByPlayerName() {
		return i_playerProfilePanelsByPlayerName;
	}

	public void setPlayerProfilePanelsByPlayerName(Map<String, PlayerProfilePanel> playerProfilePanelsByPlayerName) {
		i_playerProfilePanelsByPlayerName = playerProfilePanelsByPlayerName;
	}

	public LeaguePanel getLeaguePanel() {
		return i_leaguePanel;
	}

	public void setLeaguePanel(LeaguePanel leaguePanel) {
		i_leaguePanel = leaguePanel;
	}

	public WinningStreaks getWinningStreaks() {
		return i_winningStreaks;
	}

	public void setWinningStreaks(WinningStreaks winningStreaks) {
		i_winningStreaks = winningStreaks;
	}

	public LosingStreaks getLosingStreaks() {
		return i_losingStreaks;
	}

	public void setLosingStreaks(LosingStreaks losingStreaks) {
		i_losingStreaks = losingStreaks;
	}

}
