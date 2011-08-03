package uk.co.unclealex.rokta.internal.dao;

import java.util.SortedSet;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.views.Hand;

@Repository
@Transactional
public class HibernatePersonDao extends HibernateKeyedDao<Person> implements PersonDao {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#getPersonByName(java.lang.String)
	 */
	public Person getPersonByName(final String name) {
		return uniqueResult(getSession().getNamedQuery("person.findByName").setString("name", name));
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.PersonDao#countHandForPlayer(uk.co.unclealex.rokta.model.Person, uk.co.unclealex.rokta.model.Hand)
	 */
	public int countHandForPlayer(final Person person, final Hand hand) {
		return uniqueResult(
			getSession().getNamedQuery("person.countHand").
						setEntity("person", person).
						setParameter("hand", hand),
			Integer.class);
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
	public Person createExampleBean() {
		return new Person();
	}
}
