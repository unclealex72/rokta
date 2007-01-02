package uk.co.unclealex.rokta.filter;

import java.util.Calendar;
import java.util.GregorianCalendar;

import uk.co.unclealex.rokta.model.dao.GameDao;

public class YearGameFilter extends NamedPeriodGameFilter {

	private int i_year;

	public YearGameFilter() {
	}	
	
	public YearGameFilter(int year, GameDao gameDao) {
		super();
		i_year = year;
		setGameDao(gameDao);
	}

	@Override
	protected char getEncodingPrefix() {
		return GameFilterFactory.YEAR_PREFIX;
	}

	@Override
	protected void decodeNumbers(int[] numbers) {
		setYear(numbers[0]);
	}

	@Override
	protected int[] encodeNumbers() {
		return new int[] { getYear() };
	}

	@Override
	protected int[] getNumberLengths() {
		return new int[] { 4 };
	}

	@Override
	protected Calendar getFirstDateAsCalendar() {
		return new GregorianCalendar(getYear(), 1, 1);
	}

	@Override
	protected int getPeriodLengthField() {
		return Calendar.YEAR;
	}

	@Override
	public String getPeriodNameDateFormat() {
		return "yyyy";
	}

	public int getYear() {
		return i_year;
	}

	protected void setYear(int year) {
		i_year = year;
	}
	
	@Override
	public void accept(GameFilterVistor gameFilterVisitor) {
		gameFilterVisitor.visit(this);
	}
}
