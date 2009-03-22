package uk.co.unclealex.rokta.filter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MonthGameFilter extends NamedPeriodGameFilter {

	private int i_month;
	private int i_year;
	
	@Override
	protected void decodeNumbers(int[] numbers) {
		setMonth(numbers[0]);
		setYear(numbers[1]);
	}

	@Override
	protected int[] encodeNumbers() {
		return new int[] { getMonth(), getYear() };
	}

	@Override
	protected int[] getNumberLengths() {
		return new int[] { 2, 4 };
	}

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.MONTH_PREFIX;
	}
	
	@Override
	protected Calendar getFirstDateAsCalendar() {
		return new GregorianCalendar(getYear(), getMonth() - 1, 1);
	}
	
	@Override
	protected int getPeriodLengthField() {
		return Calendar.MONTH;
	}
	
	@Override
	public String getPeriodNameDateFormat() {
		return "MMMM, yyyy";
	}

	public int getYear() {
		return i_year;
	}

	protected void setYear(int year) {
		i_year = year;
	}
	
	public int getMonth() {
		return i_month;
	}

	protected void setMonth(int month) {
		i_month = month;
	}
	
	@Override
	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}
}
