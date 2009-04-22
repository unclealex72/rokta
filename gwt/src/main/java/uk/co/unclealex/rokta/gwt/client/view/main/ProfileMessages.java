package uk.co.unclealex.rokta.gwt.client.view.main;

import com.google.gwt.i18n.client.Messages;

public interface ProfileMessages extends Messages {

	@DefaultMessage("{0}''s hand distribution")
	public String playersHands(String playerName);

	@DefaultMessage("{0}''s opening hand distribution")
	public String playersOpeningHands(String playerName);

}
