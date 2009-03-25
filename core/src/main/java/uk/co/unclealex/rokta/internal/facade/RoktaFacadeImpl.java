package uk.co.unclealex.rokta.internal.facade;

import java.awt.Dimension;
import java.io.Writer;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.joda.time.DateTime;

import uk.co.unclealex.rokta.internal.dao.PersonDao;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.internal.process.LeagueService;
import uk.co.unclealex.rokta.internal.process.SecurityService;
import uk.co.unclealex.rokta.internal.process.StatisticsService;
import uk.co.unclealex.rokta.internal.process.dataset.ChartService;
import uk.co.unclealex.rokta.pub.facade.RoktaFacade;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.Streak;

public class RoktaFacadeImpl implements RoktaFacade {

	private ChartService i_chartService;
	private LeagueService i_leagueService;
	private StatisticsService i_statisticsService;
	private SecurityService i_securityService;
	
	private PersonDao i_personDao;
	
	@Override
	public boolean logIn(String username, String password) {
		return getSecurityService().logIn(username, password);
	}
	
	@Override
	public String getCurrentlyLoggedInUser() {
		return getSecurityService().getCurrentlyLoggedInUser();
	}
	
	@Override
	public void logOut() {
		getSecurityService().logOut();
	}
	
	@Override
	public void drawHandDistributionGraph(GameFilter gameFilter, String personName, Dimension size, Writer writer) {
		getChartService().drawHandDistributionGraph(gameFilter, whois(personName), size, writer);
	}

	@Override
	public void drawOpeningHandDistributionGraph(GameFilter gameFilter, String personName, Dimension size, Writer writer) {
		getChartService().drawOpeningHandDistributionGraph(gameFilter, whois(personName), size, writer);
	}

	@Override
	public void drawLeagueGraph(GameFilter gameFilter, Dimension size, int maximumColumns, Writer writer) {
		getChartService().drawLeagueGraph(gameFilter, size, maximumColumns, writer);
	}

	@Override
	public SortedSet<String> getAllUsersNames() {
		SortedSet<String> usersnames = new TreeSet<String>();
		Transformer<Person, String> transformer = new Transformer<Person, String>() {
			@Override
			public String transform(Person person) {
				return person.getName();
			}
		};
		CollectionUtils.collect(getPersonDao().getAll(), transformer, usersnames);
		return usersnames;
	}

	@Override
	public SortedSet<Streak> getCurrentLosingStreaks(GameFilter gameFilter) {
		return getStatisticsService().getCurrentLosingStreaks(gameFilter);
	}

	@Override
	public SortedSet<Streak> getCurrentWinningStreaks(GameFilter gameFilter) {
		return getStatisticsService().getCurrentWinningStreaks(gameFilter);
	}

	@Override
	public SortedSet<Streak> getLosingStreaks(GameFilter gameFilter, int targetSize) {
		return getStatisticsService().getLosingStreaks(gameFilter, targetSize);
	}

	@Override
	public SortedSet<Streak> getWinningStreaks(GameFilter gameFilter, int targetSize) {
		return getStatisticsService().getWinningStreaks(gameFilter, targetSize);
	}

	@Override
	public SortedSet<Streak> getLosingStreaksForPerson(GameFilter gameFilter, String personName, int targetSize) {
		return getStatisticsService().getLosingStreaks(gameFilter, personName, targetSize);
	}

	@Override
	public SortedSet<Streak> getWinningStreaksForPerson(GameFilter gameFilter, String personName, int targetSize) {
		return getStatisticsService().getWinningStreaks(gameFilter, personName, targetSize);
	}

	@Override
	public League getLeague(GameFilter gameFilter, int maximumLeagues, DateTime now) {
		return getLeagueService().generateLeague(gameFilter, maximumLeagues, now);
	}

	protected Person whois(String personName) {
		return getPersonDao().getPersonByName(personName);
	}

	protected String nullSafePersonName(Person person) {
		return person==null?null:person.getName();
	}
	
	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

	public ChartService getChartService() {
		return i_chartService;
	}

	public void setChartService(ChartService chartService) {
		i_chartService = chartService;
	}

	public LeagueService getLeagueService() {
		return i_leagueService;
	}

	public void setLeagueService(LeagueService leagueService) {
		i_leagueService = leagueService;
	}

	public StatisticsService getStatisticsService() {
		return i_statisticsService;
	}

	public void setStatisticsService(StatisticsService statisticsService) {
		i_statisticsService = statisticsService;
	}
	public SecurityService getSecurityService() {
		return i_securityService;
	}
	public void setSecurityService(SecurityService securityService) {
		i_securityService = securityService;
	}

}
