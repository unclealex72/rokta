/**
 * 
 */
package uk.co.unclealex.rokta.server.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.client.views.Hand;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Play;

/**
 * @author alex
 *
 */
@Repository
@Transactional
public class HibernatePlayDao extends HibernateKeyedDao<Play> implements PlayDao {

	public int countByPersonAndHand(final Person person, final Hand hand) {
		Query query = 
			getSession().getNamedQuery("play.countByPersonAndHand").
							setEntity("person", person).
							setParameter("hand", hand);
    return uniqueResult(query, Integer.class);
	}

	@Override
	public Play createExampleBean() {
		return new Play();
	}
}
