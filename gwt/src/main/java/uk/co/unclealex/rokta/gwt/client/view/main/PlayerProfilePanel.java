package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;

import com.google.gwt.user.client.ui.VerticalPanel;

public class PlayerProfilePanel extends RoktaAwareComposite<VerticalPanel> {

	private String i_playerName;
	private HandDistributionChartWidget i_allHandDistributionChartWidget;
	private HandDistributionChartWidget i_openingHandDistributionChartWidget;
	
	public PlayerProfilePanel(
			RoktaController roktaController,
			String playerName,
			HandDistributionChartWidget allHandDistributionChartWidget, 
			HandDistributionChartWidget openingHandDistributionChartWidget) {
		super(roktaController);
		i_playerName = playerName;
		i_allHandDistributionChartWidget = allHandDistributionChartWidget;
		i_openingHandDistributionChartWidget = openingHandDistributionChartWidget;
	}
	
	@Override
	protected VerticalPanel create() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(getAllHandDistributionChartWidget());
		panel.add(getOpeningHandDistributionChartWidget());
		return panel;
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

	public HandDistributionChartWidget getAllHandDistributionChartWidget() {
		return i_allHandDistributionChartWidget;
	}

	public HandDistributionChartWidget getOpeningHandDistributionChartWidget() {
		return i_openingHandDistributionChartWidget;
	}
}
