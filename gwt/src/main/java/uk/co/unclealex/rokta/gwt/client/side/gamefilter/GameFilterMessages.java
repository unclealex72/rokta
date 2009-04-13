package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import com.google.gwt.i18n.client.Messages;

public interface GameFilterMessages extends Messages {

	@DefaultMessage("Games for a year")
	public String year();

	@DefaultMessage("Games for a month")
	public String month();

	@DefaultMessage("Games for a week")
	public String week();

	@DefaultMessage("Games before")
	public String before();

	@DefaultMessage("Games between")
	public String between();

	@DefaultMessage("Games since")
	public String since();

	@DefaultMessage("All games")
	public String all();

	@DefaultMessage("Go")
	public String ok();

	@DefaultMessage("This year''s games")
	public String thisYear();

	@DefaultMessage("The last four weeks'' games")
	public String lastFourWeeks();

	@DefaultMessage("This month''s games")
	public String thisMonth();

	@DefaultMessage("This week''s games")
	public String thisWeek();

	@DefaultMessage("Today''s games")
	public String today();

	@DefaultMessage("Any Game")
	public String anyGame();
	
	@DefaultMessage("First game of the day")
	public String firstGameOfTheDay();
	
	@DefaultMessage("First game of the week")
	public String firstGameOfTheWeek();
	
	@DefaultMessage("First game of the month")
	public String firstGameOfTheMonth();
	
	@DefaultMessage("First game of the year")
	public String firstGameOfTheYear();
	
	@DefaultMessage("Last game of the day")
	public String lastGameOfTheDay();
	
	@DefaultMessage("Last game of the week")
	public String lastGameOfTheWeek();
	
	@DefaultMessage("Last game of the month")
	public String lastGameOfTheMonth();
	
	@DefaultMessage("Last game of the year")
	public String lastGameOfTheYear();

}
