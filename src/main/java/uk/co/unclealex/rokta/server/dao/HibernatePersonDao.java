package uk.co.unclealex.rokta.server.dao;

import java.util.SortedSet;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.server.model.Person;

@Transactional
public class HibernatePersonDao extends HibernateKeyedDao<Person> implements PersonDao {

	public Person getPersonByName(final String name) {
		return uniqueResult(getSession().getNamedQuery("person.findByName").setString("name", name));
	}
	
  public Person getPersonByEmailAddress(final String email) {
    return uniqueResult(getSession().getNamedQuery("person.findByEmail").setString("email", email));
  }

  public SortedSet<Person> getPlayers() {
		return asSortedSet(getSession().getNamedQuery("person.getPlayers"));
	}

  @Override
  public SortedSet<String> getAllEmailAddresses() {
    Query q = getSession().createQuery("select email from Person where email is not null");
    return asSortedSet(q, String.class);
  }

  @Override
  public SortedSet<String> getAllUsernames() {
    Query q = getSession().createQuery("select name from Person");
    return asSortedSet(q, String.class);
  }

	@Override
	public SortedSet<String> getAllPlayerNames() {
		Query query = getSession().createQuery("select person.name from Play");
		return asSortedSet(query, String.class);
	}
	@Override
	public Person createExampleBean() {
		return new Person();
	}
}
