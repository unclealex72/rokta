package uk.co.unclealex.rokta.model.dao;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Person;

public interface PersonDAO {

	public Person getPersonByName(String name);

	public SortedSet<Person> getEverybody();

}