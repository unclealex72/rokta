package uk.co.unclealex.rokta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(value={
		@NamedQuery(name="person.getAll", query="select p from Person p"),
		@NamedQuery(name="person.findByName", query="select p from Person p where p.name=:name")
		})
public class Person extends Identity<Person> {

	private String i_name;

	@Override
	public int compareTo(Person o) {
		return getName().compareTo(o.getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Person && getName().equals(((Person) obj).getName());
	}
	
	@Column(unique=true, nullable=false)
	public String getName() {
		return i_name;
	}

	public void setName(String name) {
		i_name = name;
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
