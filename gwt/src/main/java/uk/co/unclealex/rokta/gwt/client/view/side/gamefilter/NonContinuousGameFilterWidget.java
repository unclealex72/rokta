package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Arrays;
import java.util.List;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class NonContinuousGameFilterWidget extends GameFilterProducerComposite implements GameFilterProducer, ChangeHandler {

	private ListBox i_listBox;
	private List<GameFilter> i_gameFilters;
	
	public NonContinuousGameFilterWidget(
		RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaController, model, gameFilterProducerListeners);
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
	
	public void onValueChanged(InitialDatesView value) {
		// Do nothing
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
