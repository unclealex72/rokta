package uk.co.unclealex.rokta.internal.process.dataset;

import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.internal.dao.GameDao;
import uk.co.unclealex.rokta.pub.filter.AllGameFilter;
import uk.co.unclealex.rokta.pub.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.pub.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilterVistor;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.filter.MonthGameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;
import uk.co.unclealex.rokta.pub.filter.WeekGameFilter;
import uk.co.unclealex.rokta.pub.filter.YearGameFilter;

@Service
@Transactional
public class GameFilterServiceImpl extends GameFilterVistor<String> implements GameFilterService {

	private ResourceBundle i_resourceBundle;
	private GameDao i_gameDao;
	
	@Override
	public String describeGameFilter(GameFilter gameFilter) {
		return gameFilter.accept(this);
	}

	@Override
	public String join(String leftResult, String rightResult) {
		return formatResource("join", leftResult, rightResult);
	}

	@Override
	public String visit(BeforeGameFilter beforeGameFilter) {
		return formatResource("before", beforeGameFilter.getBefore());
	}

	@Override
	public String visit(BetweenGameFilter betweenGameFilter) {
		Date from = betweenGameFilter.getFrom();
		Date to = betweenGameFilter.getTo();
		DateTime fromTime = new DateTime(from);
		DateTime toTime = new DateTime(to);
		String key;
		if (fromTime.getYear() == toTime.getYear()) {
			if (fromTime.getMonthOfYear() == toTime.getMonthOfYear()) {
				key = "betweensamemonth";
			}
			else {
				key = "betweensameyear";
			}
		}
		else {
			key = "betweendifferentyears";
		}
		return formatResource(key, from, to);
	}

	@Override
	public String visit(SinceGameFilter sinceGameFilter) {
		return since(sinceGameFilter.getSince());
	}

	protected String since(Date since) {
		return formatResource("since", since);
	}
	@Override
	public String visit(MonthGameFilter monthGameFilter) {
		return formatResource("month", monthGameFilter.getDate());
	}

	@Override
	public String visit(WeekGameFilter weekGameFilter) {
		return formatResource("week", weekGameFilter.getDate());
	}

	@Override
	public String visit(YearGameFilter yearGameFilter) {
		return formatResource("year", yearGameFilter.getDate());
	}

	@Override
	public String visit(AllGameFilter allGameFilter) {
		return since(getGameDao().getFirstGame().getDatePlayed());
	}

	@Override
	public String visit(FirstGameOfTheDayFilter firstGameOfTheDayFilter) {
		return formatResource("firstoftheday");
	}

	@Override
	public String visit(FirstGameOfTheWeekFilter firstGameOfTheWeekFilter) {
		return formatResource("firstoftheweek");
	}

	@Override
	public String visit(FirstGameOfTheMonthFilter firstGameOfTheMonthFilter) {
		return formatResource("firstofthemonth");
	}

	@Override
	public String visit(FirstGameOfTheYearFilter firstGameOfTheYearFilter) {
		return formatResource("firstoftheyear");
	}

	@Override
	public String visit(LastGameOfTheDayFilter lastGameOfTheDayFilter) {
		return formatResource("lastoftheday");
	}

	@Override
	public String visit(LastGameOfTheWeekFilter lastGameOfTheWeekFilter) {
		return formatResource("lastoftheweek");
	}

	@Override
	public String visit(LastGameOfTheMonthFilter lastGameOfTheMonthFilter) {
		return formatResource("lastofthemonth");
	}

	@Override
	public String visit(LastGameOfTheYearFilter lastGameOfTheYearFilter) {
		return formatResource("lastoftheyear");
	}

	protected String formatResource(String resourceKey, Object... args) {
		String message = getResourceBundle().getString("title." + resourceKey);
		return MessageFormat.format(message, args);
	}
	
	public ResourceBundle getResourceBundle() {
		return i_resourceBundle;
	}

	@Required
	public void setResourceBundle(ResourceBundle resourceBundle) {
		i_resourceBundle = resourceBundle;
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	@Required
	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

}
