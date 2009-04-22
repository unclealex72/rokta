package uk.co.unclealex.rokta.gwt.client.view.main;

import com.google.gwt.i18n.client.Messages;

public interface LeagueWidgetMessages extends Messages {

	@DefaultMessage("Player")
	public String playerTitle();
	
	@DefaultMessage("{0}")
	public String playerFormat(String playerName);
	
	@DefaultMessage("Games")
	public String gamesTitle();
	
	@DefaultMessage("{0,number}")
	public String gamesFormat(int games);
	
	@DefaultMessage("Rounds")	
	public String roundsTitle();
	@DefaultMessage("{0,number}")
	public String roundsFormat(int rounds);
	
	@DefaultMessage("Lost")
	public String lostTitle();
	@DefaultMessage("{0,number}")
	public String lostFormat(int lost);
	
	@DefaultMessage("R/WG")
	public String roundsPerWonGameTitle();
	@DefaultMessage("{0,number,0.00}")
	public String roundsPerWonGameFormat(double roundsPerWonGame);

	@DefaultMessage("R/LG")
	public String roundsPerLostGameTitle();
	@DefaultMessage("{0,number,0.00}")
	public String roundsPerLostGameFormat(double roundsPerWonGame);

	@DefaultMessage("L/G")
	public String lossesPerGameTitle();
	@DefaultMessage("{0,number,0.00}")	
	public String lossesPerGameFormat(double lossesPerGame);
	
	@DefaultMessage("Gap")
	public String gapTitle();
	@DefaultMessage("{0,number}")
	public String gapFormat(int gap);
}
