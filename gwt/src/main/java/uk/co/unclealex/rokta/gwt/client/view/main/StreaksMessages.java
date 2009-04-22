package uk.co.unclealex.rokta.gwt.client.view.main;

import java.util.Date;

import com.google.gwt.i18n.client.Messages;

public interface StreaksMessages extends Messages {

	@DefaultMessage("Rank")
	public String rankTitle();
	@DefaultMessage("{0,number}.")
	public String rankFormat(int rank);
	
	@DefaultMessage("Player")
	public String playerTitle();
	@DefaultMessage("{0}")
	public String playerFormat(String personName);
	
	@DefaultMessage("Length")
	public String lengthTitle();
	@DefaultMessage("{0,number}")
	public String lengthFormat(int length);
	
	@DefaultMessage("Started")
	public String fromTitle();
	@DefaultMessage("{0,date,dd/MM/yyyy HH:mm}")
	public String fromFormat(Date fromDate);
	
	@DefaultMessage("Ended")
	public String toTitle();
	@DefaultMessage("{0,date,dd/MM/yyyy HH:mm}")
	public String toFormat(Date toDate);

	@DefaultMessage("Current winning streaks")
	public String currentWinningStreaks();
	@DefaultMessage("Current losing streaks")
	public String currentLosingStreaks();
}
