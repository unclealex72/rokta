package uk.co.unclealex.rokta.server.dao;

import java.util.SortedSet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.server.model.Person;

@Transactional
public class HibernatePersonDao extends HibernateKeyedDao<Person> implements PersonDao {

	public Person getPersonByName(final String name) {
		return uniqueResult(getSession().getNamedQuery("person.findByName").setString("name", name));
	}
	
	public SortedSet<Person> getPlayers() {
		return asSortedSet(getSession().getNamedQuery("person.getPlayers"));
	}

	public Person findPersonByNameAndPassword(String name, String encodedPassword) {
		Criteria criteria = getSession().createCriteria(Person.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("password", encodedPassword));
		return uniqueResult(criteria);
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
