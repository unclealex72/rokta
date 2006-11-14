/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections15.Transformer;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.League;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.ColourDao;
import de.laures.cewolf.DatasetProduceException;

/**
 * @author alex
 * 
 */
public class LeagueGraphDatasetProducerImpl implements LeagueGraphDatasetProducer {

	private SortedMap<Game, League> i_leaguesByGame;
	private ColourDao i_colourDao;
	private String i_averageColour;
	private Transformer<Game, String> i_gameDescriptor;

	/**
	 * Produces some random data.
	 */
	public Object produceDataset(Map params) throws DatasetProduceException {			
		ColourCategoryDataset dataset = new ColourCategoryDataset();
		// We need to make sure that the results are added to the graph in person order
		SortedMap<Person, Map<String, Double>> resultsByPerson =
			new TreeMap<Person, Map<String, Double>>();
		Map<String, Double> averageResults = new LinkedHashMap<String, Double>();
		for (Map.Entry<Game, League> entry : getLeaguesByGame().entrySet()) {
			League league = entry.getValue();
			String category = getGameDescriptor().transform(entry.getKey());
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

	/* (non-Javadoc)
	 * @see de.laures.cewolf.DatasetProducer#getProducerId()
	 */
	public String getProducerId() {
		return getClass().getName();
	}

	/* (non-Javadoc)
	 * @see de.laures.cewolf.DatasetProducer#hasExpired(java.util.Map, java.util.Date)
	 */
	public boolean hasExpired(Map params, Date since) {
		return true;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.dataset.LeagueGraphDatasetProducer#getLeaguesByGame()
	 */
	public SortedMap<Game, League> getLeaguesByGame() {
		return i_leaguesByGame;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.dataset.LeagueGraphDatasetProducer#setLeaguesByGame(java.util.SortedMap)
	 */
	public void setLeaguesByGame(SortedMap<Game, League> leaguesByGame) {
		i_leaguesByGame = leaguesByGame;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.dataset.LeagueGraphDatasetProducer#getGameDescriptor()
	 */
	public Transformer<Game, String> getGameDescriptor() {
		return i_gameDescriptor;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.dataset.LeagueGraphDatasetProducer#setGameDescriptor(org.apache.commons.collections15.Transformer)
	 */
	public void setGameDescriptor(Transformer<Game, String> gameDescriptor) {
		i_gameDescriptor = gameDescriptor;
	}

}
