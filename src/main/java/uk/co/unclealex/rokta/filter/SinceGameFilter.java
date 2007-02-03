package uk.co.unclealex.rokta.filter;

import uk.co.unclealex.rokta.process.restriction.GameRestriction;
import uk.co.unclealex.rokta.process.restriction.SinceGameRestriction;

public class SinceGameFilter extends SingleDateGameRestrictionGameFilter {

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.SINCE_PREFIX;
	}

	@Override
	protected GameRestriction getGameRestriction() {
		return new SinceGameRestriction(getDate());
	}

	public String getDescription() {
		return "since " + createDisplayDateFormat().format(getDate());
	}

	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}

}
