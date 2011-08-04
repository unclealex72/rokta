package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public class YearGameFilter extends AbstractGameFilter {

	private Date i_date;
	
	protected YearGameFilter() {
	}
	
	public YearGameFilter(Date date) {
		super();
		i_date = date;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public String[] toStringArgs() {
		return new String[] { Long.toString(getDate().getTime()) }; 
	}
	
	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	public Date getDate() {
		return i_date;
	}
	
	protected void setDate(Date date) {
		i_date = date;
	}
}
