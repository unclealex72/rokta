package uk.co.unclealex.rokta.gwt.client.view.side;

import java.util.LinkedHashMap;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;


public class StatisticsPanel extends LinksPanel<Object> {

	public StatisticsPanel(RoktaController roktaController) {
		super(roktaController, null);
	}

	@Override
	public String createHeader(Object initialisationObject) {
		NavigationMessages navigationMessages = GWT.create(NavigationMessages.class);
		return navigationMessages.statistics();
	}
	
	@Override
	public LinkedHashMap<String, Command> createCommandsByLink(Object initialisationObject) {
		StatisticsMessages statisticsMessages = GWT.create(StatisticsMessages.class);
		LinkedHashMap<String, Command> commandsByLink = new LinkedHashMap<String, Command>();
		final RoktaController roktaController = getRoktaController();
		commandsByLink.put(
			statisticsMessages.winningStreaks(),
				new Command() {
					public void execute() {
						roktaController.showWinningStreaks();
					}
				});
		commandsByLink.put(
			statisticsMessages.losingStreaks(),
				new Command() {
					public void execute() {
						roktaController.showLosingStreaks();
					}
				});
		commandsByLink.put(
			statisticsMessages.headToHeads(),
				new Command() {
					public void execute() {
						roktaController.showHeadToHeads();
					}
				});
		return commandsByLink;
	}
}
