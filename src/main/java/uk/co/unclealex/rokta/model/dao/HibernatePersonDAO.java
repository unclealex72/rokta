package uk.co.unclealex.rokta.model.dao;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Query;

import uk.co.unclealex.rokta.model.Person;

public class HibernatePersonDAO extends HibernateDAO implements PersonDAO {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDAO#getPersonByName(java.lang.String)
	 */
	public Person getPersonByName(String name) {
        Query q = getSession().getNamedQuery("person.findByName");
        q.setString("name", name);
        return (Person) q.uniqueResult();
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDAO#getEverybody()
	 */
	public SortedSet<Person> getEverybody() {
		Query q = getSession().getNamedQuery("person.getAll");
		List<Person> results = q.list();
		return new TreeSet<Person>(results);
	}
}
