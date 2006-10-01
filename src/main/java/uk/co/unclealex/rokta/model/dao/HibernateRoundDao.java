/**
 * 
 */
package uk.co.unclealex.rokta.model.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;

/**
 * @author alex
 *
 */
public class HibernateRoundDao extends HibernateDaoSupport implements RoundDao {

	public int countOpeningGambitsByPersonAndHand(final Person person, final Hand hand) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query q = session.getNamedQuery("round.countOpeningGambitsByPersonAndHand");
						q.setEntity("person", person);
						q.setParameter("hand", hand);
            return q.uniqueResult();
					}
				});
	}
	
	public List<Round> getFinalRounds() {
		List<Object[]> result = getHibernateTemplate().findByNamedQuery("round.getFinalRounds");
		List<Round> rounds = new LinkedList<Round>();
		CollectionUtils.collect(
				result,
				new Transformer<Object[], Round>() {
					public Round transform(Object[] objs) {
						return (Round) objs[0];
					}
				},
				rounds
		);
		return rounds;
	}
}
