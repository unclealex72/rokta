/**
 * 
 */
package uk.co.unclealex.rokta.model.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;

/**
 * @author alex
 *
 */
public class HibernatePlayDao extends HibernateDaoSupport implements PlayDao {

	public int countByPersonAndHand(final Person person, final Hand hand) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query q = session.getNamedQuery("play.countByPersonAndHand");
						q.setEntity("person", person);
						q.setParameter("hand", hand);
            return q.uniqueResult();
					}
				});
	}

}
