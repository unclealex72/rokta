package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Map;

public class CurrentInformation implements Serializable {

	private Leagues i_leagues;
	private Streaks i_streaks;
	private HeadToHeads i_headToHeads;
	private Map<String, PlayerProfile> i_playerProfilesByName;
	
	protected CurrentInformation() {
		super();
		// Default constructor for serialization.
	}

	public CurrentInformation(
			Leagues leagues, Streaks streaks, HeadToHeads headToHeads, Map<String, PlayerProfile> playerProfilesByName) {
		super();
		i_leagues = leagues;
		i_streaks = streaks;
		i_headToHeads = headToHeads;
		i_playerProfilesByName = playerProfilesByName;
	}

	public Leagues getLeagues() {
		return i_leagues;
	}

	public Streaks getStreaks() {
		return i_streaks;
	}

	public HeadToHeads getHeadToHeads() {
		return i_headToHeads;
	}

	public Map<String, PlayerProfile> getPlayerProfilesByName() {
		return i_playerProfilesByName;
	}
	
}
