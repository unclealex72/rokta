package uk.co.unclealex.rokta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Play extends Identity<Play> {

	private Person i_person;
	private Hand i_hand;

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	public Hand getHand() {
		return i_hand;
	}
	public void setHand(Hand hand) {
		i_hand = hand;
	}
	
	@ManyToOne
	@Column(nullable=false)
	public Person getPerson() {
		return i_person;
	}
	public void setPerson(Person person) {
		i_person = person;
	}
	
	@Id @GeneratedValue
	public Long getId() {
		return super.getId();
	}

	@Override
	public String toString() {
		return getPerson().getName() + ":" + getHand().getDescription();
	}
}
