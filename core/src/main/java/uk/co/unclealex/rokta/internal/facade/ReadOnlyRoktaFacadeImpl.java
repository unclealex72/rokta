package uk.co.unclealex.rokta.internal.facade;

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

import uk.co.unclealex.rokta.internal.dao.ColourDao;
import uk.co.unclealex.rokta.internal.dao.PersonDao;
import uk.co.unclealex.rokta.internal.model.Colour;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.internal.process.DateService;
import uk.co.unclealex.rokta.internal.process.LeagueService;
import uk.co.unclealex.rokta.internal.process.SecurityService;
import uk.co.unclealex.rokta.internal.process.StatisticsService;
import uk.co.unclealex.rokta.internal.process.dataset.ChartService;
import uk.co.unclealex.rokta.internal.process.dataset.TitleFactory;
import uk.co.unclealex.rokta.pub.facade.ReadOnlyRoktaFacade;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.YearGameFilter;
import uk.co.unclealex.rokta.pub.views.ChartView;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.Streak;
import uk.co.unclealex.rokta.pub.views.StreaksLeague;

public class ReadOnlyRoktaFacadeImpl implements ReadOnlyRoktaFacade {

	private ChartService i_chartService;
	private LeagueService i_leagueService;
	private StatisticsService i_statisticsService;
	private SecurityService i_securityService;
	private TitleFactory i_titleFactory;
	private DateService i_dateService;
	
	private PersonDao i_personDao;
	private ColourDao i_colourDao; 

	@Override
	public GameFilter getDefaultGameFilter() {
		return new YearGameFilter(new Date());
	}
	
	@Override
	public String getTitle(GameFilter gameFilter, String prefix) {
		return getTitleFactory().createTitle(gameFilter, prefix);
	}
	
	@Override
	public String getCopyright() {
		return getTitleFactory().createCopyright();
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

	public TitleFactory getTitleFactory() {
		return i_titleFactory;
	}

	@Required
	public void setTitleFactory(TitleFactory titleFactory) {
		i_titleFactory = titleFactory;
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

}
