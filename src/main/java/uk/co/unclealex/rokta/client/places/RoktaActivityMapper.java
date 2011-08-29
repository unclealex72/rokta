/**
 * 
 */
package uk.co.unclealex.rokta.client.places;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.factories.AdminPresenterFactory;
import uk.co.unclealex.rokta.client.factories.GamePresenterFactory;
import uk.co.unclealex.rokta.client.factories.GraphPresenterFactory;
import uk.co.unclealex.rokta.client.factories.HeadToHeadsPresenterFactory;
import uk.co.unclealex.rokta.client.factories.LeaguePresenterFactory;
import uk.co.unclealex.rokta.client.factories.LosingStreaksPresenterFactory;
import uk.co.unclealex.rokta.client.factories.ProfilePresenterFactory;
import uk.co.unclealex.rokta.client.factories.WinningStreaksPresenterFactory;
import uk.co.unclealex.rokta.client.presenters.MainPresenter;
import uk.co.unclealex.rokta.client.util.TitleManager;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
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
public class RoktaActivityMapper implements ActivityMapper {

	private final LeaguePresenterFactory i_leaguePresenterFactory;
	private final GraphPresenterFactory i_graphPresenterFactory;
	private final WinningStreaksPresenterFactory i_winningStreaksPresenterFactory;
	private final LosingStreaksPresenterFactory i_losingStreaksPresenterFactory;
	private final HeadToHeadsPresenterFactory i_headToHeadsPresenterFactory;
	private final AdminPresenterFactory i_adminPresenterFactory;
	private final Provider<MainPresenter> i_mainPresenterProvider;
	private final GamePresenterFactory i_gamePresenterFactory;
	private final ProfilePresenterFactory i_profilePresenterFactory;
	private final TitleManager i_titleManager;
	
	@Inject
	public RoktaActivityMapper(LeaguePresenterFactory leaguePresenterFactory,
			GraphPresenterFactory graphPresenterFactory, WinningStreaksPresenterFactory winningStreaksPresenterFactory,
			LosingStreaksPresenterFactory losingStreaksPresenterFactory,
			HeadToHeadsPresenterFactory headToHeadsPresenterFactory, AdminPresenterFactory adminPresenterFactory,
			Provider<MainPresenter> mainPresenterProvider, GamePresenterFactory gamePresenterFactory,
			ProfilePresenterFactory profilePresenterFactory, TitleManager titleManager) {
		super();
		i_leaguePresenterFactory = leaguePresenterFactory;
		i_graphPresenterFactory = graphPresenterFactory;
		i_winningStreaksPresenterFactory = winningStreaksPresenterFactory;
		i_losingStreaksPresenterFactory = losingStreaksPresenterFactory;
		i_headToHeadsPresenterFactory = headToHeadsPresenterFactory;
		i_mainPresenterProvider = mainPresenterProvider;
		i_gamePresenterFactory = gamePresenterFactory;
		i_profilePresenterFactory = profilePresenterFactory;
		i_titleManager = titleManager;
		i_adminPresenterFactory = adminPresenterFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof RoktaPlace) {
			getTitleManager().updateTitle((RoktaPlace) place);
		}
		return new ActivityProvider(place).asActivity();
	}
	
	protected class ActivityProvider implements RoktaPlaceVisitor<Activity>, Activity {

		private final Place i_place;
		private Activity i_activity;
		
		public ActivityProvider(Place place) {
			super();
			i_place = place;
		}

		public Activity asActivity() {
			Place place = getPlace();
			if (place instanceof RoktaPlace) {
				setActivity(((RoktaPlace) place).accept(this));
			}
			else {
				asDefault();
			}
			return this;
		}
		
		@Override
		public boolean equals(Object obj) {
			return (obj instanceof ActivityProvider) && (getPlace().equals(((ActivityProvider) obj).getPlace()));
		}
		
		@Override
		public String mayStop() {
			return getActivity().mayStop();
		}

		@Override
		public void onCancel() {
			getActivity().onCancel();
		}

		@Override
		public void onStop() {
			getActivity().onStop();
		}

		@Override
		public void start(AcceptsOneWidget panel, EventBus eventBus) {
			getActivity().start(panel, eventBus);
		}

		public Activity asDefault() {
			//TODO Make sure default is set correctly.
			return null;
		}
		
		@Override
		public Activity visit(RoktaPlace roktaPlace) {

			return asDefault();
		}

		@Override
		public Activity visit(LeaguePlace leaguePlace) {
			return getLeaguePresenterFactory().createLeaguePresenter(leaguePlace.getGameFilter());
		}
		
		@Override
		public Activity visit(GraphPlace graphPlace) {
			return getGraphPresenterFactory().createGraphPresenter(graphPlace.getGameFilter());
		}

		@Override
		public Activity visit(WinningStreaksPlace winningStreaksPlace) {
			return getWinningStreaksPresenterFactory().createWinningStreaksPresenter(winningStreaksPlace.getGameFilter());
		}

		@Override
		public Activity visit(LosingStreaksPlace losingStreaksPlace) {
			return getLosingStreaksPresenterFactory().createLosingStreaksPresenter(losingStreaksPlace.getGameFilter());
		}

		@Override
		public Activity visit(HeadToHeadsPlace headToHeadsPlace) {
			return getHeadToHeadsPresenterFactory().createHeadToHeadsPresenter(headToHeadsPlace.getGameFilter());
		}

		@Override
		public Activity visit(AdminPlace adminPlace) {
			return getAdminPresenterFactory().createAdminPresenter(adminPlace.getGameFilter());
		}

		@Override
		public Activity visit(GamePlace gamePlace) {
			return getGamePresenterFactory().createGamePresenter(gamePlace.getGame());
		}

		@Override
		public Activity visit(ProfilePlace profilePlace) {
			return getProfilePresenterFactory().createProfilePresenter(profilePlace.getGameFilter(), profilePlace.getUsername());
		}
		
		public Activity getActivity() {
			return i_activity;
		}

		public void setActivity(Activity activity) {
			i_activity = activity;
		}

		public Place getPlace() {
			return i_place;
		}

	}

	public Provider<MainPresenter> getMainPresenterProvider() {
		return i_mainPresenterProvider;
	}

	public GamePresenterFactory getGamePresenterFactory() {
		return i_gamePresenterFactory;
	}

	public ProfilePresenterFactory getProfilePresenterFactory() {
		return i_profilePresenterFactory;
	}

	public LeaguePresenterFactory getLeaguePresenterFactory() {
		return i_leaguePresenterFactory;
	}

	public GraphPresenterFactory getGraphPresenterFactory() {
		return i_graphPresenterFactory;
	}

	public WinningStreaksPresenterFactory getWinningStreaksPresenterFactory() {
		return i_winningStreaksPresenterFactory;
	}

	public LosingStreaksPresenterFactory getLosingStreaksPresenterFactory() {
		return i_losingStreaksPresenterFactory;
	}

	public HeadToHeadsPresenterFactory getHeadToHeadsPresenterFactory() {
		return i_headToHeadsPresenterFactory;
	}

	public TitleManager getTitleManager() {
		return i_titleManager;
	}

	public AdminPresenterFactory getAdminPresenterFactory() {
		return i_adminPresenterFactory;
	}

}
