package uk.co.unclealex.rokta.model;

import java.io.Serializable;

public class LeagueRow implements Serializable {

	private Person i_person;
	private int i_totalParticipants;
	private int i_roundsPlayedInWonGames;
	private int i_roundsPlayedInLostGames;
	private int i_gamesLost;
	private int i_gamesWon;
	private boolean i_exempt = false;
	private boolean i_playingToday = true;
	private InfiniteInteger i_gap;
	private Delta i_delta;
	private League i_league;
	
	public LeagueRow() {
	}
	
	/**
	 * @param person
	 * @param gamesPlayed
	 * @param roundsPlayedInWonGames
	 * @param gamesLost
	 * @param totalGamesPlayed
	 * @param exempt
	 * @param playingToday
	 * @param gap
	 * @param delta
	 */
	public LeagueRow(
			Person person, int gamesLost, int gamesWon, int roundsPlayedInWonGames, int roundsPlayedInLostGames,
			int totalParticipants, boolean exempt, boolean playingToday, InfiniteInteger gap, Delta delta, League league) {
		super();
		i_person = person;
		i_gamesLost = gamesLost;
		i_gamesWon = gamesWon;
		i_roundsPlayedInWonGames = roundsPlayedInWonGames;
		i_roundsPlayedInLostGames = roundsPlayedInLostGames;
		i_totalParticipants = totalParticipants;
		i_exempt = exempt;
		i_playingToday = playingToday;
		i_gap = gap;
		i_delta = delta;
		i_league = league;
	}

	public LeagueRow copy() {
		return
			new LeagueRow(
					getPerson(), getGamesLost(), getGamesWon(), getRoundsPlayedInWonGames(), getRoundsPlayedInLostGames(),
					getTotalParticipants(), isExempt(), isPlayingToday(), getGap(), getDelta(), getLeague());
	}
	
	public Double getWeightedLossesPerGame() {
		League league = getLeague();
		if (league == null) {
			return null;
		}
		return getLossesPerGame()  * league.getExpectedLossesPerGame() / getExpectedLossesPerGame();
	}
	
	public double getExpectedLossesPerGame() {
		return getGamesPlayed() / (double) getTotalParticipants();
	}
	
	public double getLossesPerGame() {
		return getGamesLost() / (double) getGamesPlayed();
	}

	public double getRoundsPerWonGames() {
		return getRoundsPlayedInWonGames() / (double) (getGamesWon());
	}
	
	public double getRoundsPerLostGames() {
		return getRoundsPlayedInLostGames() / (double) getGamesLost();
	}

	public int getGamesLost() {
		return i_gamesLost;
	}
	public void setGamesLost(int gamesLost) {
		i_gamesLost = gamesLost;
	}
	public int getGamesPlayed() {
		return getGamesWon() + getGamesLost();
	}

	public Person getPerson() {
		return i_person;
	}
	public void setPerson(Person person) {
		i_person = person;
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
					getGamesWon() == other.getGamesWon();
	}

	public int getRoundsPlayed() {
		return i_roundsPlayedInLostGames + i_roundsPlayedInWonGames;
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

	/**
	 * @return the roundsPlayedinLostGames
	 */
	public int getRoundsPlayedInLostGames() {
		return i_roundsPlayedInLostGames;
	}

	/**
	 * @return the roundsPlayedinWonGames
	 */
	public int getRoundsPlayedInWonGames() {
		return i_roundsPlayedInWonGames;
	}

	/**
	 * @param roundsPlayedInLostGames the roundsPlayedInLostGames to set
	 */
	public void setRoundsPlayedInLostGames(int roundsPlayedInLostGames) {
		i_roundsPlayedInLostGames = roundsPlayedInLostGames;
	}

	/**
	 * @param roundsPlayedInWonGames the roundsPlayedInWonGames to set
	 */
	public void setRoundsPlayedInWonGames(int roundsPlayedInWonGames) {
		i_roundsPlayedInWonGames = roundsPlayedInWonGames;
	}

	/**
	 * @return the gamesWon
	 */
	public int getGamesWon() {
		return i_gamesWon;
	}

	/**
	 * @param gamesWon the gamesWon to set
	 */
	public void setGamesWon(int gamesWon) {
		i_gamesWon = gamesWon;
	}

	public League getLeague() {
		return i_league;
	}

	public void setLeague(League league) {
		i_league = league;
	}

	public int getTotalParticipants() {
		return i_totalParticipants;
	}

	public void setTotalParticipants(int totalParticipants) {
		i_totalParticipants = totalParticipants;
	}
}
