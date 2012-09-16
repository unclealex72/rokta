/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.Date;
import java.util.SortedSet;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.util.DateUtil;
import uk.co.unclealex.rokta.shared.model.Colour;

/**
 * @author alex
 *
 */
@Transactional
public class PersonServiceImpl implements PersonService {

	private PersonDao i_personDao;
	private GameDao i_gameDao;
	
	private DateUtil i_dateUtil;
	
	public Person getExemptPlayer(Date date) {
		Game lastGame = getGameDao().getLastGame();
		if (isGameOnDate(lastGame, date)) {
			return lastGame.getLoser();
		}
		return null;
	}

	@Override
	public SortedSet<String> getAllUsernames() {
		return getPersonDao().getAllUsernames();
	}
	
	@Override
	public SortedSet<String> getAllPlayerNames() {
		return getPersonDao().getAllPlayerNames();
	}
	
	protected boolean isGameOnDate(Game game, Date date) {
		return getDateUtil().areSameDay(game.getDatePlayed(), date);
	}
	
	@Override
	public void changeGraphingColour(String name, Colour colour) {
		PersonDao personDao = getPersonDao();
		Person person = personDao.getPersonByName(name);
		person.setGraphingColour(colour);
		personDao.store(person);
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
	 * @return the dateUtil
	 */
	public DateUtil getDateUtil() {
		return i_dateUtil;
	}

	/**
	 * @param dateUtil the dateUtil to set
	 */
	public void setDateUtil(DateUtil dateUtil) {
		i_dateUtil = dateUtil;
	}
}
