package uk.co.unclealex.rokta.client.places;

import java.util.Collections;
import java.util.List;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.filter.GameFilterFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gwt.place.shared.PlaceTokenizer;

public abstract class GameFilterAwarePlace extends RoktaPlace {

	private final GameFilter i_gameFilter;
	
	public GameFilterAwarePlace(GameFilter gameFilter) {
		super();
		i_gameFilter = gameFilter;
	}

	public abstract boolean equals(Object other);
	
	public boolean isEqual(GameFilterAwarePlace other) {
		return getGameFilter().equals(other.getGameFilter());
	}
	
	public abstract GameFilterAwarePlace withGameFilter(GameFilter gameFilter);
	
	protected abstract static class Tokenizer<P extends GameFilterAwarePlace> implements PlaceTokenizer<P> {
		
		@Override
		public P getPlace(String token) {
			List<String> tokenParts = Lists.newArrayList(Splitter.on('@').split(token));
			int lastIndex = tokenParts.size() - 1;
			List<String> tokens = tokenParts.subList(0, lastIndex);
			String gameFilterToken = tokenParts.get(lastIndex);
			return getPlace(tokens, GameFilterFactory.createGameFilter(gameFilterToken));
		}
		
		protected abstract P getPlace(List<String> tokens, GameFilter gameFilter);

		@Override
		public String getToken(P place) {
			return 
				Joiner.on('@').join(
					Iterables.concat(
						formatTokens(place), 
						Collections.singleton(GameFilterFactory.asToken(place.getGameFilter()))));
		}

		protected Iterable<String> formatTokens(P place) {
			return Collections.emptySet();
		}
		
	}

	public GameFilter getGameFilter() {
		return i_gameFilter;
	}
}


