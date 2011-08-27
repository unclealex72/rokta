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

	@DefaultMessage("Get ready to play!")
	String newGame();

	@DefaultMessage("Oh, {0}, I''m so so sorry...")
	String gameLostOne(String loser);

	@DefaultMessage("And the loser is... {0}")
	String gameLostTwo(String loser);

	@DefaultMessage("{0} has lost")
	String gameLostThree(String loser);

	@DefaultMessage("{0} will be making the next round")
	String gameLostFour(String loser);

	@DefaultMessage("New game: round {0}")
	String gameRound(int round);		

	@DefaultMessage("")
	String all();

	@DefaultMessage("in {0,date,yyyy}")
	String year(Date date);

	@DefaultMessage("during the week starting on {0,date,d MMMM yyyy}")
	String week(Date date);

	@DefaultMessage("during {0,date,MMMM yyyy}")
	String month(Date date);

	@DefaultMessage("since {0,date,dd MMMM yyyy}")
	String since(Date date);

	@DefaultMessage("between {0,date,d MMMM yyyy} and {1,date,d MMMM yyyy}")
	String between(Date from, Date to);

	@DefaultMessage("before {0,date,d MMMM yyyy}")
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

	@DefaultMessage("{0}''s opening hands distribution")
	String openingHandCounts(String username);	

	@DefaultMessage("{0}''s hands distribution")
	String handCounts(String username);

	@DefaultMessage("{0}''s graph colour is {1}")
	String playerColour(String username, String colourName);

	@DefaultMessage("Top {0} winning streaks")
	String allWinningStreaks(int size);	

	@DefaultMessage("Current winning streaks")
	String currentWinningStreaks();	

	@DefaultMessage("Top {0} losing streaks")
	String allLosingStreaks(int size);	

	@DefaultMessage("Current losing streaks")
	String currentLosingStreaks();

	@DefaultMessage("{0}: {1}, {2}: {3}")
	String headToHeadsSummary(String firstPerson, int firstWinCount, String secondPerson, int secondWinCount);

	@DefaultMessage("Win next game {0,number,#.##}%, lose next game {1,number,#.##}%")
	String nextGame(double winNext, double loseNext);
}
