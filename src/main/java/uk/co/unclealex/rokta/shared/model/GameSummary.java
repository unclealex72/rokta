package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Date;

public class GameSummary implements Serializable {

	private String i_loser;
	private Date i_datePlayed;
	
	protected GameSummary() {
		// Default constructor for serialisation
	}
	
	public GameSummary(String loser, Date datePlayed) {
		super();
		i_loser = loser;
		i_datePlayed = datePlayed;
	}

	public String getLoser() {
		return i_loser;
	}

	public void setLoser(String loser) {
		i_loser = loser;
	}

	public Date getDatePlayed() {
		return i_datePlayed;
	}

	public void setDatePlayed(Date datePlayed) {
		i_datePlayed = datePlayed;
	}
	
	
}
