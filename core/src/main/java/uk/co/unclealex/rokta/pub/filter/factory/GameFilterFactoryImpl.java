package uk.co.unclealex.rokta.pub.filter.factory;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import uk.co.unclealex.rokta.pub.filter.AllGameFilter;
import uk.co.unclealex.rokta.pub.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.pub.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.MonthGameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;
import uk.co.unclealex.rokta.pub.filter.WeekGameFilter;
import uk.co.unclealex.rokta.pub.filter.YearGameFilter;

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
