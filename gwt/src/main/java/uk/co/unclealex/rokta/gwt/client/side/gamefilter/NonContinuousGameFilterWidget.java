package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Arrays;
import java.util.List;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class NonContinuousGameFilterWidget extends GameFilterProducerComposite implements GameFilterProducer, ChangeHandler {

	private ListBox i_listBox;
	private List<GameFilter> i_gameFilters;
	
	public NonContinuousGameFilterWidget(RoktaAdaptor roktaAdaptor, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaAdaptor, gameFilterProducerListeners);
		GameFilterMessages messages = GWT.create(GameFilterMessages.class);
		
		ListBox listBox = new ListBox();
		setListBox(listBox);
		for (
				String label : 
				new String[] { 
					messages.anyGame(), messages.firstGameOfTheDay(), messages.firstGameOfTheWeek(), messages.firstGameOfTheMonth(), 
					messages.firstGameOfTheYear(), messages.lastGameOfTheDay(), messages.lastGameOfTheWeek(), 
					messages.lastGameOfTheMonth(), messages.lastGameOfTheYear()}) {
			listBox.addItem(label);
		}
		setGameFilters(Arrays.asList(
				new GameFilter[] { 
					null, new FirstGameOfTheDayFilter(), new FirstGameOfTheWeekFilter(), new FirstGameOfTheMonthFilter(), 
					new FirstGameOfTheYearFilter(), new LastGameOfTheDayFilter(), new LastGameOfTheWeekFilter(), 
					new LastGameOfTheMonthFilter(), new LastGameOfTheYearFilter() }));
		listBox.addChangeHandler(this);
		initWidget(listBox);
	}

	public void onChange(ChangeEvent event) {
		setGameFilter(createGameFilter());
	}
	
	@Override
	protected GameFilter createGameFilter() {
		return getGameFilters().get(getListBox().getSelectedIndex());
	}

	protected void select(int index) {
		getListBox().setSelectedIndex(index);
		setGameFilter(createGameFilter());
	}
	
	@Override
	public void onGameFilterChange(GameFilter newGameFilter) {
		GameFilterVistor<Integer> visitor = new GameFilterVistor<Integer>() {
			@Override
			public Integer join(Integer leftResult, Integer rightResult) {
				return rightResult;
			}
			@Override
			public Integer visit(BeforeGameFilter beforeGameFilter) {
				return 0;
			}
			@Override
			public Integer visit(BetweenGameFilter betweenGameFilter) {
				return 0;
			}
			@Override
			public Integer visit(SinceGameFilter sinceGameFilter) {
				return 0;
			}
			@Override
			public Integer visit(MonthGameFilter monthGameFilter) {
				return 0;
			}
			@Override
			public Integer visit(WeekGameFilter weekGameFilter) {
				return 0;
			}
			@Override
			public Integer visit(YearGameFilter yearGameFilter) {
				return 0;
			}
			@Override
			public Integer visit(AllGameFilter allGameFilter) {
				return 0;
			}
			@Override
			public Integer visit(FirstGameOfTheDayFilter firstGameOfTheDayFilter) {
				return 1;
			}
			@Override
			public Integer visit(FirstGameOfTheWeekFilter firstGameOfTheWeekFilter) {
				return 2;
			}
			@Override
			public Integer visit(FirstGameOfTheMonthFilter firstGameOfTheMonthFilter) {
				return 3;
			}
			@Override
			public Integer visit(FirstGameOfTheYearFilter firstGameOfTheYearFilter) {
				return 4;
			}
			@Override
			public Integer visit(LastGameOfTheDayFilter lastGameOfTheDayFilter) {
				return 5;
			}
			@Override
			public Integer visit(LastGameOfTheWeekFilter lastGameOfTheWeekFilter) {
				return 6;
			}
			@Override
			public Integer visit(LastGameOfTheMonthFilter lastGameOfTheMonthFilter) {
				return 7;
			}
			@Override
			public Integer visit(LastGameOfTheYearFilter lastGameOfTheYearFilter) {
				return 8;
			}	
		};
		select(newGameFilter.accept(visitor));
	}
	
	public ListBox getListBox() {
		return i_listBox;
	}

	public void setListBox(ListBox listBox) {
		i_listBox = listBox;
	}

	public List<GameFilter> getGameFilters() {
		return i_gameFilters;
	}

	public void setGameFilters(List<GameFilter> gameFilters) {
		i_gameFilters = gameFilters;
	}

}
