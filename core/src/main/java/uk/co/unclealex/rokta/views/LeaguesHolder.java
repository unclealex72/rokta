package uk.co.unclealex.rokta.views;

import java.util.SortedMap;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.quotient.DatePlayedQuotientTransformer;

public class LeaguesHolder {

	private SortedMap<Game, League> i_leagueByGame;
	private DatePlayedQuotientTransformer i_datePlayedQuotientTransformer;
	
	public LeaguesHolder() {
	}
	
	public LeaguesHolder(DatePlayedQuotientTransformer datePlayedQuotientTransformer, SortedMap<Game, League> leagueByGame) {
		super();
		i_datePlayedQuotientTransformer = datePlayedQuotientTransformer;
		i_leagueByGame = leagueByGame;
	}

	public SortedMap<Game, League> getLeagueByGame() {
		return i_leagueByGame;
	}
	
	public void setLeagueByGame(SortedMap<Game, League> leagueByGame) {
		i_leagueByGame = leagueByGame;
	}
	
	public DatePlayedQuotientTransformer getDatePlayedQuotientTransformer() {
		return i_datePlayedQuotientTransformer;
	}
	
	public void setDatePlayedQuotientTransformer(DatePlayedQuotientTransformer datePlayedQuotientTransformer) {
		i_datePlayedQuotientTransformer = datePlayedQuotientTransformer;
	}
	
}
