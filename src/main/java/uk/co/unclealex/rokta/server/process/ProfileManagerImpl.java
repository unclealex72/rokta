/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.WinLoseCounter;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Play;
import uk.co.unclealex.rokta.server.model.Round;

/**
 * @author alex
 *
 */
@Transactional
@Service
public class ProfileManagerImpl implements ProfileManager {

	private StatisticsService i_statisticsService;
	private GameDao i_gameDao;
	
	@Override
	public SortedMap<Hand, Integer> countHands(GameFilter gameFilter, Person person) {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public Map<Hand, Integer> count(Person person, Game game) {
				Map<Hand, Integer> handCountMap = new HashMap<Hand, Integer>();
				for (Round round : game.getRounds()) {
					addHandsToHandCountMap(round, handCountMap, person);
				}
				return handCountMap;
			}
		};
		return countOperation(operation, gameFilter, person);
	}
	
	@Override
	public SortedMap<Hand, Integer> countOpeningGambits(GameFilter gameFilter, Person person) {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public Map<Hand, Integer> count(Person person, Game game) {
				Map<Hand, Integer> handCountMap = new HashMap<Hand, Integer>();
				addHandsToHandCountMap(game.getRounds().first(), handCountMap, person);
				return handCountMap;
			}
		};
		return countOperation(operation, gameFilter, person);
	}

	protected interface CountHandPersonOperation {
		public Map<Hand, Integer> count(Person person, Game game);
	}
	
	protected void addHandsToHandCountMap(Round round, Map<Hand, Integer> handCountMap, Person person) {
		boolean found = false;
		for (Iterator<Play> iter = round.getPlays().iterator(); iter.hasNext() && !found; ) {
			Play play = iter.next();
			if (play.getPerson().equals(person)) {
				found = true;
				Integer handCount = handCountMap.get(play.getHand());
				handCountMap.put(play.getHand(), (handCount==null?0:handCount) + 1);
			}
		}
	}
	
	protected SortedMap<Hand, Integer> countOperation(CountHandPersonOperation operation, GameFilter gameFilter, Person person) {
		SortedMap<Hand, Integer> handMap = new TreeMap<Hand , Integer>();
		for (Game game : getGameDao().getGamesByFilter(gameFilter)) {
			Map<Hand, Integer> handCount = operation.count(person, game);
			for (Map.Entry<Hand, Integer> entry : handCount.entrySet()) {
				Hand hand = entry.getKey();
				int count = entry.getValue();
				Integer total = handMap.get(hand);
				handMap.put(hand, (total==null?0:total) + count);
			}
		}
		return handMap;
	}

	@Override
	public SortedMap<Person, WinLoseCounter> getHeadToHeadRoundWinRate(GameFilter gameFilter, Person person) {
		return getStatisticsService().getHeadToHeadResultsByPerson(gameFilter).get(person);
	}

	/**
	 * @return the statisticsManager
	 */
	public StatisticsService getStatisticsService() {
		return i_statisticsService;
	}

	/**
	 * @param statisticsManager the statisticsManager to set
	 */
	@Required
	public void setStatisticsService(StatisticsService statisticsManager) {
		i_statisticsService = statisticsManager;
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	@Required
	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}
}
