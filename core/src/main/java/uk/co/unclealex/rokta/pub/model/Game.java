package uk.co.unclealex.rokta.pub.model;

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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import uk.co.unclealex.hibernate.model.KeyedBean;

@Entity()
@NamedQueries(value={
		@NamedQuery(name="game.getAll", query="from Game"),
		@NamedQuery(name="game.getAllSince", query="from Game g where g.datePlayed >= :datePlayed"),
		@NamedQuery(name="game.getLast", query="from Game g order by datePlayed desc"),
		@NamedQuery(
				name="game.getLastForPerson",
				query="select game from Game game join game.rounds round join round.plays play where play.person = :person order by datePlayed desc")
		})
@XmlRootElement(name="game")
@XmlType(propOrder={"datePlayed", "instigator", "rounds"})
public class Game extends KeyedBean<Game> {

	private Person i_instigator;
	private SortedSet<Round> i_rounds;
	private DateTime i_datePlayed;
	
	@Transient
	@XmlTransient
	public SortedSet<Person> getParticipants() {
		if (getRounds() == null || getRounds().isEmpty()) {
			return new TreeSet<Person>();
		}
		Round firstRound = getRounds().first();
		return firstRound.getParticipants();
	}
	
	@Transient
	@XmlTransient
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
	@XmlElement(name="date-played")
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getDatePlayed() {
		return i_datePlayed;
	}
	
	public void setDatePlayed(DateTime datePlayed) {
		i_datePlayed = datePlayed;
	}
	
	@ManyToOne
	@XmlIDREF
	public Person getInstigator() {
		return i_instigator;
	}
	
	public void setInstigator(Person instigator) {
		i_instigator = instigator;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="game_id")
	@Sort(type = SortType.NATURAL)
	@XmlElementWrapper(name="rounds")
	@XmlElement(name="round")
	public SortedSet<Round> getRounds() {
		return i_rounds;
	}
	
	public void setRounds(SortedSet<Round> rounds) {
		i_rounds = rounds;
	}
	
	@Override
	@Id @GeneratedValue
	public Integer getId() {
		return super.getId();
	}

	@Override
	public int compareTo(Game o) {
		int cmp = getDatePlayed().compareTo(o.getDatePlayed());
		return cmp!=0?cmp:super.compareTo(o);
	}
	
	@Override
	public String toString() {
		return "[Game " + getId() + " @ " + getDatePlayed() + "]";
	}
}
