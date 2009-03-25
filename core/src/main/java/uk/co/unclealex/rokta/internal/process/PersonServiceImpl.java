/**
 * 
 */
package uk.co.unclealex.rokta.internal.process;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.internal.dao.GameDao;
import uk.co.unclealex.rokta.internal.dao.PersonDao;
import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.internal.security.PasswordEncoder;
import uk.co.unclealex.rokta.internal.util.DateUtil;

/**
 * @author alex
 *
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	protected PersonDao i_personDao;
	protected GameDao i_gameDao;
	protected PasswordEncoder i_passwordEncoder;
	
	protected DateUtil i_dateUtil;
	
	public Person getExemptPlayer(DateTime date) {
		Game lastGame = getGameDao().getLastGame();
		if (isGameOnDate(lastGame, date)) {
			return lastGame.getLoser();
		}
		return null;
	}

	public boolean currentlyPlaying(Person player, DateTime date) {
		Game lastGamePlayed = getGameDao().getLastGamePlayed(player);
		return (isGameOnDate(lastGamePlayed, date)); 
	}

	protected boolean isGameOnDate(Game game, DateTime date) {
		return getDateUtil().areSameDay(game.getDatePlayed(), date);
	}
	
	public boolean changePassword(String name, String currentPassword, String newPassword) {
		PersonDao personDao = getPersonDao();
		PasswordEncoder passwordEncoder = getPasswordEncoder(); 
		Person person = personDao.findPersonByNameAndPassword(name, passwordEncoder.encode(currentPassword));
		if (person == null) {
			return false;
		}
		person.setPassword(passwordEncoder.encode(newPassword));
		personDao.store(person);
		return true;
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

	public PasswordEncoder getPasswordEncoder() {
		return i_passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		i_passwordEncoder = passwordEncoder;
	}

}
