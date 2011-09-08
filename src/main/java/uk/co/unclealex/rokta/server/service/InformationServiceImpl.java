package uk.co.unclealex.rokta.server.service;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import uk.co.unclealex.rokta.server.model.Play;
import uk.co.unclealex.rokta.server.model.Round;
import uk.co.unclealex.rokta.server.process.LeagueService;
import uk.co.unclealex.rokta.server.process.StatisticsService;
import uk.co.unclealex.rokta.server.util.DateUtil;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.DatedGame;
import uk.co.unclealex.rokta.shared.model.GameSummary;
import uk.co.unclealex.rokta.shared.model.Hand;
import uk.co.unclealex.rokta.shared.model.HeadToHeads;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.Leagues;
import uk.co.unclealex.rokta.shared.model.News;
import uk.co.unclealex.rokta.shared.model.PlayerProfile;
import uk.co.unclealex.rokta.shared.model.Streaks;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.ehcache.annotations.Cacheable;

@Transactional
public class InformationServiceImpl implements InformationService {

	private static final Logger logger = LoggerFactory.getLogger(InformationServiceImpl.class);
	
	private GameDao i_gameDao;
	private StatisticsService i_statisticsService;
	private LeagueService i_leagueService;
	private PlayDao i_playDao;
	private PersonDao i_personDao;
	private DateUtil i_dateUtil;
	
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
		Date date = day.asDate();
		Leagues leagues = getLeagueService().generateLeagues(games, date);
		Map<String, PlayerProfile> playerProfiles = createPlayerProfiles(gameFilter);
		News news = createNews(games, streaks, leagues, date);
		return new CurrentInformation(
				leagues, streaks.getWinningStreaks(), streaks.getLosingStreaks(), headToHeads, playerProfiles, news);
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
					new PlayerProfile(playsByHand, openingPlaysByHand, person.getGraphingColour());
			playerProfiles.put(name, playerProfile);
		}
		return playerProfiles;
	}

	protected News createNews(SortedSet<Game> games, Streaks streaks, Leagues leagues, final Date date) {
		Game lastGame = lastOfOrNull(games);
		League lastLeague = firstOfOrNull(leagues.getLeagues());
		final DateUtil dateUtil = getDateUtil();
		Predicate<Date> isSameDayPredicate = new Predicate<Date>() {
			@Override
			public boolean apply(Date input) {
				return dateUtil.areSameDay(date, input);
			}
		};
		Function<Game, Date> gameFunction = new Function<Game, Date>() {
			@Override
			public Date apply(Game game) {
				return game.getDatePlayed();
			}
		};
		Function<League, Date> leagueFunction = new Function<League, Date>() {
			@Override
			public Date apply(League league) {
				return league.getLastGameDate();
			}
		};
		Predicate<Game> gamePredicate = Predicates.compose(isSameDayPredicate, gameFunction);
		Predicate<League> leaguePredicate = Predicates.compose(isSameDayPredicate, leagueFunction);
		Function<Game, DatedGame> sharedGameFunction = 
				new Function<Game, DatedGame>() {
			@Override
			public DatedGame apply(Game game) {
				return game==null?null:asSharedGame(game);
			}
		};
		return new News(
			sharedGameFunction.apply(lastGame), lastLeague,
			Sets.newTreeSet(Iterables.transform(Iterables.filter(games, gamePredicate), sharedGameFunction)),
			Sets.newTreeSet(Iterables.filter(leagues.getLeagues(), leaguePredicate)),
			streaks.getCurrentWinningStreaks(), streaks.getCurrentLosingStreaks());
	}
	
	protected <C> C lastOfOrNull(SortedSet<C> comparables) {
		return comparables.isEmpty()?null:comparables.last();
	}
	
	protected <C> C firstOfOrNull(SortedSet<C> comparables) {
		return comparables.isEmpty()?null:comparables.first();
	}
	
	protected DatedGame asSharedGame(Game game) {
		final Function<Person, String> nameFunction = new Function<Person, String>() {
			@Override
			public String apply(Person person) {
				return person.getName();
			}
		};
		String instigator = nameFunction.apply(game.getInstigator());
		SortedSet<String> players = Sets.newTreeSet(Iterables.transform(game.getParticipants(), nameFunction));
		final Comparator<Play> playComparator = new Comparator<Play>() {
			@Override
			public int compare(Play p1, Play p2) {
				return nameFunction.apply(p1.getPerson()).compareTo(nameFunction.apply(p2.getPerson()));
			}
		};
		final Function<Play, Hand> handFunction = new Function<Play, Hand>() {
			@Override
			public Hand apply(Play play) {
				return play.getHand();
			}
		};
		Function<Round, DatedGame.Round> roundFunction = 
				new Function<Round, DatedGame.Round>() {
			@Override
			public DatedGame.Round apply(Round round) {
				SortedSet<Play> plays = Sets.newTreeSet(playComparator);
				plays.addAll(round.getPlays());
				List<Hand> hands = Lists.newArrayList(Iterables.transform(plays, handFunction));
				return new DatedGame.Round(nameFunction.apply(round.getCounter()), hands);
			}
		};
		List<DatedGame.Round> rounds = 
				Lists.newArrayList(Iterables.transform(game.getRounds(), roundFunction));
		return new DatedGame(instigator, players, rounds, game.getDatePlayed());
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

	public DateUtil getDateUtil() {
		return i_dateUtil;
	}

	public void setDateUtil(DateUtil dateUtil) {
		i_dateUtil = dateUtil;
	}

}
