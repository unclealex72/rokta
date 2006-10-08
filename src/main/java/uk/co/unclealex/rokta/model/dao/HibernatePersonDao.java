package uk.co.unclealex.rokta.model.dao;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;

public class HibernatePersonDao extends HibernateDaoSupport implements PersonDao {

	public Person store(Person person) {
		getHibernateTemplate().save(person);
		return person;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#getPersonByName(java.lang.String)
	 */
	public Person getPersonByName(final String name) {
		return (Person) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						return session.getNamedQuery("person.findByName").setString("name", name).uniqueResult();
					}
				});
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#getEverybody()
	 */
	public SortedSet<Person> getEverybody() {
		return new TreeSet<Person>(getHibernateTemplate().findByNamedQuery("person.getAll"));
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#countHandForPlayer(uk.co.unclealex.rokta.model.Person, uk.co.unclealex.rokta.model.Hand)
	 */
	public int countHandForPlayer(final Person person, final Hand hand) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						return session.getNamedQuery("person.countHand").
						setEntity("person", person).
						setParameter("hand", hand).
						uniqueResult();
					}
				});
	}

	public SortedSet<Person> getPlayers() {
		return new TreeSet<Person>(getHibernateTemplate().findByNamedQuery("person.getPlayers"));
	}
}
