/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import org.jfree.data.category.DefaultCategoryDataset;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.process.LeagueManager;
import uk.co.unclealex.rokta.process.LeagueMilestonePredicate;
import de.laures.cewolf.DatasetProduceException;

/**
 * @author alex
 * 
 */
public class LeagueGraphDatasetProducer extends StatisticsAwareDatasetProducer {

	private LeagueManager i_leagueManager;
	private LeagueMilestonePredicate i_leagueMilestonePredicate;
	
	/**
	 * Produces some random data.
	 */
	public Object produceDataset(Map params) throws DatasetProduceException {
	
		
		LeagueManager manager = getLeagueManager();
		manager.setCurrentDate(new Date());
		manager.setGames(getInformationProvider().getGames());
		manager.setLeagueMilestonePredicate(getLeagueMilestonePredicate());
		
		SortedMap<Game, League> leaguesByGame = manager.generateLeagues();
		DateFormat fmt = new SimpleDateFormat("dd/MM/yy");
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Map.Entry<Game, League> entry : leaguesByGame.entrySet()) {
			Game game = entry.getKey();
			League league = entry.getValue();
			String category = fmt.format(game.getDatePlayed());
			for (LeagueRow row : league.getRows()) {
				dataset.addValue(row.getLossesPerGame() * 100, row.getPerson().getName(), category);
			}
			dataset.addValue(100d * league.getTotalGames() / league.getTotalPlayers(), "Average", category);
		}
		return dataset;
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

	/**
	 * @return the leagueMilestonePredicate
	 */
	public LeagueMilestonePredicate getLeagueMilestonePredicate() {
		return i_leagueMilestonePredicate;
	}

	/**
	 * @param leagueMilestonePredicate the leagueMilestonePredicate to set
	 */
	public void setLeagueMilestonePredicate(
			LeagueMilestonePredicate leagueMilestonePredicate) {
		i_leagueMilestonePredicate = leagueMilestonePredicate;
	}

}
