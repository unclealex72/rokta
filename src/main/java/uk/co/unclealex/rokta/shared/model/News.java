package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.SortedSet;

public class News implements Serializable {

	private DatedGame i_lastGame;
	private League i_lastLeague;
	private SortedSet<DatedGame> i_todaysGames;
	private SortedSet<League> i_todaysLeague;
	private SortedSet<Streak> i_currentWinningStreaks;
	private SortedSet<Streak> i_currentLosingStreaks;
	
	protected News() {
		// Default constructor for serialisation
	}
	
	public News(
			DatedGame lastGame, League lastLeague,
			SortedSet<DatedGame> todaysGames, SortedSet<League> todaysLeagues,
			SortedSet<Streak> currentWinningStreaks, SortedSet<Streak> currentLosingStreaks) {
		super();
		i_lastGame = lastGame;
		i_lastLeague = lastLeague;
		i_todaysGames = todaysGames;
		i_todaysLeague = todaysLeagues;
		i_currentWinningStreaks = currentWinningStreaks;
		i_currentLosingStreaks = currentLosingStreaks;
	}

	public DatedGame getLastGame() {
		return i_lastGame;
	}

	public League getLastLeague() {
		return i_lastLeague;
	}

	public SortedSet<Streak> getCurrentWinningStreaks() {
		return i_currentWinningStreaks;
	}

	public SortedSet<Streak> getCurrentLosingStreaks() {
		return i_currentLosingStreaks;
	}

	public SortedSet<DatedGame> getTodaysGames() {
		return i_todaysGames;
	}

	public SortedSet<League> getTodaysLeague() {
		return i_todaysLeague;
	}

	
}
