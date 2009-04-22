package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;

import com.google.gwt.user.client.ui.VerticalPanel;

public class PlayerProfilePanel extends RoktaAwareComposite {

	private String i_playerName;
	
	public PlayerProfilePanel(
			RoktaController roktaController,
			String playerName,
			HandDistributionChartWidget allHandDistributionChartWidget, 
			HandDistributionChartWidget openingHandDistributionChartWidget) {
		super(roktaController);
		i_playerName = playerName;
		VerticalPanel panel = new VerticalPanel();
		panel.add(allHandDistributionChartWidget);
		panel.add(openingHandDistributionChartWidget);
		initWidget(panel);
	}
	
	@Override
	public void onVisibilityChange(boolean isVisible) {
		if (isVisible) {
			getRoktaController().requestLoadPlayerProfile(getPlayerName());
		}
	}

	public String getPlayerName() {
		return i_playerName;
	}
}
