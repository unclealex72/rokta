/**
 * 
 */
package uk.co.unclealex.rokta.server.dao;

import java.util.List;
import java.util.SortedMap;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Play;
import uk.co.unclealex.rokta.shared.model.Hand;

import com.google.common.collect.Maps;

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
	public SortedMap<String, SortedMap<Hand, Long>> countPlaysByPersonAndHand(GameFilter gameFilter) {
		return countPlaysByPersonAndHand(gameFilter, new EmptyQueryParameters());
	}

	@Override
	public SortedMap<String, SortedMap<Hand, Long>> countOpeningPlaysByPersonAndHand(GameFilter gameFilter) {
		QueryParameters queryParameters = new SimpleQueryParameters("r.round = 1") {
			@Override
			public void addRestrictions(Query query) {
				// Do nothing
			}
		};
		return countPlaysByPersonAndHand(gameFilter, queryParameters);
	}

	protected SortedMap<String, SortedMap<Hand, Long>> countPlaysByPersonAndHand(GameFilter gameFilter,
			QueryParameters queryParameters) {
		Query query = getGameFilterSupport().createQuery(
				gameFilter,
				"select p.person.name, p.hand, count(*) from Game g join g.rounds as r join r.plays as p", 
				queryParameters, "group by p.hand, p.person.name");
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.list();
		SortedMap<String, SortedMap<Hand, Long>> personMap = Maps.newTreeMap();
		for (Object[] result : results) {
			String person = (String) result[0];
			Hand hand = (Hand) result[1];
			Long count = (Long) result[2];
			SortedMap<Hand, Long> handMap = personMap.get(person);
			if (handMap == null) {
				handMap = Maps.newTreeMap();
				personMap.put(person, handMap);
			}
			handMap.put(hand, count);
		}
		return personMap;
	}

	
	public GameFilterSupport getGameFilterSupport() {
		return i_gameFilterSupport;
	}

	public void setGameFilterSupport(GameFilterSupport gameFilterSupport) {
		i_gameFilterSupport = gameFilterSupport;
	}
}
