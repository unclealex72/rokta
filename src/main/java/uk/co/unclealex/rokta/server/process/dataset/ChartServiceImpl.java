package uk.co.unclealex.rokta.server.process.dataset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.views.ChartEntryView;
import uk.co.unclealex.rokta.client.views.ChartView;
import uk.co.unclealex.rokta.client.views.ColourView;
import uk.co.unclealex.rokta.client.views.Hand;
import uk.co.unclealex.rokta.client.views.League;
import uk.co.unclealex.rokta.client.views.LeagueRow;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Colour;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.process.LeagueService;
import uk.co.unclealex.rokta.server.process.LeaguesHolder;
import uk.co.unclealex.rokta.server.process.ProfileManager;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {

	private LeagueService i_leagueService;
	private PersonDao i_personDao;
	private GameDescriptorFactory i_gameDescriptorFactory;
	private ProfileManager i_profileManager;
	private ResourceBundle i_resourceBundle;
	private Transformer<Colour, ColourView> i_colourViewTransformer;
	
	@Override
	public SortedMap<Hand, Integer> createHandDistributionGraph(GameFilter gameFilter, Person person) {
		return getProfileManager().countHands(gameFilter, person);
	}
	
	@Override
	public SortedMap<Hand, Integer> createOpeningHandDistributionGraph(GameFilter gameFilter, Person person) {
		return getProfileManager().countOpeningGambits(gameFilter, person);
	}
	
	@Override
	public ChartView<Double> createLeagueChart(GameFilter gameFilter, Colour averageColour, int minimumColumns) {
		LeaguesHolder leaguesHolder = getLeagueService().generateLeagues(gameFilter, minimumColumns, new Date());
		int initialArrayCapacity = leaguesHolder.getLeaguesByGame().size();
		
		List<String> labels = new ArrayList<String>(initialArrayCapacity);
		SortedMap<String, List<Double>> valuesByPersonName = new TreeMap<String, List<Double>>();
		List<Double> averages = new ArrayList<Double>(initialArrayCapacity);
		Set<String> playerNames = new HashSet<String>();
		Transformer<LeagueRow, String> leagueRowToPersonNameTransformer = new Transformer<LeagueRow, String>() {
			@Override
			public String transform(LeagueRow leagueRow) {
				return leagueRow.getPersonName();
			}
		};

		// First pass: make sure we have all the player names for all leagues.
		for (League league : leaguesHolder.getLeaguesByGame().values()) {
			CollectionUtils.collect(league.getRows(), leagueRowToPersonNameTransformer, playerNames);
		}
		for (String playerName : playerNames) {
			valuesByPersonName.put(playerName, new ArrayList<Double>(initialArrayCapacity));
		}
		
		// Second pass: collate the results.
		Transformer<Game, String> gameDescriptor = 
			getGameDescriptorFactory().createGameDescriptor(leaguesHolder.getDatePlayedQuotientTransformer()); 
		Double minimumValue = null;
		Double maximumValue = null;
		for (Map.Entry<Game, League> entry : leaguesHolder.getLeaguesByGame().entrySet()) {
			Game game = entry.getKey();
			League league = entry.getValue();
			averages.add(100d * league.getExpectedLossesPerGame());
			Map<String, Double> currentValuesByPersonName = new HashMap<String, Double>();
			for (LeagueRow leagueRow : league.getRows()) {
				double value = leagueRow.getLossesPerGame() * 100d;
				currentValuesByPersonName.put(leagueRow.getPersonName(), value);
				minimumValue = minimumValue==null?value:Math.min(minimumValue, value);
				maximumValue = maximumValue==null?value:Math.max(maximumValue, value);
			}
			for (String playerName : playerNames) {
				valuesByPersonName.get(playerName).add(currentValuesByPersonName.get(playerName));
			}
			labels.add(gameDescriptor.transform(game));
		}
		
		// Finally, transform player names into labels and colours
		List<ChartEntryView<Double>> entries = new ArrayList<ChartEntryView<Double>>(); 
		Transformer<Colour, ColourView> colourViewTransformer = getColourViewTransformer();
		for (Map.Entry<String, List<Double>> entry : valuesByPersonName.entrySet()) {
			String name = entry.getKey();
			List<Double> values = entry.getValue();
			Person person = findPerson(name);
			entries.add(new ChartEntryView<Double>(name, colourViewTransformer.transform(person.getColour()), values));
		}
		entries.add(
				new ChartEntryView<Double>(
						getResourceBundle().getString("chart.average"), colourViewTransformer.transform(averageColour), averages));
		return new ChartView<Double>(entries, labels, maximumValue, minimumValue);
	}

	protected Person findPerson(String personName) {
		Person person = getPersonDao().getPersonByName(personName);
		if (person == null) {
			throw new IllegalArgumentException(personName + " does not exist.");
		}
		return person;
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

	public Transformer<Colour, ColourView> getColourViewTransformer() {
		return i_colourViewTransformer;
	}

	@Required
	public void setColourViewTransformer(Transformer<Colour, ColourView> colourViewTransformer) {
		i_colourViewTransformer = colourViewTransformer;
	}

}
