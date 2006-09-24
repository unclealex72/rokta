/**
 * 
 */
package uk.co.unclealex.rokta.process;

import java.util.Date;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;
import uk.co.unclealex.rokta.util.DateUtil;

/**
 * @author alex
 *
 */
public class PersonManagerImpl implements PersonManager {

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

	public boolean currentlyPlaying(Person player, Date date) {
		Game lastGamePlayed = getGameDao().getLastGamePlayed(player);
		return (isGameOnDate(lastGamePlayed, date)); 
	}

	private boolean isGameOnDate(Game game, Date date) {
		return getDateUtil().areSameDay(game.getDatePlayed(), date);
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
