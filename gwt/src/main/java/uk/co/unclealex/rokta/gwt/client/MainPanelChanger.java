package uk.co.unclealex.rokta.gwt.client;

public interface MainPanelChanger {

	public String showLeague();

	public String showWinningStreaks();

	public String showLosingStreaks();
	
	public String showHeadToHeads();

	public String showProfile(String personName);
	
	public void startNewGame();
}
