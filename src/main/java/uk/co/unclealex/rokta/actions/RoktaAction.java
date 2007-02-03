package uk.co.unclealex.rokta.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;

import uk.co.unclealex.rokta.filter.GameFilter;
import uk.co.unclealex.rokta.filter.GameFilterFactory;
import uk.co.unclealex.rokta.filter.IllegalFilterEncodingException;
import uk.co.unclealex.rokta.filter.YearGameFilter;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.ColourDao;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;
import uk.co.unclealex.rokta.model.dao.PlayDao;
import uk.co.unclealex.rokta.model.dao.RoundDao;

import com.opensymphony.webwork.interceptor.PrincipalAware;
import com.opensymphony.webwork.interceptor.PrincipalProxy;
import com.opensymphony.xwork.ActionSupport;

public class RoktaAction extends ActionSupport implements PrincipalAware {

	protected static final String DATE_FORMAT_WEEK = "'Week 'ww, yyyy";
	protected static final String DATE_FORMAT_MONTH = "MMMMM, yyyy";
	protected static final String DATE_FORMAT_YEAR = "yyyy";
	
	private PersonDao i_personDao;
	private GameDao i_gameDao;
	private PlayDao i_playDao;
	private RoundDao i_roundDao;
	private ColourDao i_colourDao;
	
	private PrincipalProxy i_principalProxy;
	
	private GameFilterFactory i_gameFilterFactory;
	private GameFilter i_gameFilterInternal;
	private String i_gameFilter;
	
	private SortedSet<Person> i_players;
	private SortedSet<Game> i_games;
	
	private String i_filteredDate;
	private Date i_selectedDate;
	private Date i_initialDate;
	
	private Date i_minimumDate;
	private Date i_maximumDate;
	
	@Override
	public final String execute() throws Exception {
		populateFilter();
		populateNavigation();
		populateProfileNavigation();
		return executeInternal();
	}

	private void populateFilter() throws IllegalFilterEncodingException {
		GameFilterFactory gameFilterFactory = getGameFilterFactory();
		String encoded = getGameFilter();
		GameFilter gameFilter;
		if (StringUtils.isEmpty(encoded)) {
			gameFilter = new YearGameFilter(new GregorianCalendar().get(Calendar.YEAR), getGameDao());
			setGameFilter(gameFilter.encode());
		}
		else {
			gameFilter = gameFilterFactory.decode(encoded);
		}
		setGameFilterInternal(gameFilter);
	}
	
	private void populateProfileNavigation() {
		setPlayers(getPersonDao().getPlayers());
	}

	private void populateNavigation() throws ParseException {
		String filteredDate = getFilteredDate();
		Date selectedDate = null;
		if (filteredDate != null) {
			selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(filteredDate);
		}
		SortedSet<Game> allGames = getGameDao().getAllGames();
		Date lastGamePlayed;
		Date firstGamePlayed;
		if (allGames.isEmpty()) {
			lastGamePlayed = firstGamePlayed = new Date();
		}
		else {
			firstGamePlayed = allGames.first().getDatePlayed();
			lastGamePlayed = allGames.last().getDatePlayed();
		}
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
		setMinimumDate(firstGamePlayed);
		Calendar maximumDate = new GregorianCalendar();
		maximumDate.setTime(lastGamePlayed);
		maximumDate.add(Calendar.DAY_OF_YEAR, 1);
		setMaximumDate(maximumDate.getTime());
	}

	protected String executeInternal() throws Exception {
		return SUCCESS;
	}

	public SortedSet<Game> getGames() {
		if (i_games == null) {
			i_games = getGameFilterInternal().getGames();
		}
		return i_games;
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

	/**
	 * @return the maximumDate
	 */
	public Date getMaximumDate() {
		return i_maximumDate;
	}

	/**
	 * @param maximumDate the maximumDate to set
	 */
	public void setMaximumDate(Date maximumDate) {
		i_maximumDate = maximumDate;
	}

	/**
	 * @return the minimumDate
	 */
	public Date getMinimumDate() {
		return i_minimumDate;
	}

	/**
	 * @param minimumDate the minimumDate to set
	 */
	public void setMinimumDate(Date minimumDate) {
		i_minimumDate = minimumDate;
	}

	public GameFilterFactory getGameFilterFactory() {
		return i_gameFilterFactory;
	}

	public void setGameFilterFactory(GameFilterFactory gameFilterFactory) {
		i_gameFilterFactory = gameFilterFactory;
	}

	public String getGameFilter() {
		return i_gameFilter;
	}

	public void setGameFilter(String gameFilter) {
		i_gameFilter = gameFilter;
	}

	public GameFilter getGameFilterInternal() {
		return i_gameFilterInternal;
	}

	public void setGameFilterInternal(GameFilter gameFilterInternal) {
		i_gameFilterInternal = gameFilterInternal;
	}

	public PrincipalProxy getPrincipalProxy() {
		return i_principalProxy;
	}

	public void setPrincipalProxy(PrincipalProxy principalProxy) {
		i_principalProxy = principalProxy;
	}

}
