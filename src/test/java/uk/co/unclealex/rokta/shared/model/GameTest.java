package uk.co.unclealex.rokta.shared.model;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class GameTest {

	class PlayedRoundResult {
		
		private final List<Hand> i_playedHands;
		private final String i_counter;
		private final boolean i_expectedGameFinished;
		private final SortedSet<String> i_expectedRemainingPlayers;
		private final String i_expectedToken;
		
		public PlayedRoundResult(
				String counter, Hand[] playedHands,
				boolean expectedGameFinished, String expectedToken, String... expectedRemainingPlayers) {
			i_counter = counter;
			i_playedHands = Arrays.asList(playedHands);
			i_expectedGameFinished = expectedGameFinished;
			i_expectedToken = expectedToken;
			i_expectedRemainingPlayers = Sets.newTreeSet(Arrays.asList(expectedRemainingPlayers));
		}

		public boolean isExpectedGameFinished() {
			return i_expectedGameFinished;
		}

		public SortedSet<String> getExpectedRemainingPlayers() {
			return i_expectedRemainingPlayers;
		}

		public String getExpectedToken() {
			return i_expectedToken;
		}

		public List<Hand> getPlayedHands() {
			return i_playedHands;
		}

		public String getCounter() {
			return i_counter;
		}
	}
	@Test
	public void test() {
		Game game = new Game("Instigator", Sets.newTreeSet(Arrays.asList("Aaron", "Bob", "Carol", "Derek", "Elvis")));
		List<PlayedRoundResult> playedRoundResults = Lists.newArrayList();
		playedRoundResults.add(
				new PlayedRoundResult(
						"Bob", new Hand[] { Hand.ROCK, Hand.ROCK, Hand.ROCK, Hand.ROCK, Hand.ROCK }, false, 
						"Instigator:Aaron;Bob;Carol;Derek;Elvis:Bob,rrrrr",
						"Aaron", "Bob", "Carol", "Derek", "Elvis"));
		playedRoundResults.add(
				new PlayedRoundResult(
						"Bob", new Hand[] { Hand.ROCK, Hand.SCISSORS, Hand.ROCK, Hand.PAPER, Hand.ROCK }, false, 
						"Instigator:Aaron;Bob;Carol;Derek;Elvis:Bob,rrrrr;rsrpr",
						"Aaron", "Bob", "Carol", "Derek", "Elvis"));
		playedRoundResults.add(
				new PlayedRoundResult(
						"Aaron", new Hand[] { Hand.ROCK, Hand.SCISSORS, Hand.SCISSORS, Hand.SCISSORS, Hand.SCISSORS }, false, 
						"Instigator:Aaron;Bob;Carol;Derek;Elvis:Bob,rrrrr;rsrpr;Aaron,rssss",
						"Bob", "Carol", "Derek", "Elvis"));
		playedRoundResults.add(
				new PlayedRoundResult(
						"Aaron", new Hand[] { Hand.ROCK, Hand.PAPER, Hand.ROCK, Hand.ROCK }, false, 
						"Instigator:Aaron;Bob;Carol;Derek;Elvis:Bob,rrrrr;rsrpr;Aaron,rssss;rprr",
						"Bob", "Derek", "Elvis"));
		playedRoundResults.add(
				new PlayedRoundResult(
						"Elvis", new Hand[] { Hand.SCISSORS, Hand.SCISSORS, Hand.PAPER }, true, 
						"Instigator:Aaron;Bob;Carol;Derek;Elvis:Bob,rrrrr;rsrpr;Aaron,rssss;rprr;Elvis,ssp",
						"Elvis"));
		
		int round = 1;
		for (PlayedRoundResult playedRoundResult : playedRoundResults) {
			Game newGame = game.addRound(playedRoundResult.getCounter(), playedRoundResult.getPlayedHands());
			Assert.assertFalse("After round " + round + " the game and the previous game were equal.", newGame.equals(game));
			Assert.assertEquals("After round " + round + " the token was incorrect.", playedRoundResult.getExpectedToken(), newGame.asToken());
			Assert.assertEquals(
				"After round " + round + " the remaining players were incorrect.", 
				Joiner.on(", ").join(playedRoundResult.getExpectedRemainingPlayers()),
				Joiner.on(", ").join(newGame.getCurrentPlayers()));
			Assert.assertEquals(
				"After round " + round + " the game finished state was incorrect.", 
				playedRoundResult.isExpectedGameFinished(), newGame.isLost());
			game = newGame;
			round++;
		}
	}

}
