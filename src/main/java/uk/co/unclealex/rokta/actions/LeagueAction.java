package uk.co.unclealex.rokta.actions;

import java.util.Collection;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.model.dao.GameDAO;
import uk.co.unclealex.rokta.process.LeagueManager;

import com.opensymphony.xwork.ActionSupport;

public class LeagueAction extends ActionSupport {

	private GameDAO i_gameDAO;
	private SortedSet<LeagueRow> i_league;
	
	@Override
	public String execute() {
		LeagueManager manager = new LeagueManager();
		Collection<Game> games = getGameDAO().getAllGames();
		SortedSet<LeagueRow> league = manager.generateLeague(games, manager.getCompareByLossesPerGame());
		setLeague(league);
		return SUCCESS;
	}

	public GameDAO getGameDAO() {
		return i_gameDAO;
	}

	public void setGameDAO(GameDAO gameDAO) {
		i_gameDAO = gameDAO;
	}

	public SortedSet<LeagueRow> getLeague() {
		return i_league;
	}

	public void setLeague(SortedSet<LeagueRow> league) {
		i_league = league;
	}
}
