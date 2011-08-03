package uk.co.unclealex.rokta.internal.process;

import java.util.SortedMap;

import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.quotient.DatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.pub.views.League;

public class LeaguesHolder {

	private SortedMap<Game, League> i_leaguesByGame;
	private DatePlayedQuotientTransformer i_datePlayedQuotientTransformer;
	
	public LeaguesHolder() {
	}
	
	public LeaguesHolder(DatePlayedQuotientTransformer datePlayedQuotientTransformer, SortedMap<Game, League> leagueByGame) {
		super();
		i_datePlayedQuotientTransformer = datePlayedQuotientTransformer;
		i_leaguesByGame = leagueByGame;
	}

	public SortedMap<Game, League> getLeaguesByGame() {
		return i_leaguesByGame;
	}
	
	public void setLeaguesByGame(SortedMap<Game, League> leaguesByGame) {
		i_leaguesByGame = leaguesByGame;
	}
	
	public DatePlayedQuotientTransformer getDatePlayedQuotientTransformer() {
		return i_datePlayedQuotientTransformer;
	}
	
	public void setDatePlayedQuotientTransformer(DatePlayedQuotientTransformer datePlayedQuotientTransformer) {
		i_datePlayedQuotientTransformer = datePlayedQuotientTransformer;
	}
	
}
