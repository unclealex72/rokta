package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

import com.google.common.base.Objects;

public abstract class DateGameFilter extends AbstractGameFilter<DateGameFilter> {

	private Date i_date;
	
	protected DateGameFilter() {
		super();
	}
	
	DateGameFilter(Modifier modifier, Date date) {
		super(modifier);
		i_date = date;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int completeHash() {
		Date date = getDate();
		return Objects.hashCode(date.getYear(), date.getMonth(), date.getDate());
	}
	
	@Override
	protected boolean isEqual(DateGameFilter other) {
		return isSameDay(getDate(), other.getDate());
	}

	public Date getDate() {
		return i_date;
	}

}
