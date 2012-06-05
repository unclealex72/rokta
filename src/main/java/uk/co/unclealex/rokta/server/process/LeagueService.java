package uk.co.unclealex.rokta.server.process;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.shared.model.Leagues;

/**
 * The main service for generating a league of games.
 * @author alex
 *
 */
public interface LeagueService {

	/**
	 * Generate a league for a given set of {@link Game}s at a given time. We need to know when the league is being generated
	 * so that if the latest game is not the same as the date of generation then no-one will be exempt.
	 * @param games The games to include.
	 * @param now The time that the league is being generated.
	 * @return A {@link Leagues} object containing leagues for all games up to the latest game.
	 */
	public Leagues generateLeagues(SortedSet<Game> games, Date now);

}