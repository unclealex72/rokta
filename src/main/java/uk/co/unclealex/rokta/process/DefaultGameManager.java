package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.exceptions.InvalidRoundException;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Play;
import uk.co.unclealex.rokta.model.Round;

public class DefaultGameManager implements GameManager {

	private Game i_game;
	private SortedSet<Person> i_participants;
	private int i_rounds;
	private boolean i_finished;
	private Person i_counter;
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.GameManager#startGame()
	 */
	public void startGame(Collection<Person> participants, Person instigator, Date date) {
		setParticipants(new TreeSet<Person>(participants));
		setRounds(0);
		setFinished(false);
		Game game = new Game();
		game.setDate(date);
		game.setInstigator(instigator);
		game.setRounds(new TreeSet<Round>());
		setGame(game);
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.GameManager#nextRound(java.util.Map, uk.co.unclealex.rokta.model.Person)
	 */
	public void nextRound(Map<Person,Hand> plays, Person counter, int roundPlayed) throws InvalidRoundException {
		if (roundPlayed != getRounds() + 1) {
			throw new IllegalStateException("The wrong round has been played. Got " + roundPlayed + " but expected " + (getRounds() + 1));
		}
		if (i_finished) {
			throw new IllegalStateException("You cannot play a round after the game has finished.");
		}
		// Validation: check the players are the same as the current participants.
		if (!plays.keySet().equals(getParticipants())) {
			// TODO Throw a nice message.
			throw new InvalidRoundException();			
		}
		
		Round round = new Round();
		round.setCounter(counter);
		round.setPlays(new HashSet<Play>());
		int rounds = getRounds() + 1;
		round.setRound(rounds);
		setRounds(rounds);
		
		for (Map.Entry<Person, Hand> entry: plays.entrySet()) {
			Play play = new Play();
			play.setPerson(entry.getKey());
			play.setHand(entry.getValue());
			round.getPlays().add(play);
		}
		getGame().getRounds().add(round);
		setParticipants(round.getLosers());
		setCounter(counter);
		setFinished(getGame().getLoser() != null);
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.GameManager#finishGame()
	 */
	public final void finishGame() throws IllegalStateException {
		if (!isFinished()) {
			throw new IllegalStateException("This game is not finished.");
		}
		finishGameInternal();
	}
	
	protected void finishGameInternal() {
		// Do nothing
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.GameManager#getGame()
	 */
	public Game getGame() {
		return i_game;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.GameManager#getParticipants()
	 */
	public SortedSet<Person> getParticipants() {
		return i_participants;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.GameManager#getRounds()
	 */
	public int getRounds() {
		return i_rounds;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.process.GameManager#isFinished()
	 */
	public boolean isFinished() {
		return i_finished;
	}

	public Person getCounter() {
		return i_counter;
	}

	protected void setCounter(Person counter) {
		i_counter = counter;
	}

	protected void setFinished(boolean finished) {
		i_finished = finished;
	}

	protected void setGame(Game game) {
		i_game = game;
	}

	protected void setParticipants(SortedSet<Person> participants) {
		i_participants = participants;
	}

	protected void setRounds(int rounds) {
		i_rounds = rounds;
	}
}
