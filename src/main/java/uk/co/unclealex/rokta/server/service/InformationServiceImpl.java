package uk.co.unclealex.rokta.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.dao.PlayDao;
import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.process.LeagueService;
import uk.co.unclealex.rokta.server.process.StatisticsService;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.GameSummary;
import uk.co.unclealex.rokta.shared.model.Hand;
import uk.co.unclealex.rokta.shared.model.HeadToHeads;
import uk.co.unclealex.rokta.shared.model.Leagues;
import uk.co.unclealex.rokta.shared.model.PlayerProfile;
import uk.co.unclealex.rokta.shared.model.Streaks;

import com.googlecode.ehcache.annotations.Cacheable;

@Transactional
public class InformationServiceImpl implements InformationService {

	private static final Logger logger = LoggerFactory.getLogger(InformationServiceImpl.class);
	
	private GameDao i_gameDao;
	private StatisticsService i_statisticsService;
	private LeagueService i_leagueService;
	private PlayDao i_playDao;
	private PersonDao i_personDao;
	
	@Override
	@Cacheable(cacheName=CacheService.CACHE_NAME)
	public Date getDateFirstGamePlayed() {
		return getGameDao().getDateFirstGamePlayed();
	}

	@Override
	@Cacheable(cacheName=CacheService.CACHE_NAME)
	public Date getDateLastGamePlayed() {
		return getGameDao().getDateLastGamePlayed();
	}

	@Override
	@Cacheable(cacheName=CacheService.CACHE_NAME)
	public GameSummary getLastGameSummary() {
		Game lastGame = getGameDao().getLastGame();
		return new GameSummary(lastGame.getLoser().getName(), lastGame.getDatePlayed());
	}
	
	@Override
	@Cacheable(cacheName=CacheService.CACHE_NAME)
	public CurrentInformation getCurrentInformation(GameFilter gameFilter, Day day, int targetStreakSize) {
		logger.info("Getting current information.");
		SortedSet<Game> games = getGameDao().getGamesByFilter(gameFilter);
		StatisticsService statisticsService = getStatisticsService();
		Streaks streaks = statisticsService.getStreaks(games, targetStreakSize);
		HeadToHeads headToHeads = statisticsService.getHeadToHeadResultsByPerson(games);
		Leagues leagues = getLeagueService().generateLeagues(games, day.asDate());
		Map<String, PlayerProfile> playerProfiles = createPlayerProfiles(gameFilter);
		return new CurrentInformation(leagues, streaks, headToHeads, playerProfiles);
	}

	protected Map<String, PlayerProfile> createPlayerProfiles(GameFilter gameFilter) {
		PlayDao playDao = getPlayDao();
		SortedMap<String, SortedMap<Hand, Long>> openingPlaysByPersonAndHand = playDao.countOpeningPlaysByPersonAndHand(gameFilter);
		SortedMap<String, SortedMap<Hand, Long>> playsByPersonAndHand = playDao.countPlaysByPersonAndHand(gameFilter);
		Map<String, PlayerProfile> playerProfiles = new HashMap<String, PlayerProfile>();
		for (Person person : getPersonDao().getPlayers()) {
			String name = person.getName();
			SortedMap<Hand, Long> openingPlaysByHand = openingPlaysByPersonAndHand.get(name);
			SortedMap<Hand, Long> playsByHand = playsByPersonAndHand.get(name);
			PlayerProfile playerProfile = 
					new PlayerProfile(playsByHand, openingPlaysByHand, person.getColour().getHtmlName());
			playerProfiles.put(name, playerProfile);
		}
		return playerProfiles;
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

	public PlayDao getPlayDao() {
		return i_playDao;
	}

	public void setPlayDao(PlayDao playDao) {
		i_playDao = playDao;
	}

	public PersonDao getPersonDao() {
		return i_personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		i_personDao = personDao;
	}

}
