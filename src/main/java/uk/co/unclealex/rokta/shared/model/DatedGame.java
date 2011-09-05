package uk.co.unclealex.rokta.shared.model;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

public class DatedGame extends Game implements Comparable<DatedGame> {

	private Date i_datePlayed;
	
	protected DatedGame() {
		// Default constructor for serialisation
	}

	public DatedGame(String instigator, SortedSet<String> players, List<Round> rounds, Date datePlayed) {
		super(instigator, players, rounds);
		i_datePlayed = datePlayed;
	}
	
	@Override
	public int hashCode() {
		return getDatePlayed().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof DatedGame) && compareTo((DatedGame) obj) == 0;
	}
	
	@Override
	public int compareTo(DatedGame o) {
		return getDatePlayed().compareTo(o.getDatePlayed());
	}

	public Date getDatePlayed() {
		return i_datePlayed;
	}
}
