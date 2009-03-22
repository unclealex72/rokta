/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.util.SortedMap;

import org.apache.commons.collections15.Transformer;

import de.laures.cewolf.DatasetProducer;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;

/**
 * @author alex
 *
 */
public interface LeagueGraphDatasetProducer extends DatasetProducer {

	/**
	 * @return the leaguesByGame
	 */
	public SortedMap<Game, League> getLeaguesByGame();

	/**
	 * @param leaguesByGame the leaguesByGame to set
	 */
	public void setLeaguesByGame(SortedMap<Game, League> leaguesByGame);

	/**
	 * @return the gameDescriptor
	 */
	public Transformer<Game, String> getGameDescriptor();

	/**
	 * @param gameDescriptor the gameDescriptor to set
	 */
	public void setGameDescriptor(Transformer<Game, String> gameDescriptor);

}