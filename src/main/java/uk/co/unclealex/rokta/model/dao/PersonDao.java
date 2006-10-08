package uk.co.unclealex.rokta.model.dao;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Person;

public interface PersonDao {

	public Person store(Person person);

	public Person getPersonByName(String name);

	public SortedSet<Person> getEverybody();
	
	public SortedSet<Person> getPlayers();
}