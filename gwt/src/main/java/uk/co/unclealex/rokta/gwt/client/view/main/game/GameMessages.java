package uk.co.unclealex.rokta.gwt.client.view.main.game;

import com.google.gwt.i18n.client.Messages;

public interface GameMessages extends Messages {

	@DefaultMessage("{0} is exempt.")
	public String exempt(String exemptPlayer);

	@DefaultMessage("No-one is exempt.")
	public String nooneExempt();

	@DefaultMessage("{0} has lost.")
	public String loser(String loser);

	@DefaultMessage("Start game")
	public String startGame();

	@DefaultMessage("Back")
	public String back();

	@DefaultMessage("Next")
	public String next();

}
