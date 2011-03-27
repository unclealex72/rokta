package uk.co.unclealex.rokta.gwt.client.view.main;

import java.util.SortedMap;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.PlayerModel;
import uk.co.unclealex.rokta.gwt.client.view.ModelAwareComposite;

import com.google.gwt.user.client.ui.DeckPanel;

public class PlayerProfilesPanel extends ModelAwareComposite<String, DeckPanel> {

	private DeckPanel i_panel = new DeckPanel();
	private SortedMap<String, PlayerProfilePanel> i_playerProfilePanelsByPlayerName;
	
	public PlayerProfilesPanel(
			RoktaController roktaController, PlayerModel playerModel,
			SortedMap<String, PlayerProfilePanel> playerProfilePanelsByPlayerName) {
		super(roktaController, playerModel);
		i_playerProfilePanelsByPlayerName = playerProfilePanelsByPlayerName;
	}

	@Override
	protected DeckPanel create() {
		DeckPanel panel = getPanel();
		for (PlayerProfilePanel playerProfilePanel : getPlayerProfilePanelsByPlayerName().values()) {
			panel.add(playerProfilePanel);
		}
		return panel;
	}
	
	public void onValueChanged(String playerName) {
		PlayerProfilePanel playerProfilePanel = getPlayerProfilePanelsByPlayerName().get(playerName);
		if (playerProfilePanel != null) {
			getPanel().showWidget(getPanel().getWidgetIndex(playerProfilePanel));
		}
		
	}
	
	public DeckPanel getPanel() {
		return i_panel;
	}

	public SortedMap<String, PlayerProfilePanel> getPlayerProfilePanelsByPlayerName() {
		return i_playerProfilePanelsByPlayerName;
	}
}
