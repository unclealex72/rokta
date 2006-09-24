package uk.co.unclealex.rokta.model;

import java.util.HashSet;
import java.util.Iterator;
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

import org.apache.commons.collections15.Predicate;

@Entity
@NamedQueries({
	@NamedQuery(
			name="round.countByPerson",
			query="select count(*) from Round as round join round.plays as plays where plays.person = :person"),
			@NamedQuery(
					name="round.countOpeningGambitsByPersonAndHand",
					query="select count(*) from Round as r join r.plays as p where p.person = :person and p.hand = :hand and r.round = 1")
})
public class Round extends Identity<Round> {

	private Set<Play> i_plays;
	private Person i_counter;
	private Integer i_round;
	
	@Transient
	public SortedSet<Person> getLosers() {
		
		SortedSet<Person> losers = new TreeSet<Person>();
		/*
		 * Get a list of played hands and check that there are exactly two different plays.
		 * If so, return all the players with the losing hand. If not, everyone loses.
		 */ 
		Set<Hand> playedHands = new HashSet<Hand>();
		for (Play play : getPlays()) {
			playedHands.add(play.getHand());
		}

		Predicate<Play> loserPredicate;
		if (playedHands.size() == 2) {
			Iterator<Hand> playedHandIter = playedHands.iterator();
			Hand firstHand = playedHandIter.next();
			Hand secondHand = playedHandIter.next();
			final Hand losingHand = firstHand.beats(secondHand)?secondHand:firstHand;
			loserPredicate = new Predicate<Play>() {
				public boolean evaluate(Play play) {
					return play.getHand().equals(losingHand);
				}
			};
		}
		else {
			loserPredicate = new Predicate<Play>() {
				public boolean evaluate(Play play) {
					return true;
				}
			};
		}
		
		for (Play play : getPlays()) {
			if (loserPredicate.evaluate(play)) {
				losers.add(play.getPerson());
			}
		}
		
		return losers;
	}
	
	@Transient
	public SortedSet<Person> getParticipants() {
		SortedSet<Person> participants = new TreeSet<Person>();
		for (Play play : getPlays()) {
			participants.add(play.getPerson());
		}
		return participants;
	}
	
	@ManyToOne
	@Column(nullable=false)
	public Person getCounter() {
		return i_counter;
	}
	public void setCounter(Person counter) {
		i_counter = counter;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="round_id")
	@Column(nullable=false)
	public Set<Play> getPlays() {
		return i_plays;
	}
	
	public void setPlays(Set<Play> plays) {
		i_plays = plays;
	}
	

	@Column(nullable=false)
	public Integer getRound() {
		return i_round;
	}
	
	public void setRound(Integer round) {
		i_round = round;
	}

	@Override
	public int compareTo(Round o) {
		int cmpRound = getRound().compareTo(o.getRound());
		return cmpRound==0?super.compareTo(o):cmpRound;
	}	
	
	@Override
	@Id @GeneratedValue
	public Long getId() {
		return super.getId();
	}

}
