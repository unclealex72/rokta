package uk.co.unclealex.rokta.model;

import java.util.Date;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity()
@NamedQueries(value={
		@NamedQuery(name="game.getAll", query="from Game"),
		@NamedQuery(name="game.fetchAll", query="from Game fetch all properties"),
		@NamedQuery(name="game.getAllSince", query="from Game g where g.datePlayed >= :datePlayed"),
		@NamedQuery(name="game.getLast", query="from Game g order by datePlayed desc"),
		@NamedQuery(
				name="game.getLastForPerson",
				query="select game from Game game join game.rounds round join round.plays play with play.person = :person order by datePlayed desc")
		})
public class Game extends Identity<Game> {

	private Person i_Instigator;
	private SortedSet<Round> i_rounds;
	private Date i_datePlayed;
	
	@Transient
	public SortedSet<Person> getParticipants() {
		if (getRounds() == null || getRounds().isEmpty()) {
			return new TreeSet<Person>();
		}
		Round firstRound = getRounds().first();
		return firstRound.getParticipants();
	}
	
	@Transient
	public Person getLoser() {
		Person loser = null;
		
		if (getRounds() != null && !getRounds().isEmpty()) {
			Round finalRound = getRounds().last();
			Set<Person> losers = finalRound.getLosers();
			if (losers.size() == 1) {
				loser = losers.iterator().next();
			}
		}
		return loser;
		
	}
	
	@Column(nullable=false)
	public Date getDatePlayed() {
		return i_datePlayed;
	}
	public void setDatePlayed(Date date) {
		i_datePlayed = date;
	}
	
	@ManyToOne
	@Column(nullable=false)
	public Person getInstigator() {
		return i_Instigator;
	}
	
	public void setInstigator(Person instigator) {
		i_Instigator = instigator;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="game_id")
	@Sort(type = SortType.NATURAL)
	public SortedSet<Round> getRounds() {
		return i_rounds;
	}
	
	public void setRounds(SortedSet<Round> rounds) {
		i_rounds = rounds;
	}
	
	@Override
	@Id @GeneratedValue
	public Long getId() {
		return super.getId();
	}

	@Override
	public int compareTo(Game o) {
		int cmp = getDatePlayed().compareTo(o.getDatePlayed());
		return cmp!=0?cmp:super.compareTo(o);
	}
}
