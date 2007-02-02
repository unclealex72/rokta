package uk.co.unclealex.rokta.model.dao;

import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;

public class HibernatePersonDao extends HibernateStoreRemoveDao<Person> implements PersonDao {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#getPersonByName(java.lang.String)
	 */
	public Person getPersonByName(final String name) {
		return (Person) getSession().getNamedQuery("person.findByName").setString("name", name).uniqueResult();
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#getEverybody()
	 */
	public SortedSet<Person> getEverybody() {
		return new TreeSet<Person>(getSession().getNamedQuery("person.getAll").list());
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#countHandForPlayer(uk.co.unclealex.rokta.model.Person, uk.co.unclealex.rokta.model.Hand)
	 */
	public int countHandForPlayer(final Person person, final Hand hand) {
		return (Integer)
			getSession().getNamedQuery("person.countHand").
						setEntity("person", person).
						setParameter("hand", hand).
						uniqueResult();
	}

	public SortedSet<Person> getPlayers() {
		return new TreeSet<Person>(getSession().getNamedQuery("person.getPlayers").list());
	}
}
