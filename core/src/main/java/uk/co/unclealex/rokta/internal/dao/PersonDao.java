package uk.co.unclealex.rokta.model.dao;

import java.util.SortedSet;

import uk.co.unclealex.hibernate.dao.KeyedDao;
import uk.co.unclealex.rokta.model.Person;

public interface PersonDao extends KeyedDao<Person>{

	public Person getPersonByName(String name);

	public SortedSet<Person> getPlayers();

	public Person findPersonByNameAndPassword(String username, String encodedPassword);
}