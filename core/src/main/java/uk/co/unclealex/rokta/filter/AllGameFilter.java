package uk.co.unclealex.rokta.filter;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;

public class AllGameFilter extends AbstractGameFilter {

	@Override
	protected void decodeInfo(String encodingInfo)
			throws IllegalFilterEncodingException {
		// no info required.
	}

	@Override
	protected String encodeInfo() {
		return "";
	}

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.ALL_PREFIX;
	}

	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}

	public String getDescription() {
		return "for all games";
	}

	public SortedSet<Game> getGames() {
		return getGameDao().getAllGames();
	}

}
