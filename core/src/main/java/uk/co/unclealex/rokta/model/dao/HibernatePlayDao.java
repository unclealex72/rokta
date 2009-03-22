/**
 * 
 */
package uk.co.unclealex.rokta.model.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public class HibernatePlayDao extends HibernateDaoSupport implements PlayDao {

	public int countByPersonAndHand(final Person person, final Hand hand) {
		long result =
			(Long) getSession().getNamedQuery("play.countByPersonAndHand").
							setEntity("person", person).
							setParameter("hand", hand).
							uniqueResult();
    return (int) result;
	}

}
