package uk.co.unclealex.rokta.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.process.DateFilterPredicate;
import uk.co.unclealex.rokta.process.LeagueManager;

import com.opensymphony.xwork.ActionSupport;

public class LeagueAction extends ActionSupport {

	private static final String DATE_FORMAT_WEEK = "'Week 'ww, yyyy";
	private static final String DATE_FORMAT_MONTH = "MMMMM, yyyy";
	
	private GameDao i_gameDao;
	private LeagueManager i_leagueManager;
	
	private League i_league;

	private String i_selectedWeek;
	private String i_selectedMonth;
	
	private List<String> i_selectableWeeks;
	private List<String> i_selectableMonths;
	
	@Override
	public String execute() {
		LeagueManager manager = getLeagueManager();
		SortedSet<Game> games = getGameDao().getAllGames();
		
		DateFormat dfByWeek = new SimpleDateFormat(DATE_FORMAT_WEEK);
		DateFormat dfByMonth = new SimpleDateFormat(DATE_FORMAT_MONTH);
		
		setSelectableWeeks(createDates(dfByWeek, games));
		setSelectableMonths(createDates(dfByMonth, games));
		
		if (!StringUtils.isEmpty(getSelectedWeek())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByWeek, getSelectedWeek()));
		}
		else if (!StringUtils.isEmpty(getSelectedMonth())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByMonth, getSelectedMonth()));
		}

		SortedSet<Game> previousGames = new TreeSet<Game>();
		previousGames.addAll(games);
		if (!games.isEmpty()) {
			Game lastGame = previousGames.last();
			previousGames.remove(lastGame);
		}
		
		manager.setComparator(manager.getCompareByLossesPerGame());
		manager.setCurrentGames(games);
		manager.setPreviousGames(previousGames);
		
		League league = manager.generateLeague(new Date());
		setLeague(league);
		return SUCCESS;
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

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

	public League getLeague() {
		return i_league;
	}

	public void setLeague(League league) {
		i_league = league;
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
	 * @return the leagueManager
	 */
	public LeagueManager getLeagueManager() {
		return i_leagueManager;
	}

	/**
	 * @param leagueManager the leagueManager to set
	 */
	public void setLeagueManager(LeagueManager leagueManager) {
		i_leagueManager = leagueManager;
	}
}
