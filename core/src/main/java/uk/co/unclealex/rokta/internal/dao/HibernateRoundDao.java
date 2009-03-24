/**
 * 
 */
package uk.co.unclealex.rokta.model.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;

/**
 * @author alex
 *
 */
@Transactional
@Repository
public class HibernateRoundDao extends HibernateKeyedDao<Round> implements RoundDao {

	public int countOpeningGambitsByPersonAndHand(final Person person, final Hand hand) {
		Query query = 
			getSession().getNamedQuery("round.countOpeningGambitsByPersonAndHand").
							setEntity("person", person).
							setParameter("hand", hand);
    return uniqueResult(query, Integer.class);
	}
	
	@Override
	public Round createExampleBean() {
		return new Round();
	}
}
