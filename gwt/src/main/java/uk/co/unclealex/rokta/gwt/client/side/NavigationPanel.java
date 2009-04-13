package uk.co.unclealex.rokta.gwt.client.side;

import java.util.LinkedHashMap;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Panel;

public class NavigationPanel extends LinksPanel {

	public NavigationPanel(String id, final RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		NavigationMessages messages = GWT.create(NavigationMessages.class);
		LinkedHashMap<String, Command> commandsByLink = new LinkedHashMap<String, Command>();
		commandsByLink.put(
			messages.newGame(),
			new Command() {
				public void execute() {
					roktaAdaptor.startNewGame();
				}
			});
		commandsByLink.put(
			messages.league(),
			new Command() {
				public void execute() {
					roktaAdaptor.showLeague();
				}
			});
		commandsByLink.put(
			messages.filters(),
			new Command() {
				public void execute() {
					roktaAdaptor.showFilters();
				}
			});
		commandsByLink.put(
			messages.statistics(),
			new Command() {
				public void execute() {
					roktaAdaptor.showStatistics();
				}
			});
		commandsByLink.put(
			messages.profiles(),
			new Command() {
				public void execute() {
					roktaAdaptor.showProfiles();
				}
			});
		initialise(id, messages.header(), commandsByLink);
	}

	protected void addItem(Panel panel, String text, Command command) {
		Hyperlink hyperlink = new Hyperlink();
		hyperlink.setText(text);
		getCommandsByWidget().put(hyperlink, command);
		hyperlink.addClickHandler(this);
		panel.add(hyperlink);
	}
	
}
