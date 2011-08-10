package uk.co.unclealex.rokta.server.process;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Required;

import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialDates;

public class DateServiceImpl implements DateService {

	private GameDao i_gameDao;

	@Override
	public SortedSet<Integer> getValidMonthsForYear(int year) {
		return getValidPartsForYear(year, 0, 11, Calendar.MONTH);
	}
	
	@Override
	public SortedSet<Integer> getValidWeeksForYear(int year) {
		return getValidPartsForYear(year, 1, 53, Calendar.YEAR);
	}
	
	protected SortedSet<Integer> getValidPartsForYear(int year, int minumum, int maximum, int calendarField) {
		int first;
		int last;
		Calendar calendar = new GregorianCalendar();
		if (year == getFirstYear(calendar)) {
			calendar.setTime(getGameDao().getDateFirstGameInYearPlayed(year));
			first = calendar.get(calendarField);
			last = maximum;
		}
		else if (year == getLastYear(calendar)) {
			first = minumum;
			calendar.setTime(getGameDao().getDateLastGameInYearPlayed(year));
			last = calendar.get(calendarField);
		}
		else {
			first = minumum;
			last = maximum;
		}
		return asSortedSet(first, last);
	}

	@Override
	public SortedSet<Integer> getValidYears() {		
		Calendar calendar = new GregorianCalendar();
		return asSortedSet(getFirstYear(calendar), getLastYear(calendar));
	}

	@Override
	public Date normaliseDate(Date date) {
		GameDao gameDao = getGameDao();
		Date normalisedDate;
		Date earliestDate = gameDao.getDateFirstGamePlayed();
		if (date.compareTo(earliestDate) < 0) {
			normalisedDate = earliestDate;
		}
		else {
			Date latestDate = gameDao.getDateLastGamePlayed();
			normalisedDate = date.compareTo(latestDate) > 0?latestDate:date;
		}
		return normalisedDate;
	}
	
	@Override
	public InitialDates getInitialDates() {
		GameDao gameDao = getGameDao();
		return new InitialDates(
				normaliseDate(new Date()), gameDao.getDateFirstGamePlayed(), gameDao.getDateLastGamePlayed());
	}
	
	protected SortedSet<Integer> asSortedSet(int first, int last) {
		SortedSet<Integer> numbers = new TreeSet<Integer>();
		for (int number = first; number <= last; number++) {
			numbers.add(number);
		}
		return numbers;
	}

	protected int getYearForGame(Game game) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(game.getDatePlayed());
		return calendar.get(Calendar.YEAR);
	}
	
	protected int getFirstYear(Calendar calendar) {
		calendar.setTime(getGameDao().getDateFirstGamePlayed());
		return calendar.get(Calendar.YEAR);
	}
	
	protected int getLastYear(Calendar calendar) {
		calendar.setTime(getGameDao().getDateLastGamePlayed());
		return calendar.get(Calendar.YEAR);
	}
	
	public GameDao getGameDao() {
		return i_gameDao;
	}

	@Required
	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

}
