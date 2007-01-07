package uk.co.unclealex.rokta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@NamedQueries(value={
		@NamedQuery(name="person.getAll", query="select p from Person p"),
		@NamedQuery(name="person.getPlayers", query="select distinct p.person from Play p"),
		@NamedQuery(name="person.findByName", query="select p from Person p where p.name=:name")
		})
@XmlRootElement(name="colour")
@XmlType(propOrder={"name", "colour"})
public class Person extends Identity<Person> {

	private String i_name;
	private Colour i_colour;
	@Override
	public int compareTo(Person o) {
		return getName().compareTo(o.getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Person && getName().equals(((Person) obj).getName());
	}
	
	@Column(unique=true, nullable=false)
	@XmlID
	public String getName() {
		return i_name;
	}

	public void setName(String name) {
		i_name = name;
	}
	
	@ManyToOne
	@XmlIDREF
	public Colour getColour() {
		return i_colour;
	}
	public void setColour(Colour colour) {
		i_colour = colour;
	}

	@Override
	@Id @GeneratedValue
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
