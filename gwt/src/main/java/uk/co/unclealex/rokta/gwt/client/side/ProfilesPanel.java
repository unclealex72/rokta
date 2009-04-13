package uk.co.unclealex.rokta.gwt.client.side;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;

import uk.co.unclealex.rokta.gwt.client.PlayerListener;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;

public class ProfilesPanel extends LinksPanel implements PlayerListener {

	public ProfilesPanel(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		roktaAdaptor.addPlayerListener(this);
		NavigationMessages messages = GWT.create(NavigationMessages.class);
		initialise(messages.profiles(), new LinkedHashMap<String, Command>());
	}

	public void playerAdded(final String playerName) {
		Command command = new Command() {
			public void execute() {
				getRoktaAdaptor().showProfile(playerName);
			}
		};
		addLink(playerName, command);
	}
	
}
