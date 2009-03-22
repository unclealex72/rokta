package uk.co.unclealex.rokta.filter;

import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.predicate.GamePredicate;
import uk.co.unclealex.rokta.predicate.GamePredicateFactory;

public class PredicateGameFilter extends AbstractGameFilter {

	private static final String SEPERATOR = ".";
	private GameFilter i_decoratedGameFilter;
	private GamePredicate i_gamePredicate;
	private GamePredicateFactory i_gamePredicateFactory;

	@Override
	protected void decodeInfo(String encodingInfo) throws IllegalFilterEncodingException {
		StringTokenizer tokenizer = new StringTokenizer(encodingInfo, SEPERATOR);
		if (tokenizer.countTokens() != 2) {
			throw new IllegalFilterEncodingException("Could not extract the components from " + encodingInfo);
		}
		GamePredicate gamePredicate = getGamePredicateFactory().decode(tokenizer.nextToken());
		GameFilter gameFilter = getEncodableFactory().decode(tokenizer.nextToken());
		setGamePredicate(gamePredicate);
		setDecoratedGameFilter(gameFilter);
	}

	@Override
	protected String encodeInfo() {
		return getGamePredicate().encode() + SEPERATOR + getDecoratedGameFilter().encode();
	}

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.PREDICATE_PREFIX;
	}

	public String getDescription() {
		return getGamePredicate().getDescription() + " " + getDecoratedGameFilter().getDescription();
	}

	public SortedSet<Game> getGames() {
		SortedSet<Game> originalGames = getDecoratedGameFilter().getGames();
		SortedSet<Game> games = new TreeSet<Game>();
		CollectionUtils.select(originalGames, getGamePredicate().createPredicate(originalGames), games);
		return games;
	}

	public GameFilter getDecoratedGameFilter() {
		return i_decoratedGameFilter;
	}

	public GamePredicate getGamePredicate() {
		return i_gamePredicate;
	}

	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}

	protected void setDecoratedGameFilter(GameFilter decoratedGameFilter) {
		i_decoratedGameFilter = decoratedGameFilter;
	}

	protected void setGamePredicate(GamePredicate gamePredicate) {
		i_gamePredicate = gamePredicate;
	}

	public GamePredicateFactory getGamePredicateFactory() {
		return i_gamePredicateFactory;
	}

	public void setGamePredicateFactory(GamePredicateFactory gamePredicateFactory) {
		i_gamePredicateFactory = gamePredicateFactory;
	}
}
