package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;

import com.google.gwt.user.client.ui.VerticalPanel;

public class LeaguePanel extends RoktaAwareComposite<VerticalPanel> {

	private LeagueWidget i_leagueWidget;
	private LeagueChartWidget i_leagueChartWidget;

	public LeaguePanel(RoktaController roktaController, LeagueWidget leagueWidget, LeagueChartWidget leagueChartWidget) {
		super(roktaController);
		i_leagueWidget = leagueWidget;
		i_leagueChartWidget = leagueChartWidget;
	}

	@Override
	protected VerticalPanel create() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(getLeagueWidget());
		panel.add(getLeagueChartWidget());
		return panel;
	}
	
	@Override
	protected void postCreate(VerticalPanel widget) {
		setStylePrimaryName("leaguePanel");
	}
	
	@Override
	public void onVisibilityChange(boolean isVisible) {
		if (isVisible) {
			getRoktaController().requestLoadLeague();
		}
	}

	public LeagueWidget getLeagueWidget() {
		return i_leagueWidget;
	}

	public LeagueChartWidget getLeagueChartWidget() {
		return i_leagueChartWidget;
	}
}
