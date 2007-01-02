package uk.co.unclealex.rokta.filter;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Date;

import uk.co.unclealex.rokta.process.restriction.BetweenGameRestriction;
import uk.co.unclealex.rokta.process.restriction.GameRestriction;

public class BetweenGameFilter extends GameRestrictionGameFilter {

	private Date i_from;
	private Date i_to;

	@Override
	protected void decodeInfo(String encodingInfo) throws IllegalFilterEncodingException {
		ParsePosition pos = new ParsePosition(0);
		DateFormat fmt = createEncodingDateFormat();
		Date from = fmt.parse(encodingInfo, pos);
		Date to = fmt.parse(encodingInfo, pos);
		if (from == null || to == null) {
			throw new IllegalFilterEncodingException("Could not parse " + encodingInfo + " for a start and end date.");
		}
		setFrom(from);
		setTo(to);
	}

	@Override
	protected String encodeInfo() {
		DateFormat fmt = createEncodingDateFormat();
		return fmt.format(getFrom()) + fmt.format(getTo());
	}

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.BETWEEN_PREFIX;
	}


	@Override
	protected GameRestriction getGameRestriction() {
		return new BetweenGameRestriction(getFrom(), getTo());
	}

	public String getDescription() {
		DateFormat fmt = createDisplayDateFormat();
		return "between " + fmt.format(getFrom()) + "and " + fmt.format(getTo());
	}

	public Date getFrom() {
		return i_from;
	}

	public Date getTo() {
		return i_to;
	}

	protected void setFrom(Date from) {
		i_from = from;
	}

	protected void setTo(Date to) {
		i_to = to;
	}

	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}
}
