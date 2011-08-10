package uk.co.unclealex.rokta.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

import uk.co.unclealex.hibernate.model.KeyedBean;
import uk.co.unclealex.rokta.shared.model.Hand;

@Entity
@XmlType(propOrder={"person", "hand"})
public class Play extends KeyedBean<Play> {

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
	@XmlIDREF
	public Person getPerson() {
		return i_person;
	}
	public void setPerson(Person person) {
		i_person = person;
	}
	
	@Override
	@Id @GeneratedValue
	public Integer getId() {
		return super.getId();
	}

	@Override
	public String toString() {
		return getPerson().getName() + ":" + getHand().getDescription();
	}
}
