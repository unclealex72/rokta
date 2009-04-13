package uk.co.unclealex.rokta.gwt.client.main;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.side.NavigationMessages;
import uk.co.unclealex.rokta.gwt.client.side.StatisticsMessages;

import com.google.gwt.user.client.ui.VerticalPanel;

public class LeaguePanel extends MainPanelComposite {

	protected LeaguePanel(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		VerticalPanel panel = new VerticalPanel();
		panel.add(new LeagueWidget(roktaAdaptor));
		panel.add(new LeagueChartWidget(roktaAdaptor));
		initWidget(panel);
	}

	@Override
	protected String createTitle(NavigationMessages navigationMessages, StatisticsMessages statisticsMessages) {
		return navigationMessages.games();
	}
}
