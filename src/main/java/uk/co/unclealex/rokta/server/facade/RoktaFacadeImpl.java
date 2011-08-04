package uk.co.unclealex.rokta.server.facade;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import uk.co.unclealex.rokta.pub.facade.RoktaFacade;
import uk.co.unclealex.rokta.pub.views.Game;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.dao.PersonDao;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Play;
import uk.co.unclealex.rokta.server.model.Round;

public class RoktaFacadeImpl extends ReadOnlyRoktaFacadeImpl implements RoktaFacade {

	private GameDao i_gameDao;

	@Override
	public void submitGame(Game game) {
		uk.co.unclealex.rokta.server.model.Game internalGame = new uk.co.unclealex.rokta.server.model.Game();
		PersonDao personDao = getPersonDao();
		internalGame.setDatePlayed(game.getDatePlayed());
		internalGame.setInstigator(personDao.getPersonByName(game.getInstigator()));
		Map<String, Person> peopleByName = new TreeMap<String, Person>();
		for (Person person : personDao.getAll()) {
			peopleByName.put(person.getName(), person);
		}
		final List<String> counters = game.getCounters();
		SortedSet<Round> rounds = new TreeSet<Round>();
		for (ListIterator<Map<String, Hand>> iter = game.getRounds().listIterator(); iter.hasNext(); ) {
			final int index = iter.nextIndex();
			String counter = counters.get(index);
			Map<String, Hand> round = iter.next();
			Round internalRound = new Round();
			internalRound.setCounter(peopleByName.get(counter));
			Set<Play> plays = new HashSet<Play>();
			for (Map.Entry<String, Hand> entry : round.entrySet()) {
				Play play = new Play();
				play.setPerson(peopleByName.get(entry.getKey()));
				play.setHand(entry.getValue());
				plays.add(play);
			}
			internalRound.setPlays(plays);
			internalRound.setRound(index);
			rounds.add(internalRound);
		}
		internalGame.setRounds(rounds);
		getGameDao().store(internalGame);
	}

	protected GameDao getGameDao() {
		return i_gameDao;
	}

	protected void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

}
