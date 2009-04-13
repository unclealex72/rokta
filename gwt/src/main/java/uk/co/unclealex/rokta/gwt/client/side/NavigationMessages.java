package uk.co.unclealex.rokta.gwt.client.side;

import com.google.gwt.i18n.client.Messages;

public interface NavigationMessages extends Messages {

	@DefaultMessage("Navigation")
	public String header();
	
	@DefaultMessage("New game")
	public String newGame();

	@DefaultMessage("League")
	public String league();
	
	@DefaultMessage("Filters")
	public String filters();
	
	@DefaultMessage("Statistics")
	public String statistics();
	
	@DefaultMessage("Profiles")
	public String profiles();

	@DefaultMessage("{0}''s profile")
	public String playersProfile(String playerName);

	@DefaultMessage("Games")
	public String games();
}
