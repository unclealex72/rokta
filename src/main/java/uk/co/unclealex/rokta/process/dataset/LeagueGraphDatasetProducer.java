/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.ColourDao;
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
	private ColourDao i_colourDao;
	
	private String i_averageColour;
	
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
		
		ColourCategoryDataset dataset = new ColourCategoryDataset();
		// We need to make sure that the results are added to the graph in person order
		SortedMap<Person, Map<String, Double>> resultsByPerson =
			new TreeMap<Person, Map<String, Double>>();
		Map<String, Double> averageResults = new LinkedHashMap<String, Double>();
		for (Map.Entry<Game, League> entry : leaguesByGame.entrySet()) {
			Game game = entry.getKey();
			League league = entry.getValue();
			String category = fmt.format(game.getDatePlayed());
			for (LeagueRow row : league.getRows()) {
				Person person = row.getPerson();
				if (resultsByPerson.get(person) == null) {
					resultsByPerson.put(person, new LinkedHashMap<String, Double>());
				}
				resultsByPerson.get(person).put(category, row.getLossesPerGame() * 100);
			}
			averageResults.put(category, 100d * league.getTotalGames() / league.getTotalPlayers());
		}
		// Now we can add in the correct order
		List<Colour> colours = new LinkedList<Colour>();
		for (Map.Entry<Person, Map<String, Double>> entry : resultsByPerson.entrySet()) {
			Person person = entry.getKey();
			colours.add(person.getColour());
			String name = person.getName();
			for (Map.Entry<String, Double> resultEntry : entry.getValue().entrySet()) {
				dataset.addValue(resultEntry.getValue(), name, resultEntry.getKey());
			}
		}
		Colour averageColour = getColourDao().getColourByName(getAverageColour());
		colours.add(averageColour);
		dataset.setColours(colours);
		for (Map.Entry<String, Double> entry : averageResults.entrySet()) {
			dataset.addValue(entry.getValue(), "Average", entry.getKey());
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

	/**
	 * @return the averageColour
	 */
	public String getAverageColour() {
		return i_averageColour;
	}

	/**
	 * @param averageColour the averageColour to set
	 */
	public void setAverageColour(String averageColour) {
		i_averageColour = averageColour;
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

}
