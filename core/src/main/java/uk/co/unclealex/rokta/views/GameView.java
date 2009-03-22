package uk.co.unclealex.rokta.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;

public class GameView {

	private Date i_datePlayed;
	private List<RoundView> i_rounds;
	private SortedSet<Person> i_participants;
	private Person i_instigator;
	private Person i_loser;
	
	public GameView(Game game) {
		setDatePlayed(game.getDatePlayed());
		setParticipants(game.getParticipants());
		setInstigator(game.getInstigator());
		setLoser(game.getLoser());
		
		List<RoundView> rounds = new ArrayList<RoundView>();
		for (Round round : game.getRounds()) {
			rounds.add(new RoundView(round, getParticipants()));
		}
		setRounds(rounds);
	}
	
	public Date getDatePlayed() {
		return i_datePlayed;
	}
	
	public List<RoundView> getRounds() {
		return i_rounds;
	}

	public SortedSet<Person> getParticipants() {
		return i_participants;
	}

	protected void setDatePlayed(Date datePlayed) {
		i_datePlayed = datePlayed;
	}

	protected void setParticipants(SortedSet<Person> participants) {
		i_participants = participants;
	}

	protected void setRounds(List<RoundView> rounds) {
		i_rounds = rounds;
	}

	public Person getInstigator() {
		return i_instigator;
	}

	protected void setInstigator(Person instigator) {
		i_instigator = instigator;
	}

	/**
	 * @return the loser
	 */
	public Person getLoser() {
		return i_loser;
	}

	/**
	 * @param loser the loser to set
	 */
	public void setLoser(Person loser) {
		i_loser = loser;
	}
	
}
