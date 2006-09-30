package uk.co.unclealex.rokta.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.process.DateFilterPredicate;
import uk.co.unclealex.rokta.process.GamesLeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.LeagueManager;

public class LeagueAction extends BasicAction {

	private LeagueManager i_leagueManager;
	
	private League i_league;

	@Override
	public String executeInternal() {
		LeagueManager manager = getLeagueManager();
		SortedSet<Game> games = getGameDao().getAllGames();
		
		DateFormat dfByWeek = new SimpleDateFormat(DATE_FORMAT_WEEK);
		DateFormat dfByMonth = new SimpleDateFormat(DATE_FORMAT_MONTH);
		DateFormat dfByYear = new SimpleDateFormat(DATE_FORMAT_YEAR);
		
		if (!StringUtils.isEmpty(getSelectedWeek())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByWeek, getSelectedWeek()));
		}
		else if (!StringUtils.isEmpty(getSelectedMonth())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByMonth, getSelectedMonth()));
		}
		else if (!StringUtils.isEmpty(getSelectedYear())) {
			CollectionUtils.filter(games, new DateFilterPredicate(dfByYear, getSelectedYear()));
		}

		if (games.isEmpty()) {
			setLeague(new League());
		}
		else {
			List<Game> milestoneGames = new LinkedList<Game>();
			
			Game lastGame = games.last();
			milestoneGames.add(lastGame);
			
			SortedSet<Game> previousGames = new TreeSet<Game>();
			previousGames.addAll(games);
			previousGames.remove(lastGame);
			
			
			if (!previousGames.isEmpty()) {
				milestoneGames.add(previousGames.last()); 
			}
			
			manager.setLeagueMilestonePredicate(new GamesLeagueMilestonePredicate(milestoneGames));
			manager.setGames(games);
			manager.setCurrentDate(new Date());
			SortedMap<Game, League> leagues = manager.generateLeagues();
			League league = leagues.get(lastGame); 
			setLeague(league);
		}
		return SUCCESS;
	}

	public League getLeague() {
		return i_league;
	}

	public void setLeague(League league) {
		i_league = league;
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

	public boolean isShowLeague() {
		return true;
	}
}
