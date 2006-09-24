package uk.co.unclealex.rokta.model;

import java.io.Serializable;

public class LeagueRow implements Serializable {

	private Person i_person;
	private int i_gamesPlayed;
	private int i_roundsPlayed;
	private int i_gamesLost;
	private int i_totalGamesPlayed;
	private boolean i_exempt = false;
	private boolean i_playingToday = true;
	private InfiniteInteger i_gap;
	private Delta i_delta;
	
	public LeagueRow() {
	}
	
	/**
	 * @param person
	 * @param gamesPlayed
	 * @param roundsPlayed
	 * @param gamesLost
	 * @param totalGamesPlayed
	 * @param exempt
	 * @param playingToday
	 * @param gap
	 * @param delta
	 */
	public LeagueRow(Person person, int gamesPlayed, int roundsPlayed, int gamesLost, int totalGamesPlayed, boolean exempt, boolean playingToday, InfiniteInteger gap, Delta delta) {
		super();
		i_person = person;
		i_gamesPlayed = gamesPlayed;
		i_roundsPlayed = roundsPlayed;
		i_gamesLost = gamesLost;
		i_totalGamesPlayed = totalGamesPlayed;
		i_exempt = exempt;
		i_playingToday = playingToday;
		i_gap = gap;
		i_delta = delta;
	}

	public LeagueRow copy() {
		return new LeagueRow(getPerson(), getGamesPlayed(), getRoundsPlayed(), getGamesLost(), getTotalGamesPlayed(), isExempt(), isPlayingToday(), getGap(), getDelta());
	}
	public double getLossesPerGame() {
		return getGamesLost() / (double) getGamesPlayed();
	}
	
	public double getRoundsPerGame() {
		return getRoundsPlayed() / (double) getGamesPlayed();
	}
	
	public int getGamesLost() {
		return i_gamesLost;
	}
	public void setGamesLost(int gamesLost) {
		i_gamesLost = gamesLost;
	}
	public int getGamesPlayed() {
		return i_gamesPlayed;
	}
	public void setGamesPlayed(int gamesPlayed) {
		i_gamesPlayed = gamesPlayed;
	}
	public Person getPerson() {
		return i_person;
	}
	public void setPerson(Person person) {
		i_person = person;
	}
	public int getTotalGamesPlayed() {
		return i_totalGamesPlayed;
	}
	public void setTotalGamesPlayed(int totalGamesPlayed) {
		i_totalGamesPlayed = totalGamesPlayed;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LeagueRow)) {
			return false;
		}
		LeagueRow other = (LeagueRow) obj;
		return getPerson().equals(other.getPerson()) &&
					getGamesLost() == other.getGamesLost() &&
					getRoundsPlayed() == other.getRoundsPlayed() &&
					getGamesPlayed() == other.getGamesPlayed() &&
					getTotalGamesPlayed() == other.getTotalGamesPlayed();
	}

	public int getRoundsPlayed() {
		return i_roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		i_roundsPlayed = roundsPlayed;
	}

	public Delta getDelta() {
		return i_delta;
	}

	public void setDelta(Delta delta) {
		i_delta = delta;
	}

	/**
	 * @return the exempt
	 */
	public boolean isExempt() {
		return i_exempt;
	}

	/**
	 * @param exempt the exempt to set
	 */
	public void setExempt(boolean exempt) {
		i_exempt = exempt;
	}

	/**
	 * @return the playingToday
	 */
	public boolean isPlayingToday() {
		return i_playingToday;
	}

	/**
	 * @param playingToday the playingToday to set
	 */
	public void setPlayingToday(boolean playingToday) {
		i_playingToday = playingToday;
	}

	/**
	 * @return the gap
	 */
	public InfiniteInteger getGap() {
		return i_gap;
	}

	/**
	 * @param gap the gap to set
	 */
	public void setGap(InfiniteInteger gap) {
		i_gap = gap;
	}
}
