/**
 * 
 */
package uk.co.unclealex.rokta.server.dao;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Play;
import uk.co.unclealex.rokta.shared.model.Hand;

/**
 * @author alex
 *
 */
@Transactional
public class HibernatePlayDao extends HibernateKeyedDao<Play> implements PlayDao {

	private GameFilterSupport i_gameFilterSupport;
	
	@Override
	public Play createExampleBean() {
		return new Play();
	}

	@Override
	public SortedMap<Hand, Integer> countPlaysByPersonAndHand(GameFilter gameFilter, Person person) {
		return countPlaysByPersonAndHand(gameFilter, person, new EmptyQueryParameters());
	}

	@Override
	public SortedMap<Hand, Integer> countOpeningPlaysByPersonAndHand(GameFilter gameFilter, Person person) {
		QueryParameters queryParameters = new SimpleQueryParameters("r.round = 1") {
			@Override
			public void addRestrictions(Query query) {
				// Do nothing
			}
		};
		return countPlaysByPersonAndHand(gameFilter, person, queryParameters);
	}

	protected SortedMap<Hand, Integer> countPlaysByPersonAndHand(GameFilter gameFilter, final Person person,
			QueryParameters extraQueryParameters) {
		QueryParameters queryParameters = new SimpleQueryParameters("p.person = :person") {
			@Override
			public void addRestrictions(Query query) {
				query.setEntity("person", person);
			}
		};
		Query query = getGameFilterSupport().createQuery(
				gameFilter,
				"select count(*), p.hand from Game g join g.rounds as r join r.plays as p", 
				new JoinQueryParameters(queryParameters, extraQueryParameters), "group by p.hand");
		return asMap(query);
	}

	
	protected SortedMap<Hand, Integer> asMap(Query query) {
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.list();
		SortedMap<Hand, Integer> map = new TreeMap<Hand, Integer>();
		for (Object[] result : results) {
			map.put((Hand) result[0], (Integer) result[1]);
		}
		return map;
	}

	public GameFilterSupport getGameFilterSupport() {
		return i_gameFilterSupport;
	}

	public void setGameFilterSupport(GameFilterSupport gameFilterSupport) {
		i_gameFilterSupport = gameFilterSupport;
	}
}
