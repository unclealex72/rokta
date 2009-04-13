package uk.co.unclealex.rokta.gwt.client.side;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;


public class StatisticsPanel extends LinksPanel {

	public StatisticsPanel(final RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		StatisticsMessages statisticsMessages = GWT.create(StatisticsMessages.class);
		NavigationMessages navigationMessages = GWT.create(NavigationMessages.class);
		LinkedHashMap<String, Command> commandsByLink = new LinkedHashMap<String, Command>();
		commandsByLink.put(
			statisticsMessages.winningStreaks(),
				new Command() {
					public void execute() {
						roktaAdaptor.showWinningStreaks();
					}
				});
		commandsByLink.put(
			statisticsMessages.losingStreaks(),
				new Command() {
					public void execute() {
						roktaAdaptor.showLosingStreaks();
					}
				});
		commandsByLink.put(
			statisticsMessages.headToHeads(),
				new Command() {
					public void execute() {
						roktaAdaptor.showHeadToHeads();
					}
				});
		initialise(navigationMessages.statistics(), commandsByLink);
	}
}
