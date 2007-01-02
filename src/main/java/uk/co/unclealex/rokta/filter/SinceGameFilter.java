package uk.co.unclealex.rokta.filter;

import java.util.Date;

import uk.co.unclealex.rokta.process.restriction.GameRestriction;
import uk.co.unclealex.rokta.process.restriction.SinceGameRestriction;

public class SinceGameFilter extends SingleDateGameRestrictionGameFilter {

	private Date i_since;
	
	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.BEFORE_PREFIX;
	}

	@Override
	protected GameRestriction getGameRestriction() {
		return new SinceGameRestriction(getSince());
	}

	public String getDescription() {
		return "since " + createDisplayDateFormat().format(getSince());
	}

	public Date getSince() {
		return i_since;
	}

	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}

}
