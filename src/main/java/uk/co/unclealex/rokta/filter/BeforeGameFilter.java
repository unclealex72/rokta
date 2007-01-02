package uk.co.unclealex.rokta.filter;

import uk.co.unclealex.rokta.process.restriction.BeforeGameRestriction;
import uk.co.unclealex.rokta.process.restriction.GameRestriction;

public class BeforeGameFilter extends SingleDateGameRestrictionGameFilter {

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.BEFORE_PREFIX;
	}

	@Override
	protected GameRestriction getGameRestriction() {
		return new BeforeGameRestriction(getDate());
	}

	public String getDescription() {
		return "before " + createDisplayDateFormat().format(getDate());
	}

	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}
}
