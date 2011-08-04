package uk.co.unclealex.rokta.server.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.springframework.beans.factory.annotation.Required;

import uk.co.unclealex.rokta.client.facade.ReadOnlyRoktaFacade;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.filter.YearGameFilter;
import uk.co.unclealex.rokta.client.views.ChartView;
import uk.co.unclealex.rokta.client.views.Hand;
import uk.co.unclealex.rokta.client.views.InitialDatesView;
import uk.co.unclealex.rokta.client.views.InitialPlayers;
import uk.co.unclealex.rokta.client.views.League;
import uk.co.unclealex.rokta.client.views.Streak;
import uk.co.unclealex.rokta.client.views.StreaksLeague;
import uk.co.unclealex.rokta.server.dao.ColourDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Colour;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.process.DateService;
import uk.co.unclealex.rokta.server.process.LeagueService;
import uk.co.unclealex.rokta.server.process.PersonService;
import uk.co.unclealex.rokta.server.process.SecurityService;
import uk.co.unclealex.rokta.server.process.StatisticsService;
import uk.co.unclealex.rokta.server.process.dataset.ChartService;
import uk.co.unclealex.rokta.server.process.dataset.GameFilterService;

public class ReadOnlyRoktaFacadeImpl implements ReadOnlyRoktaFacade {

	private ChartService i_chartService;
	private LeagueService i_leagueService;
	private StatisticsService i_statisticsService;
	private SecurityService i_securityService;
	private GameFilterService i_gameFilterService;
	private DateService i_dateService;
	private PersonService i_personService;
	
	private PersonDao i_personDao;
	private ColourDao i_colourDao; 

	@Override
	public GameFilter getDefaultGameFilter() {
		return new YearGameFilter(new Date());
	}
	
	@Override
	public String describeGameFilter(GameFilter gameFilter) {
		return getGameFilterService().describeGameFilter(gameFilter);
	}
	
	@Override
	public InitialPlayers getInitialPlayers(Date date) {
		Person exemptPlayer = getPersonService().getExemptPlayer(date);
		List<String> playerNames = getAllPlayerNames();
		String exemptPlayerName;
		if (exemptPlayer == null) {
			exemptPlayerName = null;
		}
		else {
			exemptPlayerName = exemptPlayer.getName();
			playerNames.remove(exemptPlayerName);
		}
		return new InitialPlayers(getAllUsersNames(), playerNames, exemptPlayerName);
	}

	@Override
	public List<String> getAllPlayerNames() {
		return getNames(getPersonDao().getPlayers());
	}

	@Override
	public List<String> getAllUsersNames() {
		return getNames(getPersonDao().getAll());
	}
	
	protected List<String> getNames(Collection<Person> people) {
		SortedSet<String> usersnames = new TreeSet<String>();
		Transformer<Person, String> transformer = new Transformer<Person, String>() {
			@Override
			public String transform(Person person) {
				return person.getName();
			}
		};
		CollectionUtils.collect(people, transformer, usersnames);
		return new ArrayList<String>(usersnames);
	}

	@Override
	public StreaksLeague getCurrentLosingStreaks(GameFilter gameFilter) {
		return asStreakList(getStatisticsService().getCurrentLosingStreaks(gameFilter), true);
	}

	@Override
	public StreaksLeague getCurrentWinningStreaks(GameFilter gameFilter) {
		return asStreakList(getStatisticsService().getCurrentWinningStreaks(gameFilter), true);
	}

	@Override
	public StreaksLeague getLosingStreaks(GameFilter gameFilter, int targetSize) {
		return asStreakList(getStatisticsService().getLosingStreaks(gameFilter, targetSize), false);
	}

	@Override
	public StreaksLeague getWinningStreaks(GameFilter gameFilter, int targetSize) {
		return asStreakList(getStatisticsService().getWinningStreaks(gameFilter, targetSize), false);
	}

	@Override
	public StreaksLeague getLosingStreaksForPerson(GameFilter gameFilter, String personName, int targetSize) {
		return asStreakList(getStatisticsService().getLosingStreaks(gameFilter, personName, targetSize), false);
	}

	@Override
	public StreaksLeague getWinningStreaksForPerson(GameFilter gameFilter, String personName, int targetSize) {
		return asStreakList(getStatisticsService().getWinningStreaks(gameFilter, personName, targetSize), false);
	}

	protected StreaksLeague asStreakList(Collection<Streak> streaks, boolean current) {
		return new StreaksLeague(new ArrayList<Streak>(streaks), current);
	}
	
	@Override
	public Map<Hand, Integer> createHandDistributionGraph(GameFilter gameFilter, String personName) {
		return new HashMap<Hand, Integer>(getChartService().createHandDistributionGraph(gameFilter, whois(personName)));
	}
	
	@Override
	public Map<Hand, Integer> createOpeningHandDistributionGraph(GameFilter gameFilter, String personName) {
		return new HashMap<Hand, Integer>(getChartService().createOpeningHandDistributionGraph(gameFilter, whois(personName)));
	}
	
	@Override
	public ChartView<Double> createLeagueChart(GameFilter gameFilter, String averageColourHtmlName, int maximumColumns) {
		return getChartService().createLeagueChart(gameFilter, colourOf(averageColourHtmlName), maximumColumns);
	}
	
	@Override
	public League getLeague(GameFilter gameFilter, Date now) {
		return getLeagueService().generateLeague(gameFilter, now);
	}

	@Override
	public InitialDatesView getInitialDates() {
		return getDateService().getInitialDates();
	}
	
	protected Person whois(String personName) {
		Person person = getPersonDao().getPersonByName(personName);
		if (person == null) {
			throw new IllegalArgumentException(personName + " is not a valid name.");
		}
		return person;
	}

	protected Colour colourOf(String htmlName) {
		Colour colour = getColourDao().getColourByHtmlName(htmlName);
		if (colour == null) {
			throw new IllegalArgumentException(htmlName + " is not a valid colour.");
		}
		return colour;
	}

	protected String nullSafePersonName(Person person) {
		return person==null?null:person.getName();
	}
	
	public PersonDao getPersonDao() {
		return i_personDao;
	}

	@Required
	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

	public ChartService getChartService() {
		return i_chartService;
	}

	@Required
	public void setChartService(ChartService chartService) {
		i_chartService = chartService;
	}

	public LeagueService getLeagueService() {
		return i_leagueService;
	}

	@Required
	public void setLeagueService(LeagueService leagueService) {
		i_leagueService = leagueService;
	}

	public StatisticsService getStatisticsService() {
		return i_statisticsService;
	}

	@Required
	public void setStatisticsService(StatisticsService statisticsService) {
		i_statisticsService = statisticsService;
	}
	
	public SecurityService getSecurityService() {
		return i_securityService;
	}
	
	@Required
	public void setSecurityService(SecurityService securityService) {
		i_securityService = securityService;
	}

	public GameFilterService getGameFilterService() {
		return i_gameFilterService;
	}

	@Required
	public void setGameFilterService(GameFilterService gameFilterService) {
		i_gameFilterService = gameFilterService;
	}

	public ColourDao getColourDao() {
		return i_colourDao;
	}

	@Required
	public void setColourDao(ColourDao colourDao) {
		i_colourDao = colourDao;
	}

	public DateService getDateService() {
		return i_dateService;
	}

	@Required
	public void setDateService(DateService dateService) {
		i_dateService = dateService;
	}

	protected PersonService getPersonService() {
		return i_personService;
	}

	protected void setPersonService(PersonService personService) {
		i_personService = personService;
	}
}
