/**
 * 
 */
package uk.co.unclealex.rokta.client.presenters;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.filter.GameFilterFactory;
import uk.co.unclealex.rokta.client.places.AdminPlace;
import uk.co.unclealex.rokta.client.places.GameFilterAwarePlace;
import uk.co.unclealex.rokta.client.places.GamePlace;
import uk.co.unclealex.rokta.client.places.GraphPlace;
import uk.co.unclealex.rokta.client.places.HeadToHeadsPlace;
import uk.co.unclealex.rokta.client.places.LeaguePlace;
import uk.co.unclealex.rokta.client.places.LosingStreaksPlace;
import uk.co.unclealex.rokta.client.places.ProfilePlace;
import uk.co.unclealex.rokta.client.places.RoktaPlace;
import uk.co.unclealex.rokta.client.places.RoktaPlaceVisitor;
import uk.co.unclealex.rokta.client.places.WinningStreaksPlace;
import uk.co.unclealex.rokta.client.presenters.NavigationPresenter.Display;
import uk.co.unclealex.rokta.client.security.AuthenticationManager;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

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
public class NavigationPresenter implements Presenter<Display>, PlaceChangeEvent.Handler, RoktaPlaceVisitor<HasClickHandlers> {

	public interface Display extends IsWidget {
		HasClickHandlers getNewGameLink();
		HasClickHandlers getLeagueLink();
		HasClickHandlers getGraphLink();
		HasClickHandlers getWinningStreaksLink();
		HasClickHandlers getLosingStreaksLink();
		HasClickHandlers getHeadToHeadsLink();
		HasClickHandlers getFiltersLink();
		HasClickHandlers getAdminLink();
		HasClickHandlers addProfileLink(String playerName);
		void addStyleName(HasClickHandlers widget);
		void removeStyleName(HasClickHandlers widget);
	}

	private final PlaceController i_placeController;
	private final Display i_display;
	private final AuthenticationManager i_authenticationManager;
	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	private final Provider<FiltersPresenter> i_filtersPresenterProvider;
	private final Map<String, HasClickHandlers> profileHandlersByPlayerName = Maps.newHashMap();
	
	@Inject
	public NavigationPresenter(PlaceController placeController, Display display,
			AsyncCallbackExecutor asyncCallbackExecutor, Provider<FiltersPresenter> filtersPresenterProvider,
			AuthenticationManager authenticationManager, EventBus eventBus) {
		super();
		i_placeController = placeController;
		i_display = display;
		i_filtersPresenterProvider = filtersPresenterProvider;
		i_asyncCallbackExecutor = asyncCallbackExecutor;
		i_authenticationManager = authenticationManager;
		eventBus.addHandler(PlaceChangeEvent.TYPE, this);
	}

	@Override
	public void show(final AcceptsOneWidget container) {
		ExecutableAsyncCallback<Date> callback = new FailureAsPopupExecutableAsyncCallback<Date>() {
			@Override
			public void onSuccess(Date dateFirstGamePlayed) {
				show(container, dateFirstGamePlayed);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Date> callback) {
				anonymousRoktaService.getDateFirstGamePlayed(callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Finding the date the first game was played");
	}
	
	public void show(AcceptsOneWidget container, Date dateFirstGamePlayed) {
		Display display = getDisplay();
		addNewGameLink(display);
		addGameFilterLinks(display);
		addFiltersLink(display);
		container.setWidget(display);
	}

	protected void addGameFilterLinks(final Display display) {
		final Map<HasClickHandlers, Function<GameFilter, RoktaPlace>> roktaPlaceLinkMap = Maps.newHashMap();
		roktaPlaceLinkMap.put(
			display.getLeagueLink(),
			new Function<GameFilter, RoktaPlace>() { 
				public RoktaPlace apply(GameFilter gameFilter) { return new LeaguePlace(gameFilter); }});
		roktaPlaceLinkMap.put(
				display.getGraphLink(),
				new Function<GameFilter, RoktaPlace>() { 
					public RoktaPlace apply(GameFilter gameFilter) { return new GraphPlace(gameFilter); }});
		roktaPlaceLinkMap.put(
				display.getWinningStreaksLink(),
				new Function<GameFilter, RoktaPlace>() { 
					public RoktaPlace apply(GameFilter gameFilter) { return new WinningStreaksPlace(gameFilter); }});
		roktaPlaceLinkMap.put(
				display.getLosingStreaksLink(),
				new Function<GameFilter, RoktaPlace>() { 
					public RoktaPlace apply(GameFilter gameFilter) { return new LosingStreaksPlace(gameFilter); }});
		roktaPlaceLinkMap.put(
				display.getHeadToHeadsLink(),
				new Function<GameFilter, RoktaPlace>() { 
					public RoktaPlace apply(GameFilter gameFilter) { return new HeadToHeadsPlace(gameFilter); }});
		roktaPlaceLinkMap.put(
				display.getAdminLink(),
				new Function<GameFilter, RoktaPlace>() { 
					public RoktaPlace apply(GameFilter gameFilter) { return new AdminPlace(gameFilter); }});
		final Map<String, HasClickHandlers> profileHandlersByPlayerName = getProfileHandlersByPlayerName();
		ExecutableAsyncCallback<String[]> callback = new FailureAsPopupExecutableAsyncCallback<String[]>() {
			@Override
			public void onSuccess(String[] playerNames) {
				for (final String playerName : playerNames) {
					HasClickHandlers profileLink = display.addProfileLink(playerName);
					profileHandlersByPlayerName.put(playerName, profileLink);
					Function<GameFilter, RoktaPlace> function = new Function<GameFilter, RoktaPlace>() {
						@Override
						public RoktaPlace apply(GameFilter gameFilter) {
							return new ProfilePlace(gameFilter, playerName);
						}
					};
					roktaPlaceLinkMap.put(profileLink, function);
				}
				bindGameFilterLinks(roktaPlaceLinkMap);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<String[]> callback) {
				anonymousRoktaService.getAllPlayerNames(callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Getting all player names");
	}

	protected void bindGameFilterLinks(final Map<HasClickHandlers, Function<GameFilter, RoktaPlace>> roktaPlaceLinkMap) {
		ClickHandler gameFilterHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Place where = getPlaceController().getWhere();
				GameFilter gameFilter;
				if (where instanceof GameFilterAwarePlace) {
					gameFilter = ((GameFilterAwarePlace) where).getGameFilter();
				}
				else {
					gameFilter = GameFilterFactory.createDefaultGameFilter();
				}
				getPlaceController().goTo(roktaPlaceLinkMap.get(event.getSource()).apply(gameFilter));
			}
		};
		for (HasClickHandlers hasClickHandlers : roktaPlaceLinkMap.keySet()) {
			hasClickHandlers.addClickHandler(gameFilterHandler);
		}
	}

	protected void addFiltersLink(Display display) {
		ClickHandler filtersHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getFiltersPresenterProvider().get().center();
			}
		};
		display.getFiltersLink().addClickHandler(filtersHandler);
	}

	protected void addNewGameLink(Display display) {
		ClickHandler newGameHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getPlaceController().goTo(new GamePlace());
			}
		};
		display.getNewGameLink().addClickHandler(newGameHandler);
	}

	@Override
	public void onPlaceChange(PlaceChangeEvent event) {
		Place newPlace = event.getNewPlace();
		if (newPlace instanceof RoktaPlace) {
			HasClickHandlers selected = ((RoktaPlace) newPlace).accept(this);
			if (selected != null) {
				selectLink(selected);
			}
		}
	}

	protected void selectLink(HasClickHandlers selected) {
		Display display = getDisplay();
		HasClickHandlers[] handlers = new HasClickHandlers[] { 
				display.getAdminLink(), display.getFiltersLink(), display.getGraphLink(), display.getHeadToHeadsLink(), 
				display.getLeagueLink(), display.getLosingStreaksLink(), display.getNewGameLink(), 
				display.getWinningStreaksLink() };
		Iterable<HasClickHandlers> all = Iterables.concat(Arrays.asList(handlers), getProfileHandlersByPlayerName().values());
		for (HasClickHandlers hasClickHandlers : all) {
			if (hasClickHandlers == selected) {
				display.addStyleName(hasClickHandlers);
			}
			else {
				display.removeStyleName(hasClickHandlers);
			}
		}
	}

	@Override
	public HasClickHandlers visit(AdminPlace adminPlace) {
		return getDisplay().getAdminLink();
	}
	
	@Override
	public HasClickHandlers visit(GamePlace gamePlace) {
		return getDisplay().getNewGameLink();
	}
	
	@Override
	public HasClickHandlers visit(GraphPlace graphPlace) {
		return getDisplay().getGraphLink();
	}
	
	@Override
	public HasClickHandlers visit(HeadToHeadsPlace headToHeadsPlace) {
		return getDisplay().getHeadToHeadsLink();
	}
	
	@Override
	public HasClickHandlers visit(LeaguePlace leaguePlace) {
		return getDisplay().getLeagueLink();
	}
	
	@Override
	public HasClickHandlers visit(LosingStreaksPlace losingStreaksPlace) {
		return getDisplay().getLosingStreaksLink();
	}
	
	@Override
	public HasClickHandlers visit(ProfilePlace profilePlace) {
		return getProfileHandlersByPlayerName().get(profilePlace.getUsername());
	}
	
	@Override
	public HasClickHandlers visit(RoktaPlace roktaPlace) {
		throw new IllegalArgumentException(roktaPlace.getClass().getName());
	}
	
	@Override
	public HasClickHandlers visit(WinningStreaksPlace winningStreaksPlace) {
		return getDisplay().getWinningStreaksLink();
	}
	
	public Display getDisplay() {
		return i_display;
	}

	public AuthenticationManager getAuthenticationManager() {
		return i_authenticationManager;
	}

	public PlaceController getPlaceController() {
		return i_placeController;
	}

	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}

	public Provider<FiltersPresenter> getFiltersPresenterProvider() {
		return i_filtersPresenterProvider;
	}

	public Map<String, HasClickHandlers> getProfileHandlersByPlayerName() {
		return profileHandlersByPlayerName;
	}

}
