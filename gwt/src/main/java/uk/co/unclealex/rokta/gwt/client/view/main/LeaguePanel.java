package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;

import com.google.gwt.user.client.ui.VerticalPanel;

public class LeaguePanel extends RoktaAwareComposite {

	public LeaguePanel(RoktaController roktaController, LeagueWidget leagueWidget, LeagueChartWidget leagueChartWidget) {
		super(roktaController);
		VerticalPanel panel = new VerticalPanel();
		panel.add(leagueWidget);
		panel.add(leagueChartWidget);
		initWidget(panel);
		setStylePrimaryName("leaguePanel");
	}

	@Override
	public void onVisibilityChange(boolean isVisible) {
		if (isVisible) {
			getRoktaController().requestLoadLeague();
		}
	}
}
