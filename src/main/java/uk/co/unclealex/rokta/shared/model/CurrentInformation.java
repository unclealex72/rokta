package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;

public class CurrentInformation implements Serializable {

	private Leagues i_leagues;
	private Streaks i_streaks;
	private HeadToHeads i_headToHeads;
	
	protected CurrentInformation() {
		super();
		// Default constructor for serialization.
	}

	public CurrentInformation(Leagues leagues, Streaks streaks, HeadToHeads headToHeads) {
		super();
		i_leagues = leagues;
		i_streaks = streaks;
		i_headToHeads = headToHeads;
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
	
}
