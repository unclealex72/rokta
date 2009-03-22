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
public class HibernateRoundDao extends HibernateDaoSupport implements RoundDao {

	public int countOpeningGambitsByPersonAndHand(final Person person, final Hand hand) {
		long result =
			(Long) getSession().getNamedQuery("round.countOpeningGambitsByPersonAndHand").
							setEntity("person", person).
							setParameter("hand", hand).
							uniqueResult();
    return (int) result;
	}	
}
