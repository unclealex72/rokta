package uk.co.unclealex.rokta.gwt.client.main;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.side.NavigationMessages;
import uk.co.unclealex.rokta.gwt.client.side.StatisticsMessages;

import com.google.gwt.user.client.ui.VerticalPanel;


public class PlayerProfilePanel extends MainPanelComposite {

	private String i_playerName;
	private VerticalPanel i_verticalPanel;
	
	public PlayerProfilePanel(RoktaAdaptor roktaAdaptor, String playerName) {
		super(roktaAdaptor);
		i_playerName = playerName;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new HandsChartWidget(roktaAdaptor, playerName));
		verticalPanel.add(new OpeningHandsChartWidget(roktaAdaptor, playerName));
		setVerticalPanel(verticalPanel);
		initWidget(verticalPanel);
	}

	@Override
	protected String createTitle(NavigationMessages navigationMessages, StatisticsMessages statisticsMessages) {
		return navigationMessages.playersProfile(getPlayerName());
	}
	
	public String getPlayerName() {
		return i_playerName;
	}

	public VerticalPanel getVerticalPanel() {
		return i_verticalPanel;
	}

	public void setVerticalPanel(VerticalPanel verticalPanel) {
		i_verticalPanel = verticalPanel;
	}
}
