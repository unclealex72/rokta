/**
 * 
 */
package uk.co.unclealex.rokta.client.places;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.AdminPresenter;
import uk.co.unclealex.rokta.client.presenters.GamePresenter;
import uk.co.unclealex.rokta.client.presenters.HeadToHeadsPresenter;
import uk.co.unclealex.rokta.client.presenters.LeaguePresenter;
import uk.co.unclealex.rokta.client.presenters.LosingStreaksPresenter;
import uk.co.unclealex.rokta.client.presenters.MainPresenter;
import uk.co.unclealex.rokta.client.presenters.ProfilePresenter;
import uk.co.unclealex.rokta.client.presenters.WinningStreaksPresenter;

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

	private final Provider<LeaguePresenter> i_leaguePresenterProvider;
	private final Provider<WinningStreaksPresenter> i_winningStreaksPresenterProvider;
	private final Provider<LosingStreaksPresenter> i_losingStreaksPresenterProvider;
	private final Provider<HeadToHeadsPresenter> i_headToHeadsPresenterProvider;
	private final Provider<AdminPresenter> i_adminPresenterProvider;
	private final Provider<MainPresenter> i_mainPresenterProvider;
	private final Provider<GamePresenter> i_gamePresenterProvider;
	private final Provider<ProfilePresenter> i_profilePresenterProvider;

	@Inject
	public RoktaActivityMapper(Provider<LeaguePresenter> leaguePresenterProvider,
			Provider<WinningStreaksPresenter> winningStreaksPresenterProvider,
			Provider<LosingStreaksPresenter> losingStreaksPresenterProvider,
			Provider<HeadToHeadsPresenter> headToHeadsPresenterProvider, Provider<AdminPresenter> adminPresenterProvider,
			Provider<MainPresenter> mainPresenterProvider, Provider<GamePresenter> gamePresenterProvider,
			Provider<ProfilePresenter> profilePresenterProvider) {
		super();
		i_leaguePresenterProvider = leaguePresenterProvider;
		i_winningStreaksPresenterProvider = winningStreaksPresenterProvider;
		i_losingStreaksPresenterProvider = losingStreaksPresenterProvider;
		i_headToHeadsPresenterProvider = headToHeadsPresenterProvider;
		i_adminPresenterProvider = adminPresenterProvider;
		i_mainPresenterProvider = mainPresenterProvider;
		i_gamePresenterProvider = gamePresenterProvider;
		i_profilePresenterProvider = profilePresenterProvider;
	}
	
	@Override
	public Activity getActivity(Place place) {
		return new ActivityProvider(place).asActivity();
	}
	
	protected class ActivityProvider implements RoktaPlaceVisitor, Activity {

		private final Place i_place;
		private Activity i_activity;
		
		public ActivityProvider(Place place) {
			super();
			i_place = place;
		}

		public Activity asActivity() {
			Place place = getPlace();
			if (place instanceof RoktaPlace) {
				((RoktaPlace) place).accept(this);
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

		public void asDefault() {
			//new MainPlace().accept(this);
		}
		
		@Override
		public void visit(RoktaPlace roktaPlace) {
			asDefault();
		}

		@Override
		public void visit(LeaguePlace leaguePlace) {
			setActivity(getLeaguePresenterProvider().get());
		}

		@Override
		public void visit(WinningStreaksPlace winningStreaksPlace) {
			setActivity(getWinningStreaksPresenterProvider().get());
		}

		@Override
		public void visit(LosingStreaksPlace losingStreaksPlace) {
			setActivity(getLosingStreaksPresenterProvider().get());
		}

		@Override
		public void visit(HeadToHeadsPlace headToHeadsPlace) {
			setActivity(getHeadToHeadsPresenterProvider().get());
		}

		@Override
		public void visit(AdminPlace adminPlace) {
			setActivity(getAdminPresenterProvider().get());
		}

		@Override
		public void visit(GamePlace gamePlace) {
			setActivity(getGamePresenterProvider().get());
		}

		@Override
		public void visit(ProfilePlace profilePlace) {
			setActivity(getProfilePresenterProvider().get());
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

	public Provider<LeaguePresenter> getLeaguePresenterProvider() {
		return i_leaguePresenterProvider;
	}

	public Provider<AdminPresenter> getAdminPresenterProvider() {
		return i_adminPresenterProvider;
	}

	public Provider<WinningStreaksPresenter> getWinningStreaksPresenterProvider() {
		return i_winningStreaksPresenterProvider;
	}

	public Provider<LosingStreaksPresenter> getLosingStreaksPresenterProvider() {
		return i_losingStreaksPresenterProvider;
	}

	public Provider<HeadToHeadsPresenter> getHeadToHeadsPresenterProvider() {
		return i_headToHeadsPresenterProvider;
	}

	public Provider<MainPresenter> getMainPresenterProvider() {
		return i_mainPresenterProvider;
	}

	public Provider<GamePresenter> getGamePresenterProvider() {
		return i_gamePresenterProvider;
	}

	public Provider<ProfilePresenter> getProfilePresenterProvider() {
		return i_profilePresenterProvider;
	}
}
