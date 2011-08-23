package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.SortedSet;

public class Streaks implements Serializable {

	private SortedSet<Streak> i_winningStreaks;
	private SortedSet<Streak> i_losingStreaks;
	private SortedSet<Streak> i_currentWinningStreaks;
	private SortedSet<Streak> i_currentLosingStreaks;
	
	protected Streaks() {
		super();
		// Default constructor for serialisation
	}

	public Streaks(SortedSet<Streak> winningStreaks, SortedSet<Streak> losingStreaks,
			SortedSet<Streak> currentWinningStreaks, SortedSet<Streak> currentLosingStreaks) {
		super();
		i_winningStreaks = winningStreaks;
		i_losingStreaks = losingStreaks;
		i_currentWinningStreaks = currentWinningStreaks;
		i_currentLosingStreaks = currentLosingStreaks;
	}

	public SortedSet<Streak> getWinningStreaks() {
		return i_winningStreaks;
	}

	public SortedSet<Streak> getLosingStreaks() {
		return i_losingStreaks;
	}

	public SortedSet<Streak> getCurrentWinningStreaks() {
		return i_currentWinningStreaks;
	}

	public SortedSet<Streak> getCurrentLosingStreaks() {
		return i_currentLosingStreaks;
	}
}
