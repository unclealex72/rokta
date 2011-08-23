package uk.co.unclealex.rokta.server.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.process.LeagueService;
import uk.co.unclealex.rokta.server.process.StatisticsService;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.HeadToHeads;
import uk.co.unclealex.rokta.shared.model.Leagues;
import uk.co.unclealex.rokta.shared.model.Streaks;

@Transactional
public class InformationServiceImpl implements InformationService {

	private GameDao i_gameDao;
	private StatisticsService i_statisticsService;
	private LeagueService i_leagueService;
	
	@Override
	public Date getDateFirstGamePlayed() {
		return getGameDao().getDateFirstGamePlayed();
	}

	@Override
	public Date getDateLastGamePlayed() {
		return getGameDao().getDateLastGamePlayed();
	}

	@Override
	public CurrentInformation getCurrentInformation(GameFilter gameFilter, int currentYear, int currentMonth,
			int currentDay, int targetStreakSize) {
		Calendar cal = new GregorianCalendar(currentYear, currentMonth, currentDay);
		SortedSet<Game> games = getGameDao().getGamesByFilter(gameFilter);
		StatisticsService statisticsService = getStatisticsService();
		Streaks streaks = statisticsService.getStreaks(games, targetStreakSize);
		HeadToHeads headToHeads = statisticsService.getHeadToHeadResultsByPerson(games);
		Leagues leagues = getLeagueService().generateLeagues(games, cal.getTime());
		return new CurrentInformation(leagues, streaks, headToHeads);
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

	public StatisticsService getStatisticsService() {
		return i_statisticsService;
	}

	public void setStatisticsService(StatisticsService statisticsService) {
		i_statisticsService = statisticsService;
	}

	public LeagueService getLeagueService() {
		return i_leagueService;
	}

	public void setLeagueService(LeagueService leagueService) {
		i_leagueService = leagueService;
	}

}
