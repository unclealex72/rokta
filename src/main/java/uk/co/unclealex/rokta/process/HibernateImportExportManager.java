package uk.co.unclealex.rokta.process;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Rokta;
import uk.co.unclealex.rokta.model.dao.ColourDao;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;

public class HibernateImportExportManager implements ImportExportManager {

	private PersonDao i_personDao;
	private GameDao i_gameDao;
	private ColourDao i_colourDao;
	
	public Rokta exportAll() {
		SortedSet<Person> everybody = getPersonDao().getEverybody();
		SortedSet<Game> allGames = getGameDao().getAllGames();
		SortedSet<Colour> allColours = getColourDao().getColours();
		return new Rokta(everybody, allGames, allColours);
	}

	public void importAll(Rokta rokta) {
		// TODO Auto-generated method stub

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
