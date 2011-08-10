package uk.co.unclealex.rokta.client.places;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GamePlace extends RoktaPlace {

	private final Iterable<Integer> i_players;
	private final String i_plays;

	public GamePlace(Iterable<Integer> players, String plays) {
		super();
		i_players = players;
		i_plays = plays;
	}

	@Override
	public boolean equals(Object other) {
		return 
				(other instanceof GamePlace) && 
				(Iterables.elementsEqual(getPlayers(), ((GamePlace) other).getPlayers())) &&
				(getPlays().equals(((GamePlace) other).getPlays()));
	}
	
	@Override
	public void accept(RoktaPlaceVisitor visitor) {
		visitor.visit(this);
	}
	
	public static class Tokenizer implements PlaceTokenizer<GamePlace> {

		@Override
		public GamePlace getPlace(String token) {
			List<String> parts = Lists.newArrayList(Splitter.on(':').split(token));
			String plays = parts.remove(parts.size() - 1);
			Function<String, Integer> function = new Function<String, Integer>() {
				public Integer apply(String hex) {
					return Integer.parseInt(hex, 16);
				}
			};
			return new GamePlace(Iterables.transform(parts, function), plays);
		}

		@Override
		public String getToken(GamePlace place) {
			Function<Integer, String> function = new Function<Integer, String>() {
				public String apply(Integer player) {
					return Integer.toHexString(player);
				}
			};
			return Joiner.on(':').join(
				Iterables.concat(Iterables.transform(place.getPlayers(), function), Collections.singleton(place.getPlays())));
		}

	}

	public Iterable<Integer> getPlayers() {
		return i_players;
	}

	public String getPlays() {
		return i_plays;
	}
}
