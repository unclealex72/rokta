package uk.co.unclealex.rokta.client.messages;

import java.util.Date;

import com.google.gwt.i18n.client.Messages;

public interface TitleMessages extends Messages {

	@DefaultMessage("Rokta - {0}")
	String title(String title);
	
	@DefaultMessage("Rokta - {0} for {1}")
	String gameFilterTitle(String title, String gameFilter);
	
	@DefaultMessage("{0} {1}")
	String gameFilter(String modifier, String unmodifiedGameFilter);
	
	@DefaultMessage("Current information")
	String information();

	@DefaultMessage("The league")
	String league();

	@DefaultMessage("The league graph")
	String graph();

	@DefaultMessage("Winning streaks")
	String winningStreaks();

	@DefaultMessage("Losing streaks")
	String losingStreaks();

	@DefaultMessage("Head to heads")
	String headToHeads();

	@DefaultMessage("Administration")
	String admin();

	@DefaultMessage("Game in progress")
	String game();

	@DefaultMessage("")
	String all();

	@DefaultMessage("in {0,date,yyyy}")
	String year(Date date);

	@DefaultMessage("during week {0,date,ww yyyy}")
	String week(Date date);

	@DefaultMessage("during {0,date,MMMM yyyy}")
	String month(Date date);

	@DefaultMessage("since {0,date,dd MMMM yyyy}")
	String since(Date date);

	@DefaultMessage("between {0,date,dd MMMM yyyy} and {1,date,dd MMMM yyyy}")
	String between(Date from, Date to);

	@DefaultMessage("before {0,date,dd MMMM yyyy}")
	String before(Date date);

	@DefaultMessage("{0}''s profile")
	String profile(String username);

	@DefaultMessage("first games of the year")
	String firstGameOfTheYear();

	@DefaultMessage("first games of the month")
	String firstGameOfTheMonth();

	@DefaultMessage("first games of the week")
	String firstGameOfTheWeek();

	@DefaultMessage("first games of the day")
	String firstGameOfTheDay();

	@DefaultMessage("last games of the year")
	String lastGameOfTheYear();

	@DefaultMessage("last games of the month")
	String lastGameOfTheMonth();

	@DefaultMessage("last games of the week")
	String lastGameOfTheWeek();

	@DefaultMessage("last games of the day")
	String lastGameOfTheDay();

	@DefaultMessage("all games")
	String noop();	
}
