package uk.co.unclealex.rokta.actions;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.actions.statistics.LeagueAction;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.process.GamesLeagueMilestonePredicate;
import uk.co.unclealex.rokta.process.LeagueManager;

public abstract class LeagueTableAction extends LeagueAction {

	private LeagueManager i_leagueManager;
	
	private League i_league;

	@Override
	public String executeInternal() throws Exception {
		LeagueManager manager = getLeagueManager();
		SortedSet<Game> games = getGames();
		
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

	public abstract SortedSet<Game> getGames() throws Exception;
	
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
}
