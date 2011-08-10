/**
 * 
 */
package uk.co.unclealex.rokta.server.dao;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.server.model.Round;

/**
 * @author alex
 *
 */
@Transactional
public class HibernateRoundDao extends HibernateKeyedDao<Round> implements RoundDao {

	@Override
	public Round createExampleBean() {
		return new Round();
	}
}
