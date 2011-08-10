/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.Date;
import java.util.SortedSet;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.security.PasswordEncoder;
import uk.co.unclealex.rokta.server.util.DateUtil;

/**
 * @author alex
 *
 */
@Transactional
public class PersonServiceImpl implements PersonService {

	protected PersonDao i_personDao;
	protected GameDao i_gameDao;
	protected PasswordEncoder i_passwordEncoder;
	
	protected DateUtil i_dateUtil;
	
	public Person getExemptPlayer(Date date) {
		Game lastGame = getGameDao().getLastGame();
		if (isGameOnDate(lastGame, date)) {
			return lastGame.getLoser();
		}
		return null;
	}

	public SortedSet<String> getCurrentPlayerNames(final Date date) {
		DateTime midnight = getDateUtil().getStartOfDay(new DateTime(date.getTime()));
		return getPersonDao().getPlayerNamesWhoHavePlayedSince(new Date(midnight.getMillis()));
	}

	protected boolean isGameOnDate(Game game, Date date) {
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
