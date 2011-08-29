package uk.co.unclealex.rokta.server.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Play;
import uk.co.unclealex.rokta.server.model.Round;
import uk.co.unclealex.rokta.server.process.PersonService;
import uk.co.unclealex.rokta.server.util.DateUtil;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.Hand;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.googlecode.ehcache.annotations.Cacheable;

@Transactional
public class NewGameServiceImpl implements NewGameService {

	private CacheService i_cacheService;
	private GameDao i_gameDao;
	private PersonDao i_personDao;
	private DateUtil i_dateUtil;
	private PersonService i_personService;
	
	@Override
	@Cacheable(cacheName=CacheService.CACHE_NAME)
	public InitialPlayers getInitialPlayers(Day day) {
		Person exemptPlayer = getPersonService().getExemptPlayer(day.asDate());
		SortedSet<String> currentPlayerNames = getPersonDao().getAllPlayerNames();
		SortedSet<String> users = getPersonService().getAllUsernames();
		return new InitialPlayers(
				Lists.newArrayList(users), 
						Lists.newArrayList(currentPlayerNames), exemptPlayer==null?null:exemptPlayer.getName());
	}

	@Override
	public void submitGame(Game game) {
		Map<String, Person> peopleByName = getPeopleByName();
		uk.co.unclealex.rokta.server.model.Game newGame = new uk.co.unclealex.rokta.server.model.Game();
		Calendar cal = new GregorianCalendar();
		newGame.setDatePlayed(cal.getTime());
		newGame.setYearPlayed(cal.get(Calendar.YEAR));
		newGame.setMonthPlayed(cal.get(Calendar.MONTH));
		newGame.setDayPlayed(cal.get(Calendar.DAY_OF_MONTH));
		newGame.setWeekPlayed(cal.get(Calendar.WEEK_OF_YEAR));
		newGame.setInstigator(peopleByName.get(game.getInstigator()));
		SortedSet<Round> newRounds = Sets.newTreeSet();
		newGame.setRounds(newRounds);
		List<Game.Round> rounds = game.getRounds();
		int roundCount = rounds.size();
		for (int idx = 0; idx < roundCount; idx++) {
			Game.Round round = rounds.get(idx);
			Round newRound = new Round();
			newRound.setRound(idx + 1);
			newRound.setCounter(peopleByName.get(round.getCounter()));
			Set<Play> newPlays = Sets.newHashSet();
			newRound.setPlays(newPlays);
			SortedSet<String> currentPlayers = game.getCurrentPlayers(idx);
			Iterator<String> playerIter = currentPlayers.iterator();
			for (Hand hand : round.getPlays()) {
				Play play = new Play();
				play.setHand(hand);
				play.setPerson(peopleByName.get(playerIter.next()));
				newPlays.add(play);
			}
			newRounds.add(newRound);
		}
		getGameDao().store(newGame);
		getCacheService().clearCache();
	}
	
	protected Map<String, Person> getPeopleByName() {
		Function<Person, String> keyFunction = new Function<Person, String>() {
			@Override
			public String apply(Person person) {
				return person.getName();
			}
		};
		return Maps.uniqueIndex(getPersonDao().getAll(), keyFunction);
	}

	@Override
	public void removeLastGame() {
		GameDao gameDao = getGameDao();
		gameDao.remove(gameDao.getLastGame());
		getCacheService().clearCache();
	}
	
	public CacheService getCacheService() {
		return i_cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		i_cacheService = cacheService;
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
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

	public PersonService getPersonService() {
		return i_personService;
	}

	public void setPersonService(PersonService personService) {
		i_personService = personService;
	}

}
