package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public abstract class DateGameFilter extends AbstractGameFilter<DateGameFilter> {

	private Date i_date;
	
	protected DateGameFilter() {
		super();
	}
	
	public DateGameFilter(Modifier modifier, Date date) {
		super(modifier);
		i_date = date;
	}

	@Override
	protected boolean isEqual(DateGameFilter other) {
		return getDate().equals(other.getDate());
	}

	public Date getDate() {
		return i_date;
	}

}
