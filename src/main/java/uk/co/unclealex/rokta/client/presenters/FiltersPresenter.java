/**
 * 
 */
package uk.co.unclealex.rokta.client.presenters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.AllGameFilter;
import uk.co.unclealex.rokta.client.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.client.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheDayModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheMonthModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheWeekModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheYearModifier;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.filter.GameFilterVisitor;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheDayModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheMonthModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheWeekModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheYearModifier;
import uk.co.unclealex.rokta.client.filter.Modifier;
import uk.co.unclealex.rokta.client.filter.MonthGameFilter;
import uk.co.unclealex.rokta.client.filter.NoOpModifier;
import uk.co.unclealex.rokta.client.filter.SinceGameFilter;
import uk.co.unclealex.rokta.client.filter.WeekGameFilter;
import uk.co.unclealex.rokta.client.filter.YearGameFilter;
import uk.co.unclealex.rokta.client.places.GameFilterAwarePlace;
import uk.co.unclealex.rokta.client.places.LeaguePlace;
import uk.co.unclealex.rokta.client.presenters.FiltersPresenter.Display;
import uk.co.unclealex.rokta.client.security.AuthenticationManager;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * Copyright 2011 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 *
 * @author unclealex72
 *
 */
public class FiltersPresenter extends AbstractPopupPresenter<DialogBox, Display>{

	public interface Display extends AbstractPopupPresenter.Display<DialogBox> {
		Anchor getBeforeAnchor();
		HasValue<Boolean> getBeforeButton();
		Anchor getSinceAnchor();
		HasValue<Boolean> getSinceButton();
		Anchor getToAnchor();
		Anchor getFromAnchor();
		HasValue<Boolean> getBetweenButton();
		HasValue<Boolean> getAllButton();
		Anchor getWeekAnchor();
		HasValue<Boolean> getWeekButton();
		ValueListBox<Integer> getMonthYearSelect();
		ValueListBox<Integer> getMonthSelect();
		HasValue<Boolean> getMonthButton();
		ValueListBox<Integer> getYearSelect();
		ValueListBox<Modifier> getModifierSelect();
		HasValue<Boolean> getYearButton();
		HasClickHandlers getSubmitButton();
		HasClickHandlers getCancelButton();
	}

	private final PlaceController i_placeController;
	private final Display i_display;
	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	private DatePickingLink i_beforeDatePickingLink;
	private DatePickingLink i_sinceDatePickingLink;
	private DatePickingLink i_fromDatePickingLink;
	private DatePickingLink i_toDatePickingLink;
	private DatePickingLink i_weekDatePickingLink;

	@Inject
	public FiltersPresenter(PlaceController placeController, Display display,
			AuthenticationManager authenticationManager, AsyncCallbackExecutor asyncCallbackExecutor) {
		super();
		i_placeController = placeController;
		i_display = display;
		i_asyncCallbackExecutor = asyncCallbackExecutor;
	}

	@Override
	protected void prepare(final Display display) {
		ExecutableAsyncCallback<Date> callback = new FailureAsPopupExecutableAsyncCallback<Date>() {
			@Override
			public void onSuccess(Date dateFirstGamePlayed) {
				prepare(display, dateFirstGamePlayed);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Date> callback) {
				anonymousRoktaService.getDateFirstGamePlayed(callback);
			}
		};
		getAsyncCallbackExecutor().execute(callback);
	}
	
	protected void prepare(Display display, Date dateFirstGamePlayed) {
		Date now = new Date();
		showDropdowns(dateFirstGamePlayed, now);
		showDatePickers(dateFirstGamePlayed, now);
		initialiseFilterButtons(display);
		initialiseModifierSelect(display);
		updateValues();
	}

	@SuppressWarnings("deprecation")
	protected void showDropdowns(final Date dateFirstGamePlayed, final Date now) {
		final int firstYear = dateFirstGamePlayed.getYear() + 1900;
		final int lastYear = now.getYear() + 1900;
		populateValueListBox(getDisplay().getYearSelect(), firstYear, lastYear, true);
		ValueListBox<Integer> monthYearSelect = getDisplay().getMonthYearSelect();
		populateValueListBox(monthYearSelect, firstYear, lastYear, false);
		populateMonths(dateFirstGamePlayed, now, monthYearSelect.getValue(), firstYear, lastYear);
		monthYearSelect.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				int selectedYear = event.getValue();
				populateMonths(dateFirstGamePlayed, now, selectedYear, firstYear, lastYear);
			}
		});
	}

	protected void populateValueListBox(ValueListBox<Integer> valueListBox, int min, int max, boolean reverse) {
		List<Integer> values = new ArrayList<Integer>();
		for (int value = min; value <= max; value++) {
			values.add(value);
		}
		if (reverse) {
			Lists.reverse(values);
		}
		populateValueListBox(valueListBox, values);
	}
	
	protected <V> void populateValueListBox(ValueListBox<V> valueListBox, List<V> values) {
		V currentValue = valueListBox.getValue();
		valueListBox.setValue(values.get(values.size() - 1));
		valueListBox.setAcceptableValues(values.subList(0, values.size() - 1));
		if (currentValue != null && values.contains(values)) {
			valueListBox.setValue(currentValue);
		}
	}

	@SuppressWarnings("deprecation")
	protected void populateMonths(Date dateFirstGamePlayed, Date now, int selectedYear, int firstYear, int lastYear) {
		int firstMonth = 0;
		int lastMonth = 11;
		if (selectedYear == firstYear) {
			firstMonth = dateFirstGamePlayed.getMonth();
		}
		else if (selectedYear == lastYear) {
			lastMonth = now.getMonth();
		}
		populateValueListBox(getDisplay().getMonthSelect(), firstMonth, lastMonth, false);
	}
	
	protected void showDatePickers(Date dateFirstGamePlayed, final Date now) {
		final Display display = getDisplay();
		// From & To datepickers
		DatePickingLink fromDatePickingLink = null;
		DatePickingLink toDatePickingLink = null;
		Date oneWeekAgo = now;
		CalendarUtil.addDaysToDate(oneWeekAgo, -7);
		fromDatePickingLink = new DatePickingLink(
				display.getFromAnchor(), dateFirstGamePlayed, now, oneWeekAgo, display.getBetweenButton(), toDatePickingLink) {
			@Override
			protected void updateOnClose(DatePickingLink toDatePickingLink, Date date) {
				Date latestDate = CalendarUtil.copyDate(getDate());
				CalendarUtil.addDaysToDate(latestDate, -1);
				toDatePickingLink.setLatestDate(latestDate);
			}
		};
		setFromDatePickingLink(fromDatePickingLink);
		toDatePickingLink = new DatePickingLink(
				display.getToAnchor(), dateFirstGamePlayed, now, now, display.getBetweenButton(), fromDatePickingLink) {
			@Override
			protected void updateOnClose(DatePickingLink fromDatePickingLink, Date date) {
				Date earliestDate = CalendarUtil.copyDate(getDate());
				CalendarUtil.addDaysToDate(earliestDate, 1);
				fromDatePickingLink.setEarliestDate(earliestDate);
			}
		};
		setToDatePickingLink(toDatePickingLink);
		// Before date picker
		setBeforeDatePickingLink(
				new DatePickingLink(display.getBeforeAnchor(), dateFirstGamePlayed, now, now, display.getBeforeButton()));
		// Since date picker
		setSinceDatePickingLink(
				new DatePickingLink(display.getSinceAnchor(), dateFirstGamePlayed, now, now, display.getSinceButton()));
		// Week date picker
		setWeekDatePickingLink(
				new DatePickingLink(display.getWeekAnchor(), dateFirstGamePlayed, now, now, display.getWeekButton()));
	}
	
	protected void updateValues() {
		Place roktaPlace = getPlaceController().getWhere();
		GameFilter gameFilter;
		if (roktaPlace instanceof GameFilterAwarePlace) {
			gameFilter = ((GameFilterAwarePlace) roktaPlace).getGameFilter();
		}
		else {
			gameFilter = new YearGameFilter(new NoOpModifier(), new Date());
		}
		final Display display = getDisplay();
		GameFilterVisitor<HasValue<Boolean>> gameFilterVisitor = new GameFilterVisitor<HasValue<Boolean>>() {
			public HasValue<Boolean> visit(BeforeGameFilter beforeGameFilter) {
				getBeforeDatePickingLink().setDate(beforeGameFilter.getDate());
				return display.getBeforeButton();
			}
			public HasValue<Boolean> visit(BetweenGameFilter betweenGameFilter) {
				getFromDatePickingLink().setDate(betweenGameFilter.getFrom());
				getToDatePickingLink().setDate(betweenGameFilter.getTo());
				return display.getBetweenButton();
			}
			public HasValue<Boolean> visit(SinceGameFilter sinceGameFilter) {
				getSinceDatePickingLink().setDate(sinceGameFilter.getDate());
				return display.getSinceButton();
			}
			@SuppressWarnings("deprecation")
			public HasValue<Boolean> visit(MonthGameFilter monthGameFilter) {
				Date date = monthGameFilter.getDate();
				display.getMonthYearSelect().setValue(date.getYear() + 1900, true);
				display.getMonthSelect().setValue(date.getMonth());
				return display.getMonthButton();
			}
			public HasValue<Boolean> visit(WeekGameFilter weekGameFilter) {
				getWeekDatePickingLink().setDate(weekGameFilter.getDate());
				return display.getWeekButton();
			}
			@SuppressWarnings("deprecation")
			public HasValue<Boolean> visit(YearGameFilter yearGameFilter) {
				display.getYearSelect().setValue(yearGameFilter.getDate().getYear() + 1900);
				return display.getYearButton();
			}
			public HasValue<Boolean> visit(AllGameFilter allGameFilter) {
				return display.getAllButton();
			}
			public HasValue<Boolean> visit(GameFilter gameFilter) {
				return null;
			}
		};
		selectButton(gameFilter.accept(gameFilterVisitor));
		display.getModifierSelect().setValue(gameFilter.getModifier());
	}
	
	protected void selectButton(HasValue<Boolean> button) {
		if (button != null) {
			button.setValue(true);
		}
	}

	protected void initialiseFilterButtons(final Display display) {
		final Map<HasValue<Boolean>, GameFilterFactory> gameFilterFactories = new HashMap<HasValue<Boolean>, GameFilterFactory>();
		gameFilterFactories.put(display.getAllButton(), new GameFilterFactory() {
			public GameFilter createGameFilter(Modifier modifier) {
				return new AllGameFilter(modifier);
			}
		});
		gameFilterFactories.put(display.getBeforeButton(), new GameFilterFactory() {
			public GameFilter createGameFilter(Modifier modifier) {
				return new BeforeGameFilter(modifier, getBeforeDatePickingLink().getDate());
			}
		});
		gameFilterFactories.put(display.getBetweenButton(), new GameFilterFactory() {
			public GameFilter createGameFilter(Modifier modifier) {
				return new BetweenGameFilter(modifier, getFromDatePickingLink().getDate(), getToDatePickingLink().getDate());
			}
		});
		gameFilterFactories.put(display.getMonthButton(), new GameFilterFactory() {
			@SuppressWarnings("deprecation")
			public GameFilter createGameFilter(Modifier modifier) {
				return new MonthGameFilter(modifier, new Date(display.getMonthYearSelect().getValue() - 1900, display.getMonthSelect().getValue(), 1));
			}
		});
		gameFilterFactories.put(display.getSinceButton(), new GameFilterFactory() {
			public GameFilter createGameFilter(Modifier modifier) {
				return new SinceGameFilter(modifier, getSinceDatePickingLink().getDate());
			}
		});
		gameFilterFactories.put(display.getWeekButton(), new GameFilterFactory() {
			public GameFilter createGameFilter(Modifier modifier) {
				return new WeekGameFilter(modifier, getWeekDatePickingLink().getDate());
			}
		});
		gameFilterFactories.put(display.getYearButton(), new GameFilterFactory() {
			@SuppressWarnings("deprecation")
			public GameFilter createGameFilter(Modifier modifier) {
				return new YearGameFilter(modifier, new Date(display.getYearSelect().getValue() - 1900, 0, 1));
			}
		});
		final Predicate<HasValue<Boolean>> selectionFindingPredicate = new Predicate<HasValue<Boolean>>() {
			@Override
			public boolean apply(HasValue<Boolean> hasValue) {
				return hasValue.getValue().booleanValue();
			}
		};
		ClickHandler submitHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Modifier modifier = display.getModifierSelect().getValue();
				GameFilter gameFilter = getValue(gameFilterFactories).createGameFilter(modifier);
				Place where = getPlaceController().getWhere();
				Place newPlace;
				if (where instanceof GameFilterAwarePlace) {
					newPlace = ((GameFilterAwarePlace) where).withGameFilter(gameFilter);
				}
				else {
					newPlace = new LeaguePlace(gameFilter);
				}
				getPlaceController().goTo(newPlace);
				hide();
			}
			
			protected <V> V getValue(Map<HasValue<Boolean>, V> valueMap) {
				HasValue<Boolean> selectedKey = Iterables.find(valueMap.keySet(), selectionFindingPredicate);
				return valueMap.get(selectedKey);
			}
		};
		display.getSubmitButton().addClickHandler(submitHandler);
		
		ClickHandler cancelHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		};
		display.getCancelButton().addClickHandler(cancelHandler);
	}

	protected void initialiseModifierSelect(Display display) {
		List<Modifier> modifiers = new ArrayList<Modifier>();
		modifiers.add(new NoOpModifier());
		modifiers.add(new FirstGameOfTheDayModifier());
		modifiers.add(new LastGameOfTheDayModifier());
		modifiers.add(new FirstGameOfTheWeekModifier());
		modifiers.add(new LastGameOfTheWeekModifier());
		modifiers.add(new FirstGameOfTheMonthModifier());
		modifiers.add(new LastGameOfTheMonthModifier());
		modifiers.add(new FirstGameOfTheYearModifier());
		modifiers.add(new LastGameOfTheYearModifier());
		ValueListBox<Modifier> modifierListBox = display.getModifierSelect();
		populateValueListBox(modifierListBox, modifiers);
	}
	
	interface GameFilterFactory {
		public GameFilter createGameFilter(Modifier modifier);
	}
	
	public Display getDisplay() {
		return i_display;
	}

	public PlaceController getPlaceController() {
		return i_placeController;
	}

	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}

	public DatePickingLink getBeforeDatePickingLink() {
		return i_beforeDatePickingLink;
	}

	public void setBeforeDatePickingLink(DatePickingLink beforeDatePickingLink) {
		i_beforeDatePickingLink = beforeDatePickingLink;
	}

	public DatePickingLink getSinceDatePickingLink() {
		return i_sinceDatePickingLink;
	}

	public void setSinceDatePickingLink(DatePickingLink sinceDatePickingLink) {
		i_sinceDatePickingLink = sinceDatePickingLink;
	}

	public DatePickingLink getFromDatePickingLink() {
		return i_fromDatePickingLink;
	}

	public void setFromDatePickingLink(DatePickingLink fromDatePickingLink) {
		i_fromDatePickingLink = fromDatePickingLink;
	}

	public DatePickingLink getToDatePickingLink() {
		return i_toDatePickingLink;
	}

	public void setToDatePickingLink(DatePickingLink toDatePickingLink) {
		i_toDatePickingLink = toDatePickingLink;
	}

	public DatePickingLink getWeekDatePickingLink() {
		return i_weekDatePickingLink;
	}

	public void setWeekDatePickingLink(DatePickingLink weekDatePickingLink) {
		i_weekDatePickingLink = weekDatePickingLink;
	}
}
