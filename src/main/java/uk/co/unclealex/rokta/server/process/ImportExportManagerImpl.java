package uk.co.unclealex.rokta.server.process;

import java.util.SortedSet;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.dao.ColourDao;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Colour;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Rokta;

@Transactional
public class ImportExportManagerImpl implements ImportExportManager {

	private PersonDao i_personDao;
	private GameDao i_gameDao;
	private ColourDao i_colourDao;
	
	public Rokta exportAll() {
		SortedSet<Person> everybody = getPersonDao().getAll();
		SortedSet<Game> allGames = getGameDao().getAll();
		SortedSet<Colour> allColours = getColourDao().getAll();
		return new Rokta(everybody, allGames, allColours);
	}

	public void importAll(Rokta rokta) {
		GameDao gameDao = getGameDao();
		PersonDao personDao = getPersonDao();
		ColourDao colourDao = getColourDao();
		
		// Remove everything first
		for (Game game : gameDao.getAll()) {
			gameDao.remove(game);
		}
		for (Person person : personDao.getAll()) {
			personDao.remove(person);
		}
		for (Colour colour : colourDao.getAll()) {
			colourDao.remove(colour);
		}
		
		getGameDao().flush();
		
		for (Colour colour : rokta.getColours()) {
			colourDao.store(colour);
		}
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

	public ColourDao getColourDao() {
		return i_colourDao;
	}

	public void setColourDao(ColourDao colourDao) {
		i_colourDao = colourDao;
	}
}
