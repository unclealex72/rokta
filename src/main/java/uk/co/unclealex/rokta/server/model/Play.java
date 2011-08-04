package uk.co.unclealex.rokta.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

import uk.co.unclealex.hibernate.model.KeyedBean;
import uk.co.unclealex.rokta.pub.views.Hand;

@Entity
@NamedQueries({
	@NamedQuery(
			name="play.countByPersonAndHand",
			query="select count(p.id) from Play p where p.person = :person and p.hand = :hand"),
	@NamedQuery(
			name="play.countByPersonAndHandAfter",
			query=
				"select count(p.id) " +
				"from Game g join g.rounds r join r.plays p " +
				"where p.person = :person and p.hand = :hand and g.datePlayed >= :after"),
	@NamedQuery(
			name="play.countByPersonAndHandBefore",
			query=
				"select count(p.id) " +
				"from Game g join g.rounds r join r.plays p " +
				"where p.person = :person and p.hand = :hand and g.datePlayed <= :before"),
	@NamedQuery(
			name="play.countByPersonAndHandBetween",
			query=
				"select count(p.id) " +
				"from Game g join g.rounds r join r.plays p " +
				"where p.person = :person and p.hand = :hand and g.datePlayed <= :before and g.datePlayed >= :after")
})
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
