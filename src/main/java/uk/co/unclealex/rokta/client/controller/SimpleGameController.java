package uk.co.unclealex.rokta.client.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import uk.co.unclealex.rokta.client.views.Game;
import uk.co.unclealex.rokta.client.views.Hand;
import uk.co.unclealex.rokta.client.views.Game.State;

public class SimpleGameController implements GameController {

	private Game i_game;
	private GameInitialiser i_gameInitialiser;
	private GameSubmitter i_gameSubmitter;
	
	public SimpleGameController(Game game, GameInitialiser gameInitialiser, GameSubmitter gameSubmitter) {
		super();
		i_game = game;
		i_gameInitialiser = gameInitialiser;
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.pub.controller.GameController#initialise()
	 */
	public void initialise(Date date) {
		getGameInitialiser().initialiseGame(getGame(), date);
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.pub.controller.GameController#selectPlayers(java.util.SortedSet)
	 */
	public void selectPlayers(String instigator, SortedSet<String> players, Date date) {
		Game game = getGame();
		State gameState = game.getGameState();
		if (gameState != Game.State.CHOOSE_PLAYERS) {
			throw new IllegalStateException(
					"You cannot select the players in a game when the game is in the " + gameState + " state.");
		}
		if (!game.getAllBarExemptPlayers().containsAll(players)) {
			throw new IllegalArgumentException(players + " is not a valid set of players.");
		}
		game.setPlayers(new ArrayList<String>(players));
		game.setInstigator(instigator);
		game.setDatePlayed(date);
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.pub.controller.GameController#back()
	 */
	public void back() {
		Game game = getGame();
		State gameState = game.getGameState();
		if (gameState != State.PLAYING && gameState != State.FINISHED) {
			throw new IllegalStateException("You cannot go back when the game is in the " + gameState + " state.");
		}
		List<Map<String, Hand>> rounds = game.getRounds();
		if (rounds.isEmpty()) {
			game.setGameState(State.CHOOSE_PLAYERS);
		}
		else {
			Map<String, Hand> lastRound = rounds.remove(rounds.size() - 1);
			game.getPlayers().addAll(lastRound.keySet());
			game.setGameState(State.PLAYING);
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.pub.controller.GameController#addRound(java.util.Map)
	 */
	public void addRound(Map<String, Hand> round) {
		Game game = getGame();
		Set<String> playersForRound = round.keySet();
		List<String> players = game.getPlayers();
		if (!players.containsAll(playersForRound)) {
			throw new IllegalArgumentException(playersForRound + " is not a valid set of players.");
		}
		game.getRounds().add(round);
		// The only way people get knocked out of a round is if there are exactly 2 hands
		Collection<Hand> hands = round.values();
		if (hands.size() == 2) {
			Hand losingHand;
			Iterator<Hand> handIterator = hands.iterator();
			Hand firstHand = handIterator.next();
			Hand secondHand = handIterator.next();
			losingHand = firstHand.beats(secondHand)?secondHand:firstHand;
			for (Map.Entry<String, Hand> entry : round.entrySet()) {
				if (entry.getValue() == losingHand) {
					players.remove(entry.getKey());
				}
			}
			if (players.size() == 1) {
				game.setGameState(State.FINISHED);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.pub.controller.GameController#submitGame()
	 */
	public void submitGame() {
		Game game = getGame();
		game.setGameState(State.SUBMITTING);
		getGameSubmitter().submitGame(game);
		game.setGameState(State.SUBMITTED);
	}
	
	public Game getGame() {
		return i_game;
	}

	protected GameInitialiser getGameInitialiser() {
		return i_gameInitialiser;
	}

	protected GameSubmitter getGameSubmitter() {
		return i_gameSubmitter;
	}

	protected void setGameSubmitter(GameSubmitter gameSubmitter) {
		i_gameSubmitter = gameSubmitter;
	}
	
}
