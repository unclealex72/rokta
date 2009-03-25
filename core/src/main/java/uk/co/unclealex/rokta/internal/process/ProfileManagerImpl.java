/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.pub.model.Game;
import uk.co.unclealex.rokta.pub.model.Hand;
import uk.co.unclealex.rokta.pub.model.Person;
import uk.co.unclealex.rokta.pub.model.Play;
import uk.co.unclealex.rokta.pub.model.Round;
import uk.co.unclealex.rokta.pub.views.WinLoseCounter;

/**
 * @author alex
 *
 */
@Transactional
@Service
public class ProfileManagerImpl implements ProfileManager {

	private Person i_person;
	private SortedSet<Game> i_games;
  
	private StatisticsService i_statisticsManager;
	
	public SortedMap<Hand, Integer> countHands() {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public Map<Hand, Integer> count(Person person, Game game) {
				Map<Hand, Integer> handCountMap = new HashMap<Hand, Integer>();
				for (Round round : game.getRounds()) {
					addHandsToHandCountMap(round, handCountMap, person);
				}
				return handCountMap;
			}
		};
		return countOperation(operation);
	}
	
	public SortedMap<Hand, Integer> countOpeningGambits() {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public Map<Hand, Integer> count(Person person, Game game) {
				Map<Hand, Integer> handCountMap = new HashMap<Hand, Integer>();
				addHandsToHandCountMap(game.getRounds().first(), handCountMap, person);
				return handCountMap;
			}
		};
		return countOperation(operation);
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
	
	protected SortedMap<Hand, Integer> countOperation(CountHandPersonOperation operation) {
		SortedMap<Hand, Integer> handMap = new TreeMap<Hand , Integer>();
		Person person = getPerson();
		for (Game game : getGames()) {
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

	public SortedMap<Person, WinLoseCounter> getHeadToHeadRoundWinRate() {
		return getStatisticsManager().getHeadToHeadResultsByPerson().get(getPerson());
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return i_person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		i_person = person;
	}

	/**
	 * @return the statisticsManager
	 */
	public StatisticsService getStatisticsManager() {
		return i_statisticsManager;
	}

	/**
	 * @param statisticsManager the statisticsManager to set
	 */
	public void setStatisticsManager(StatisticsService statisticsManager) {
		i_statisticsManager = statisticsManager;
	}

  /**
   * @return the games
   */
  public SortedSet<Game> getGames() {
    return i_games;
  }

  /**
   * @param games the games to set
   */
  public void setGames(SortedSet<Game> games) {
    i_games = games;
    StatisticsService statisticsManager = getStatisticsManager();
    if (statisticsManager != null) {
      statisticsManager.setGames(games);
    }
  }

}
