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

@Entity
@NamedQueries(value={
		@NamedQuery(name="game.getAll", query="select g from Game g"),
		@NamedQuery(name="game.getAllSince", query="select g from Game g where g.date >= :date")
		})
public class Game extends Identity<Game> {

	private Person i_Instigator;
	private SortedSet<Round> i_rounds;
	private Date i_date;
	
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
	public Date getDate() {
		return i_date;
	}
	public void setDate(Date date) {
		i_date = date;
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
	
	@Id @GeneratedValue
	public Long getId() {
		return super.getId();
	}

}
