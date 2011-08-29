/**
 * 
 */
package uk.co.unclealex.rokta.server.process;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.dao.ColourDao;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Colour;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.util.DateUtil;

/**
 * @author alex
 *
 */
@Transactional
public class PersonServiceImpl implements PersonService {

	private PersonDao i_personDao;
	private GameDao i_gameDao;
	private PasswordEncoder i_passwordEncoder;
	private SaltSource i_saltSource;
	private ColourDao i_colourDao;
	
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
	public void changePassword(String name, String newPassword) {
		PersonDao personDao = getPersonDao();
		Person person = personDao.getPersonByName(name);
		if (person != null) {
			person.setPassword(encryptPassword(name, newPassword));
			personDao.store(person);
		}
	}

	@Override
	public void resetAllPasswords() {
		for (String username : getAllUsernames()) {
			changePassword(username, username);
		}
	}
	
	@Override
	public void changeGraphingColour(String name, String colourName) {
		PersonDao personDao = getPersonDao();
		Person person = personDao.getPersonByName(name);
		Colour colour = getColourDao().getColourByName(colourName);
		person.setColour(colour);
		personDao.store(person);
	}
	
  protected String encryptPassword(String username, String password) {
		Set<GrantedAuthority> emptySet = Collections.emptySet();
		User user = new User(username, password, true, true, true, true, emptySet);
		return getPasswordEncoder().encodePassword(password, getSaltSource().getSalt(user));
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

	public SaltSource getSaltSource() {
		return i_saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		i_saltSource = saltSource;
	}

	public ColourDao getColourDao() {
		return i_colourDao;
	}

	public void setColourDao(ColourDao colourDao) {
		i_colourDao = colourDao;
	}

}
