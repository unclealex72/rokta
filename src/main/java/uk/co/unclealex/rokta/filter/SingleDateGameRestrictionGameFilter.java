package uk.co.unclealex.rokta.filter;

import java.text.ParseException;
import java.util.Date;

public abstract class SingleDateGameRestrictionGameFilter extends
		GameRestrictionGameFilter {

	private Date i_date;

	@Override
	protected final void decodeInfo(String encodingInfo) throws IllegalFilterEncodingException {
		try {
			setDate(createEncodingDateFormat().parse(encodingInfo));
		} catch (ParseException e) {
			throw new IllegalFilterEncodingException("Could not parse date " + encodingInfo, e);
		}
	}

	@Override
	protected final String encodeInfo() {
		return createEncodingDateFormat().format(getDate());
	}


	public Date getDate() {
		return i_date;
	}

	protected void setDate(Date date) {
		i_date = date;
	}

}
