package uk.co.unclealex.rokta.server.dao;

import java.util.SortedSet;

import uk.co.unclealex.hibernate.dao.KeyedDao;
import uk.co.unclealex.rokta.server.model.Person;

public interface PersonDao extends KeyedDao<Person>{

	public Person getPersonByName(String name);

	public SortedSet<Person> getPlayers();

	public Person findPersonByNameAndPassword(String username, String encodedPassword);

	public SortedSet<String> getAllPlayerNames();

	public SortedSet<String> getAllUsernames();
}