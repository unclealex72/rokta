package uk.co.unclealex.rokta.gwt.client.view.side;

import java.util.LinkedHashMap;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;

public class NavigationPanel extends LinksPanel<NavigationMessages> {

	public NavigationPanel(RoktaController roktaController) {
		super(roktaController, (NavigationMessages) GWT.create(NavigationMessages.class));
	}

	@Override
	public String createHeader(NavigationMessages messages) {
		return messages.header();
	}
	
	@Override
	public LinkedHashMap<String, Command> createCommandsByLink(NavigationMessages messages) {
		LinkedHashMap<String, Command> commandsByLink = new LinkedHashMap<String, Command>();
		final RoktaController roktaController = getRoktaController();
		commandsByLink.put(
			messages.newGame(),
			new Command() {
				public void execute() {
					roktaController.startNewGame();
				}
			});
		commandsByLink.put(
			messages.league(),
			new Command() {
				public void execute() {
					roktaController.showLeague();
				}
			});
		commandsByLink.put(
			messages.filters(),
			new Command() {
				public void execute() {
					roktaController.showFilters();
				}
			});
		commandsByLink.put(
			messages.statistics(),
			new Command() {
				public void execute() {
					roktaController.showStatistics();
				}
			});
		commandsByLink.put(
			messages.profiles(),
			new Command() {
				public void execute() {
					roktaController.showProfiles();
				}
			});
		return commandsByLink;
	}
	
}
