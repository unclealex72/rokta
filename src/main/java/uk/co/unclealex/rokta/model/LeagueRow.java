package uk.co.unclealex.rokta.model;

public class LeagueRow {

	private Person i_person;
	private int i_gamesPlayed;
	private int i_roundsPlayed;
	private int i_gamesLost;
	private int i_totalGamesPlayed;
	
	public double getLostToPlayedRatio() {
		return getGamesLost() / (double) getGamesPlayed();
	}
	
	public double getLostToTotalRatio() {
		return getGamesLost() / (double) getTotalGamesPlayed();
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
}
