package uk.co.unclealex.rokta.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import uk.co.unclealex.hibernate.model.KeyedBean;
import uk.co.unclealex.rokta.shared.model.Colour;

@Entity
@NamedQueries(value={
		@NamedQuery(name="person.getAll", query="select p from Person p"),
		@NamedQuery(name="person.getPlayers", query="select distinct p.person from Play p"),
		@NamedQuery(name="person.findByName", query="select p from Person p where p.name=:name"),
    @NamedQuery(name="person.findByEmail", query="select p from Person p where p.email=:email")
		})
@XmlRootElement(name="colour")
@XmlType(propOrder={"name", "email", "graphingColour"})
public class Person extends KeyedBean<Person> {

	private String i_name;
  private String i_email;
	private Colour i_graphingColour;
	
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
	
	@Column
	public String getEmail() {
		return i_email;
	}

	public void setEmail(String email) {
		i_email = email;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	public Colour getGraphingColour() {
		return i_graphingColour;
	}
	
	public void setGraphingColour(Colour graphingColour) {
		i_graphingColour = graphingColour;
	}

	@Override
	@Id @GeneratedValue
	public Integer getId() {
		return super.getId();
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
