package uk.co.unclealex.rokta.views;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Play;
import uk.co.unclealex.rokta.model.Round;

public class RoundView {

	private Person i_counter;
	private List<String> i_hands;
	
	public RoundView(Round round, SortedSet<Person> participants) {
		setCounter(round.getCounter());
		SortedMap<Person, Hand> plays = new TreeMap<Person, Hand>();
		for (Play play : round.getPlays()) {
			plays.put(play.getPerson(), play.getHand());
		}

		List<String> hands = new ArrayList<String>();
		for (Person participant : participants) {
			Hand hand = plays.get(participant);
			hands.add(hand==null?"":hand.getDescription());
		}
		setHands(hands);
	}

	public Person getCounter() {
		return i_counter;
	}

	protected void setCounter(Person counter) {
		i_counter = counter;
	}

	public List<String> getHands() {
		return i_hands;
	}

	protected void setHands(List<String> hands) {
		i_hands = hands;
	}

}
