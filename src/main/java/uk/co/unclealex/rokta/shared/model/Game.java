package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class Game implements Serializable {

	private static final char MAIN_SEPARATOR = ':';
	private static final char SUB_SEPARATOR = ';';
	private static final char ROUND_SEPARATOR = ',';
	
	private static final Map<Character, Hand> HANDS_BY_TOKEN = new HashMap<Character, Hand>();
	private static final Map<Hand, Character> TOKENS_BY_HAND = new HashMap<Hand, Character>();
	
	static {
		HANDS_BY_TOKEN.put('r', Hand.ROCK);
		HANDS_BY_TOKEN.put('s', Hand.SCISSORS);
		HANDS_BY_TOKEN.put('p', Hand.PAPER);
		for (Entry<Character, Hand> entry : HANDS_BY_TOKEN.entrySet()) {
			TOKENS_BY_HAND.put(entry.getValue(), entry.getKey());
		}
	}
	
	public static class Round implements Serializable {
		private String i_counter;
		private List<Hand> i_plays;
		
		protected Round() {
			// Default constructor for serialisation
		}
		
		public Round(String counter, List<Hand> plays) {
			i_counter = counter;
			i_plays = plays;
		}
	
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Round)) {
				return false;
			}
			Round other = (Round) obj;
			return Objects.equal(getCounter(), other.getCounter()) && Objects.equal(getPlays(), other.getPlays());
		}
		
		public String getCounter() {
			return i_counter;
		}
		
		public List<Hand> getPlays() {
			return i_plays;
		}
	}
	
	private String i_instigator;
	private SortedSet<String> i_players;
	private List<Round> i_rounds;
	
	public Game() {
		// Empty game.
	}

	public Game(String token) {
		List<String> tokenParts = Lists.newArrayList(Splitter.on(MAIN_SEPARATOR).split(token));
		int size = tokenParts.size();
		if (size > 0) {
			setInstigator(createInstigator(tokenParts.get(0)));
		}
		if (size > 1) {
			setPlayers(createPlayers(tokenParts.get(1)));
		}
		if (size > 2) {
			setRounds(createRounds(tokenParts.get(2)));
		}
	}

	public Game(String instigator, SortedSet<String> players) {
		this(instigator, players, null);
	}
	
	protected Game(String instigator, SortedSet<String> players, List<Round> rounds) {
		i_instigator = instigator;
		i_players = players;
		i_rounds = rounds;
	}

	protected Game(Game game) {
		this(
			game.getInstigator(),
			game.getPlayers()==null?null:Sets.newTreeSet(game.getPlayers()),
			game.getRounds()==null?null:Lists.newArrayList(game.getRounds()));
	}
	
	public String asToken() {
		String instigatorToken = instigatorAsToken();
		String playersToken = playersAsToken();
		String roundsToken = roundsAsToken();
		return Joiner.on(MAIN_SEPARATOR).join(
			Iterables.filter(Arrays.asList(instigatorToken, playersToken, roundsToken), Predicates.notNull()));
	}
	
	protected String createInstigator(String instigator) {
		return instigator;
	}

	protected String instigatorAsToken() {
		return getInstigator();
	}
	
	protected SortedSet<String> createPlayers(String players) {
		return Sets.newTreeSet(Splitter.on(SUB_SEPARATOR).split(players));
	}

	protected String playersAsToken() {
		SortedSet<String> players = getPlayers();
		return players == null?null:Joiner.on(SUB_SEPARATOR).join(players);
	}
	
	protected List<Round> createRounds(String rounds) {
		Function<String, Round> roundFunction = new Function<String, Game.Round>() {
			String previousCounter = null;
			@Override
			public Round apply(String token) {
				Round round = createRound(token, previousCounter);
				previousCounter = round.getCounter();
				return round;
			}
		};
		return Lists.newArrayList(Iterables.transform(Splitter.on(SUB_SEPARATOR).split(rounds), roundFunction));
	}

	protected String roundsAsToken() {
		List<Round> rounds = getRounds();
		return rounds == null?null:Joiner.on(SUB_SEPARATOR).join(Iterables.transform(rounds, createRoundAsTokenFactory()));
	}
	
	protected Function<Round, String> createRoundAsTokenFactory() {
		return new Function<Round, String>() {
			String previousCounter = null;
			@Override
			public String apply(Round round) {
				StringBuilder builder = new StringBuilder();
				String counter = round.getCounter();
				if (!counter.equals(previousCounter)) {
					builder.append(counter);
					builder.append(ROUND_SEPARATOR);
				}
				previousCounter = counter;
				for (Hand hand : round.getPlays()) {
					builder.append(TOKENS_BY_HAND.get(hand));
				}
				return builder.toString();
			}
		};
	}
	
	protected Round createRound(String token, String previousCounter) {
		String counter;
		int separatorPos = token.indexOf(ROUND_SEPARATOR);
		if (separatorPos < 0) {
			counter = previousCounter;
		}
		else {
			counter = token.substring(0, separatorPos);
		}
		String playToken = token.substring(separatorPos + 1);
		char[] plays = playToken.toCharArray();
		List<Hand> hands = new ArrayList<Hand>();
		for (char play : plays) {
			hands.add(HANDS_BY_TOKEN.get(play));
		}
		return new Round(counter, hands);
	}
	
	public boolean isStarted() {
		return getPlayers() != null;
	}
	
	public boolean isLost() {
		SortedSet<String> currentPlayers = getCurrentPlayers();
		return currentPlayers != null && currentPlayers.size() == 1;
	}
	
	public String getLastCounter() {
		List<Round> rounds = getRounds();
		return rounds == null?null:rounds.get(rounds.size() - 1).getCounter();
	}
	

	public SortedSet<String> getCurrentPlayers(int rounds) {
		SortedSet<String> currentPlayers = Sets.newTreeSet(getPlayers());
		for (Round round : getRounds().subList(0, rounds)) {
			removeWinners(round, currentPlayers);
		}
		return currentPlayers;
	}
	
	public SortedSet<String> getCurrentPlayers() {
		List<Round> rounds = getRounds();
		return rounds==null?getPlayers():getCurrentPlayers(rounds.size());
	}
	
	protected void removeWinners(Round round, SortedSet<String> currentPlayers) {
		// People can only be removed if there are exactly two hand types played.
		final List<Hand> plays = round.getPlays();
		Set<Hand> playedHands = Sets.newHashSet(plays);
		if (playedHands.size() == 2) {
			Iterator<Hand> playedHandsIter = playedHands.iterator();
			Hand firstHand = playedHandsIter.next();
			Hand secondHand = playedHandsIter.next();
			final Hand winningHand = firstHand.beats(secondHand)?firstHand:secondHand;
			Predicate<String> winningPredicate = new Predicate<String>() {
				int idx = 0;
				@Override
				public boolean apply(String name) {
					return plays.get(idx++) == winningHand;
				}
			};
			Iterables.removeIf(currentPlayers, winningPredicate);
		}
	}

	public Game addRound(String counter, Iterable<Hand> plays) {
		Game newGame = new Game(this);
		List<Round> rounds = newGame.getRounds();
		if (rounds == null) {
			rounds = Lists.newArrayList();
			newGame.setRounds(rounds);
		}
		rounds.add(new Round(counter, Lists.newArrayList(plays)));
		return newGame;
	}

	public String getLoser() {
		SortedSet<String> currentPlayers = getCurrentPlayers();
		return currentPlayers.size() == 1?currentPlayers.first():null;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Game)) {
			return false;
		}
		Game other = (Game) obj;
		return Objects.equal(getInstigator(), other.getInstigator()) && Objects.equal(getPlayers(), other.getPlayers()) &&
				Objects.equal(getRounds(), other.getRounds());
	}

	public String getInstigator() {
		return i_instigator;
	}

	protected void setInstigator(String instigator) {
		i_instigator = instigator;
	}

	public SortedSet<String> getPlayers() {
		return i_players;
	}

	protected void setPlayers(SortedSet<String> players) {
		i_players = players;
	}

	public List<Round> getRounds() {
		return i_rounds;
	}

	protected void setRounds(List<Round> rounds) {
		i_rounds = rounds;
	}
}
