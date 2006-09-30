package uk.co.unclealex.rokta.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;
import uk.co.unclealex.rokta.model.dao.PlayDao;
import uk.co.unclealex.rokta.model.dao.RoundDao;

import com.opensymphony.xwork.ActionSupport;

public class BasicAction extends ActionSupport {

	protected static final String DATE_FORMAT_WEEK = "'Week 'ww, yyyy";
	protected static final String DATE_FORMAT_MONTH = "MMMMM, yyyy";
	protected static final String DATE_FORMAT_YEAR = "yyyy";
	private PersonDao i_personDao;
	private GameDao i_gameDao;
	private PlayDao i_playDao;
	private RoundDao i_roundDao;
	
	private String i_selectedWeek;
	private String i_selectedMonth;
	private String i_selectedYear;
	private List<String> i_selectableWeeks;
	private List<String> i_selectableMonths;
	private List<String> i_selectableYears;
	
	private SortedSet<Person> i_players;
	
	@Override
	public final String execute() throws Exception {
		populateLeagueNavigation();
		populateProfileNavigation();
		return executeInternal();
	}

	private void populateProfileNavigation() {
		setPlayers(getPersonDao().getPlayers());
	}

	private void populateLeagueNavigation() {
		SortedSet<Game> games = getGameDao().getAllGames();
		
		DateFormat dfByWeek = new SimpleDateFormat(DATE_FORMAT_WEEK);
		DateFormat dfByMonth = new SimpleDateFormat(DATE_FORMAT_MONTH);
		DateFormat dfByYear = new SimpleDateFormat(DATE_FORMAT_YEAR);
		
		setSelectableWeeks(createDates(dfByWeek, games));
		setSelectableMonths(createDates(dfByMonth, games));
		setSelectableYears(createDates(dfByYear, games));
		
	}

	/**
	 * @param dfByWeek
	 * @param games
	 * @return
	 */
	private List<String> createDates(DateFormat df, SortedSet<Game> games) {
		List<String> dates = new LinkedList<String>();
		for (Game game : games) {
			String date = df.format(game.getDatePlayed());
			if (!dates.contains(date)) {
				dates.add(date);
			}
		}
		return dates;
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
	 * @return Returns the selectableMonths.
	 */
	public List<String> getSelectableMonths() {
		return i_selectableMonths;
	}

	/**
	 * @param selectableMonths The selectableMonths to set.
	 */
	public void setSelectableMonths(List<String> selectableMonths) {
		i_selectableMonths = selectableMonths;
	}

	/**
	 * @return Returns the selectableWeeks.
	 */
	public List<String> getSelectableWeeks() {
		return i_selectableWeeks;
	}

	/**
	 * @param selectableWeeks The selectableWeeks to set.
	 */
	public void setSelectableWeeks(List<String> selectableWeeks) {
		i_selectableWeeks = selectableWeeks;
	}

	/**
	 * @return Returns the selectedMonth.
	 */
	public String getSelectedMonth() {
		return i_selectedMonth;
	}

	/**
	 * @param selectedMonth The selectedMonth to set.
	 */
	public void setSelectedMonth(String selectedMonth) {
		i_selectedMonth = selectedMonth;
	}

	/**
	 * @return Returns the selectedWeek.
	 */
	public String getSelectedWeek() {
		return i_selectedWeek;
	}

	/**
	 * @param selectedWeek The selectedWeek to set.
	 */
	public void setSelectedWeek(String selectedWeek) {
		i_selectedWeek = selectedWeek;
	}

	/**
	 * @return the selectableYears
	 */
	public List<String> getSelectableYears() {
		return i_selectableYears;
	}

	/**
	 * @param selectableYears the selectableYears to set
	 */
	public void setSelectableYears(List<String> selectableYears) {
		i_selectableYears = selectableYears;
	}

	/**
	 * @return the selectedYear
	 */
	public String getSelectedYear() {
		return i_selectedYear;
	}

	/**
	 * @param selectedYear the selectedYear to set
	 */
	public void setSelectedYear(String selectedYear) {
		i_selectedYear = selectedYear;
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
}
