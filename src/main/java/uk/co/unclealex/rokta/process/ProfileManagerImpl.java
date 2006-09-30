/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.math.Fraction;

import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;
import uk.co.unclealex.rokta.model.dao.PlayDao;
import uk.co.unclealex.rokta.model.dao.RoundDao;

/**
 * @author alex
 *
 */
public class ProfileManagerImpl implements ProfileManager {

	private Person i_person;

	private GameDao i_gameDao;
	private PersonDao i_personDao;
	private PlayDao i_playDao;
	private RoundDao i_roundDao;
	
	public SortedMap<Hand, Integer> countHands() {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public int count(Hand hand) {
				return getPlayDao().countByPersonAndHand(getPerson(), hand);
			}
		};
		return countOperation(operation);
	}
	
	public SortedMap<Hand, Integer> countOpeningGambits() {
		CountHandPersonOperation operation = new CountHandPersonOperation() {
			public int count(Hand hand) {
				return getRoundDao().countOpeningGambitsByPersonAndHand(getPerson(), hand);
			}			
		};
		return countOperation(operation);
	}

	protected interface CountHandPersonOperation {
		public int count(Hand hand);
	}
	
	protected SortedMap<Hand, Integer> countOperation(CountHandPersonOperation operation) {
		SortedMap<Hand, Integer> handMap = new TreeMap<Hand , Integer>();
		for (Hand hand : Hand.getAllHands()) {
			handMap.put(hand, operation.count(hand));
		}
		return handMap;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.ProfileManager#getHeadToHeadRoundWinRate()
	 */
	public SortedMap<Person, Fraction> getHeadToHeadRoundWinRate() {
		// TODO Auto-generated method stub
		return null;
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
	 * @return the gameDao
	 */
	public GameDao getGameDao() {
		return i_gameDao;
	}

	/**
	 * @param gameDao the gameDao to set
	 */
	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

	/**
	 * @return the personDao
	 */
	public PersonDao getPersonDao() {
		return i_personDao;
	}

	/**
	 * @param personDao the personDao to set
	 */
	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

	/**
	 * @return the playDao
	 */
	public PlayDao getPlayDao() {
		return i_playDao;
	}

	/**
	 * @param playDao the playDao to set
	 */
	public void setPlayDao(PlayDao playDao) {
		i_playDao = playDao;
	}

	/**
	 * @return the roundDao
	 */
	public RoundDao getRoundDao() {
		return i_roundDao;
	}

	/**
	 * @param roundDao the roundDao to set
	 */
	public void setRoundDao(RoundDao roundDao) {
		i_roundDao = roundDao;
	}
}
