package uk.co.unclealex.rokta.filter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeekGameFilter extends NamedPeriodGameFilter {

	private int i_week;
	private int i_year;
	
	@Override
	protected void decodeNumbers(int[] numbers) {
		setWeek(numbers[0]);
		setYear(numbers[1]);
	}

	@Override
	protected int[] encodeNumbers() {
		return new int[] { getWeek(), getYear() };
	}

	@Override
	protected int[] getNumberLengths() {
		return new int[] { 2, 4 };
	}

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.WEEK_PREFIX;
	}

	@Override
	protected Calendar getFirstDateAsCalendar() {
		Calendar cal = new GregorianCalendar(getYear(), 0, 1);
		cal.add(Calendar.WEEK_OF_YEAR, getWeek() - 1);
		return cal;
	}
	
	@Override
	protected int getPeriodLengthField() {
		return Calendar.WEEK_OF_YEAR;
	}
	
	@Override
	public String getPeriodNameDateFormat() {
		return "'Week' w, yyyy";
	}

	public int getYear() {
		return i_year;
	}

	protected void setYear(int year) {
		i_year = year;
	}
	
	public int getWeek() {
		return i_week;
	}

	protected void setWeek(int week) {
		i_week = week;
	}
	
	@Override
	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}
}
