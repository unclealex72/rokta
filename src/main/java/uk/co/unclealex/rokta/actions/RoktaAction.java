package uk.co.unclealex.rokta.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.ColourDao;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;
import uk.co.unclealex.rokta.model.dao.PlayDao;
import uk.co.unclealex.rokta.model.dao.RoundDao;

import com.opensymphony.xwork.ActionSupport;

public class RoktaAction extends ActionSupport {

	protected static final String DATE_FORMAT_WEEK = "'Week 'ww, yyyy";
	protected static final String DATE_FORMAT_MONTH = "MMMMM, yyyy";
	protected static final String DATE_FORMAT_YEAR = "yyyy";
	
	private PersonDao i_personDao;
	private GameDao i_gameDao;
	private PlayDao i_playDao;
	private RoundDao i_roundDao;
	private ColourDao i_colourDao;
	
	private SortedSet<Person> i_players;
	private SortedSet<Game> i_allGames;
	
	private String i_filteredDate;
	private Date i_selectedDate;
	private Date i_initialDate;
	
	@Override
	public final String execute() throws Exception {
		populateLeagueNavigation();
		populateProfileNavigation();
		return executeInternal();
	}

	private void populateProfileNavigation() {
		setPlayers(getPersonDao().getPlayers());
	}

	private void populateLeagueNavigation() throws ParseException {
		String filteredDate = getFilteredDate();
		Date selectedDate = null;
		if (filteredDate != null) {
			selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(filteredDate);
		}
		SortedSet<Game> allGames = getGameDao().getAllGames();
		setAllGames(allGames);
		Date lastGamePlayed = allGames.last().getDatePlayed();
		Date now = new Date();
		setSelectedDate(selectedDate);
		if (selectedDate != null) {
			setInitialDate(selectedDate);
		}
		else if (lastGamePlayed.getTime() < now.getTime()) {
			setInitialDate(lastGamePlayed);
		}
		else {
			setInitialDate(now);
		}
	}

	protected String executeInternal() throws Exception {
		return SUCCESS;
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
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

	/**
	 * @return the players
	 */
	public SortedSet<Person> getPlayers() {
		return i_players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(SortedSet<Person> players) {
		i_players = players;
	}

	/**
	 * @return the colourDao
	 */
	public ColourDao getColourDao() {
		return i_colourDao;
	}

	/**
	 * @param colourDao the colourDao to set
	 */
	public void setColourDao(ColourDao colourDao) {
		i_colourDao = colourDao;
	}

	/**
	 * @return the allGames
	 */
	public SortedSet<Game> getAllGames() {
		return i_allGames;
	}

	/**
	 * @param allGames the allGames to set
	 */
	public void setAllGames(SortedSet<Game> allGames) {
		i_allGames = allGames;
	}

	/**
	 * @return the filteredDate
	 */
	public String getFilteredDate() {
		return i_filteredDate;
	}

	/**
	 * @param filteredDate the filteredDate to set
	 */
	public void setFilteredDate(String filteredDate) {
		i_filteredDate = filteredDate;
	}

	/**
	 * @return the selectedDate
	 */
	public Date getSelectedDate() {
		return i_selectedDate;
	}

	/**
	 * @param selectedDate the selectedDate to set
	 */
	public void setSelectedDate(Date selectedDate) {
		i_selectedDate = selectedDate;
	}

	/**
	 * @return the initialDate
	 */
	public Date getInitialDate() {
		return i_initialDate;
	}

	/**
	 * @param initialDate the initialDate to set
	 */
	public void setInitialDate(Date initialDate) {
		i_initialDate = initialDate;
	}
}
