package uk.co.unclealex.rokta.server.process;

import java.util.SortedSet;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Rokta;

@Transactional
public class ImportExportManagerImpl implements ImportExportManager {

	private PersonDao i_personDao;
	private GameDao i_gameDao;
	
	public Rokta exportAll() {
		SortedSet<Person> everybody = getPersonDao().getAll();
		SortedSet<Game> allGames = getGameDao().getAll();
		return new Rokta(everybody, allGames);
	}

	public void importAll(Rokta rokta) {
		GameDao gameDao = getGameDao();
		PersonDao personDao = getPersonDao();
		
		// Remove everything first
		for (Game game : gameDao.getAll()) {
			gameDao.remove(game);
		}
		for (Person person : personDao.getAll()) {
			personDao.remove(person);
		}
		getGameDao().flush();
		
		for (Person person : rokta.getPeople()) {
			personDao.store(person);
		}
		for (Game game : rokta.getGames()) {
			gameDao.store(game);
		}
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}
}
