package uk.co.unclealex.rokta.filter.factory;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import uk.co.unclealex.rokta.filter.AllGameFilter;
import uk.co.unclealex.rokta.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.filter.GameFilter;
import uk.co.unclealex.rokta.filter.MonthGameFilter;
import uk.co.unclealex.rokta.filter.SinceGameFilter;
import uk.co.unclealex.rokta.filter.WeekGameFilter;
import uk.co.unclealex.rokta.filter.YearGameFilter;

@Service
public class GameFilterFactoryImpl implements GameFilterFactory {

	@Override
	public GameFilter createAllGameFilter() {
		return new AllGameFilter();
	}

	@Override
	public GameFilter createBeforeGameFilter(DateTime before) {
		return new BeforeGameFilter(before);
	}

	@Override
	public GameFilter createBetweenGameFilter(DateTime from, DateTime to) {
		return new BetweenGameFilter(from, to);
	}

	@Override
	public GameFilter createSinceGameFilter(DateTime since) {
		return new SinceGameFilter(since);
	}

	@Override
	public GameFilter createMonthGameFilter(int month, int year) {
		return new MonthGameFilter(month, year);
	}

	@Override
	public GameFilter createWeekGameFilter(int week, int year) {
		return new WeekGameFilter(week, year);
	}

	@Override
	public GameFilter createYearGameFilter(int year) {
		return new YearGameFilter(year);
	}
}
