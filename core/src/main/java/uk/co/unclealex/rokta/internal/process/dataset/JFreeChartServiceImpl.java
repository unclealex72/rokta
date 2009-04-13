package uk.co.unclealex.rokta.internal.process.dataset;

import java.awt.Dimension;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections15.Transformer;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.internal.dao.ColourDao;
import uk.co.unclealex.rokta.internal.dao.PersonDao;
import uk.co.unclealex.rokta.internal.model.Colour;
import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.internal.process.LeagueService;
import uk.co.unclealex.rokta.internal.process.LeaguesHolder;
import uk.co.unclealex.rokta.internal.process.ProfileManager;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.LeagueRow;

@Service
@Transactional
public class JFreeChartServiceImpl implements JFreeChartService {

	private ColourDao i_colourDao;
	private LeagueService i_leagueService;
	private PersonDao i_personDao;
	private GameDescriptorFactory i_gameDescriptorFactory;
	private GenericChartFactory i_genericChartFactory;
	private TitleFactory i_titleFactory;
	private ProfileManager i_profileManager;
	private ResourceBundle i_resourceBundle;
	
	@Override
	public void drawLeagueGraph(GameFilter gameFilter, String averageColourHtmlName, int maximumColumns, Dimension size, Writer writer) throws IOException {
		PersonDao personDao = getPersonDao();
		LeaguesHolder leagueHolder = getLeagueService().generateLeagues(gameFilter, maximumColumns, new Date());
		Transformer<Game, String> gameDescriptor = 
			getGameDescriptorFactory().createGameDescriptor(leagueHolder.getDatePlayedQuotientTransformer()); 
		ColourCategoryDataset dataset = new ColourCategoryDataset();
		// We need to make sure that the results are added to the graph in person order
		SortedMap<Person, Map<String, Double>> resultsByPerson =
			new TreeMap<Person, Map<String, Double>>();
		Map<String, Double> averageResults = new LinkedHashMap<String, Double>();
		for (Map.Entry<Game, League> entry : leagueHolder.getLeaguesByGame().entrySet()) {
			League league = entry.getValue();
			String category = gameDescriptor.transform(entry.getKey());
			for (LeagueRow row : league.getRows()) {
				Person person = personDao.getPersonByName(row.getPersonName());
				if (resultsByPerson.get(person) == null) {
					resultsByPerson.put(person, new LinkedHashMap<String, Double>());
				}
				resultsByPerson.get(person).put(category, row.getLossesPerGame() * 100);
			}
			averageResults.put(category, 100d * league.getExpectedLossesPerGame());
		}
		// Now we can add in the correct order
		List<Colour> colours = new ArrayList<Colour>();
		
		// Add the average first so that every game is included in the first run and so keeps in the right order.
		Colour averageColour = getColourDao().getColourByHtmlName(averageColourHtmlName);
		colours.add(averageColour);
		dataset.setColours(colours);
		for (Map.Entry<String, Double> entry : averageResults.entrySet()) {
			dataset.addValue(entry.getValue(), getResourceBundle().getString("chart.average"), entry.getKey());
		}
		for (Map.Entry<Person, Map<String, Double>> entry : resultsByPerson.entrySet()) {
			Person person = entry.getKey();
			colours.add(person.getColour());
			String name = person.getName();
			for (Map.Entry<String, Double> resultEntry : entry.getValue().entrySet()) {
				dataset.addValue(resultEntry.getValue(), name, resultEntry.getKey());
			}
		}
		getGenericChartFactory().drawLineGraph(dataset, null, size, writer);
	}

	@Override
	public void drawOpeningHandDistributionGraph(GameFilter gameFilter, String personName, String rockHtmlColour, 
			String scissorsHtmlColour, String paperHtmlColour, String outlineHtmlColour, Dimension size, Writer writer) throws IOException {
		drawHandDistributionGraph(
				getProfileManager().countOpeningGambits(gameFilter, findPerson(personName)), 
				rockHtmlColour, scissorsHtmlColour, paperHtmlColour, outlineHtmlColour, size, writer);
	}

	@Override
	public void drawHandDistributionGraph(GameFilter gameFilter, String personName, String rockHtmlColour, 
			String scissorsHtmlColour, String paperHtmlColour, String outlineHtmlColour, Dimension size, Writer writer) throws IOException {
		drawHandDistributionGraph(
				getProfileManager().countHands(gameFilter, findPerson(personName)),
				rockHtmlColour, scissorsHtmlColour, paperHtmlColour, outlineHtmlColour, size, writer);
	}

	protected void drawHandDistributionGraph(
			SortedMap<Hand, Integer> handCounts, String rockHtmlColour, 
			String scissorsHtmlColour, String paperHtmlColour, String outlineHtmlColour, Dimension size, Writer writer) throws IOException {
		ColourDao colourDao = getColourDao();
		SortedMap<Comparable<?>, Colour> coloursByHand = new TreeMap<Comparable<?>, Colour>();
		coloursByHand.put(Hand.ROCK.getDescription(), colourDao.getColourByHtmlName(rockHtmlColour));
		coloursByHand.put(Hand.SCISSORS.getDescription(), colourDao.getColourByHtmlName(scissorsHtmlColour));
		coloursByHand.put(Hand.PAPER.getDescription(), colourDao.getColourByHtmlName(paperHtmlColour));
		DefaultPieDataset dataset = new DefaultPieDataset();
		int sum = 0;
		for (int count : handCounts.values()) {
			sum += count;
		}
		for (Map.Entry<Hand,Integer> entry : handCounts.entrySet()) {
			dataset.setValue(entry.getKey().getDescription(), 100.0 * entry.getValue() / (double) sum);
		}
		getGenericChartFactory().drawPieChart(
				coloursByHand, colourDao.getColourByHtmlName(outlineHtmlColour), dataset, size, writer);
	}
	
	protected Person findPerson(String personName) {
		Person person = getPersonDao().getPersonByName(personName);
		if (person == null) {
			throw new IllegalArgumentException(personName + " does not exist.");
		}
		return person;
	}
	
	public ColourDao getColourDao() {
		return i_colourDao;
	}

	@Required
	public void setColourDao(ColourDao colourDao) {
		i_colourDao = colourDao;
	}

	public LeagueService getLeagueService() {
		return i_leagueService;
	}

	@Required
	public void setLeagueService(LeagueService leagueService) {
		i_leagueService = leagueService;
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	@Required
	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

	public GameDescriptorFactory getGameDescriptorFactory() {
		return i_gameDescriptorFactory;
	}

	@Required
	public void setGameDescriptorFactory(GameDescriptorFactory gameDescriptorFactory) {
		i_gameDescriptorFactory = gameDescriptorFactory;
	}

	public GenericChartFactory getGenericChartFactory() {
		return i_genericChartFactory;
	}

	@Required
	public void setGenericChartFactory(GenericChartFactory genericChartFactory) {
		i_genericChartFactory = genericChartFactory;
	}

	public TitleFactory getTitleFactory() {
		return i_titleFactory;
	}

	@Required
	public void setTitleFactory(TitleFactory titleFactory) {
		i_titleFactory = titleFactory;
	}

	public ProfileManager getProfileManager() {
		return i_profileManager;
	}

	@Required
	public void setProfileManager(ProfileManager profileManager) {
		i_profileManager = profileManager;
	}

	public ResourceBundle getResourceBundle() {
		return i_resourceBundle;
	}

	@Required
	public void setResourceBundle(ResourceBundle resourceBundle) {
		i_resourceBundle = resourceBundle;
	}

}
