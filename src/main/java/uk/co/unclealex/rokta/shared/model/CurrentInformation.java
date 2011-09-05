package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Map;
import java.util.SortedSet;

public class CurrentInformation implements Serializable {

	private Leagues i_leagues;
	private SortedSet<Streak> i_winningStreaks;
	private SortedSet<Streak> i_losingStreaks;
	private HeadToHeads i_headToHeads;
	private Map<String, PlayerProfile> i_playerProfilesByName;
	private News i_news;
	
	protected CurrentInformation() {
		super();
		// Default constructor for serialization.
	}

	public CurrentInformation(
			Leagues leagues, SortedSet<Streak> winningStreaks, SortedSet<Streak> losingStreaks, 
			HeadToHeads headToHeads, Map<String, PlayerProfile> playerProfilesByName, News news) {
		super();
		i_leagues = leagues;
		i_winningStreaks = winningStreaks;
		i_losingStreaks = losingStreaks;
		i_headToHeads = headToHeads;
		i_playerProfilesByName = playerProfilesByName;
		i_news = news;
	}

	public News getNews() {
		return i_news;
	}
	
	public Leagues getLeagues() {
		return i_leagues;
	}

	public HeadToHeads getHeadToHeads() {
		return i_headToHeads;
	}

	public Map<String, PlayerProfile> getPlayerProfilesByName() {
		return i_playerProfilesByName;
	}

	public SortedSet<Streak> getWinningStreaks() {
		return i_winningStreaks;
	}

	public SortedSet<Streak> getLosingStreaks() {
		return i_losingStreaks;
	}
	
}
