package uk.co.unclealex.rokta.gwt.client.view.side;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;

public class ProfilesPanel extends LinksPanel<SortedSet<String>> {

	public ProfilesPanel(RoktaController roktaController, Collection<String> playerNames) {
		super(roktaController, new TreeSet<String>(playerNames));
	}

	@Override
	public String createHeader(SortedSet<String> playerNames) {
		NavigationMessages messages = GWT.create(NavigationMessages.class);
		return messages.profiles();
	}
	
	@Override
	public LinkedHashMap<String, Command> createCommandsByLink(SortedSet<String> playerNames) {
		LinkedHashMap<String, Command> commandsByLink = new LinkedHashMap<String, Command>();
		for (final String playerName : playerNames) {
			Command command = new Command() {
				public void execute() {
					getRoktaController().showPlayerProfile(playerName);
				}	
			};
			commandsByLink.put(playerName, command);
		}
		return commandsByLink;
	}
}
