/**
 * 
 */
package uk.co.unclealex.rokta.client.gin;

import javax.inject.Singleton;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.cache.InformationCacheImpl;
import uk.co.unclealex.rokta.client.factories.GameFinishedPresenterFactory;
import uk.co.unclealex.rokta.client.factories.GamePresenterFactory;
import uk.co.unclealex.rokta.client.factories.GraphPresenterFactory;
import uk.co.unclealex.rokta.client.factories.HandCountPresenterFactory;
import uk.co.unclealex.rokta.client.factories.HeadToHeadsPresenterFactory;
import uk.co.unclealex.rokta.client.factories.LeaguePresenterFactory;
import uk.co.unclealex.rokta.client.factories.LoginPresenterFactory;
import uk.co.unclealex.rokta.client.factories.LoginPresenterFactoryImpl;
import uk.co.unclealex.rokta.client.factories.LosingStreaksPresenterFactory;
import uk.co.unclealex.rokta.client.factories.NextRoundPresenterFactory;
import uk.co.unclealex.rokta.client.factories.ProfilePresenterFactory;
import uk.co.unclealex.rokta.client.factories.StreaksTablePresenterFactory;
import uk.co.unclealex.rokta.client.factories.WinningStreaksPresenterFactory;
import uk.co.unclealex.rokta.client.presenters.FiltersPresenter;
import uk.co.unclealex.rokta.client.presenters.GameFinishedPresenter;
import uk.co.unclealex.rokta.client.presenters.GamePresenter;
import uk.co.unclealex.rokta.client.presenters.GraphPresenter;
import uk.co.unclealex.rokta.client.presenters.HandCountPresenter;
import uk.co.unclealex.rokta.client.presenters.HeadToHeadsPresenter;
import uk.co.unclealex.rokta.client.presenters.LeaguePresenter;
import uk.co.unclealex.rokta.client.presenters.LoginPresenter;
import uk.co.unclealex.rokta.client.presenters.LosingStreaksPresenter;
import uk.co.unclealex.rokta.client.presenters.MainPresenter;
import uk.co.unclealex.rokta.client.presenters.NavigationPresenter;
import uk.co.unclealex.rokta.client.presenters.NewGamePresenter;
import uk.co.unclealex.rokta.client.presenters.NextRoundPresenter;
import uk.co.unclealex.rokta.client.presenters.ProfilePresenter;
import uk.co.unclealex.rokta.client.presenters.StreaksPresenter;
import uk.co.unclealex.rokta.client.presenters.StreaksTablePresenter;
import uk.co.unclealex.rokta.client.presenters.TitlePresenter;
import uk.co.unclealex.rokta.client.presenters.WinningStreaksPresenter;
import uk.co.unclealex.rokta.client.util.ClickHelper;
import uk.co.unclealex.rokta.client.util.ClickHelperImpl;
import uk.co.unclealex.rokta.client.util.TitleManager;
import uk.co.unclealex.rokta.client.util.WaitingController;
import uk.co.unclealex.rokta.client.util.WaitingControllerImpl;
import uk.co.unclealex.rokta.client.views.Filters;
import uk.co.unclealex.rokta.client.views.Game;
import uk.co.unclealex.rokta.client.views.GameFinished;
import uk.co.unclealex.rokta.client.views.Graph;
import uk.co.unclealex.rokta.client.views.HandCount;
import uk.co.unclealex.rokta.client.views.HeadToHeads;
import uk.co.unclealex.rokta.client.views.League;
import uk.co.unclealex.rokta.client.views.Login;
import uk.co.unclealex.rokta.client.views.Main;
import uk.co.unclealex.rokta.client.views.Navigation;
import uk.co.unclealex.rokta.client.views.NewGame;
import uk.co.unclealex.rokta.client.views.NextRound;
import uk.co.unclealex.rokta.client.views.Profile;
import uk.co.unclealex.rokta.client.views.Streaks;
import uk.co.unclealex.rokta.client.views.StreaksTable;
import uk.co.unclealex.rokta.client.views.Title;
import uk.co.unclealex.rokta.client.visualisation.Visualisation;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

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
public class RoktaClientModule extends AbstractGinModule {

	@Override
	protected void configure() {
		
		bind(WaitingController.class).to(WaitingControllerImpl.class).in(Singleton.class);
		
		bind(MainPresenter.Display.class).to(Main.class).in(Singleton.class);
		bind(MainPresenter.class).in(Singleton.class);
		
		bind(TitlePresenter.Display.class).to(Title.class).in(Singleton.class);
		bind(TitlePresenter.class).in(Singleton.class);
		bind(TitleManager.class).to(TitlePresenter.class).in(Singleton.class);
		
		bind(NavigationPresenter.Display.class).to(Navigation.class).in(Singleton.class);
		bind(NavigationPresenter.class).in(Singleton.class);

		bind(LoginPresenter.Display.class).to(Login.class);
		bind(LoginPresenterFactory.class).to(LoginPresenterFactoryImpl.class);

		bind(ClickHelper.class).to(ClickHelperImpl.class);

		bind(FiltersPresenter.Display.class).to(Filters.class);
		bind(FiltersPresenter.class);
		
		bindPresenterWithDisplay(
			GamePresenter.Display.class, Game.class, 
			GamePresenter.class, GamePresenterFactory.class);
		
		bindPresenterWithDisplay(
				ProfilePresenter.Display.class, Profile.class, 
				ProfilePresenter.class, ProfilePresenterFactory.class);

		bindPresenterWithDisplay(
				LeaguePresenter.Display.class, League.class, 
				LeaguePresenter.class, LeaguePresenterFactory.class);
		
		bindPresenterWithDisplay(
				GraphPresenter.Display.class, Graph.class, 
				GraphPresenter.class, GraphPresenterFactory.class);
		
		bindPresenterWithDisplay(
				StreaksTablePresenter.Display.class, StreaksTable.class, 
				StreaksTablePresenter.class, StreaksTablePresenterFactory.class);

		bindDisplay(StreaksPresenter.Display.class, Streaks.class);
		
		bindPresenter(
				WinningStreaksPresenter.class, WinningStreaksPresenterFactory.class);

		bindPresenter(
				LosingStreaksPresenter.class, LosingStreaksPresenterFactory.class);

		bindPresenterWithDisplay(
				HeadToHeadsPresenter.Display.class, HeadToHeads.class, 
				HeadToHeadsPresenter.class, HeadToHeadsPresenterFactory.class);
		
		bindPresenterWithDisplay(
				HandCountPresenter.Display.class, HandCount.class,
				HandCountPresenter.class, HandCountPresenterFactory.class);
		
		bind(NewGamePresenter.class);
		bindDisplay(NewGamePresenter.Display.class, NewGame.class);
		
		bindPresenterWithDisplay(
				NextRoundPresenter.Display.class, NextRound.class, 
				NextRoundPresenter.class, NextRoundPresenterFactory.class);

		bindPresenterWithDisplay(
				GameFinishedPresenter.Display.class, GameFinished.class, 
				GameFinishedPresenter.class, GameFinishedPresenterFactory.class);
		
		bind(Visualisation.class);
		
		bind(InformationCache.class).to(InformationCacheImpl.class).in(Singleton.class);
	}

	protected <D, P> void bindPresenterWithDisplay(
			Class<D> displayInterface, Class<? extends D> displayImplementation, 
			Class<P> presenterImplementation, Class<?> factoryInterface) {
    bindDisplay(displayInterface, displayImplementation);
    bindPresenter(presenterImplementation, factoryInterface);

	}

	protected <P> void bindPresenter(Class<P> presenterImplementation, Class<?> factoryInterface) {
		install(new GinFactoryModuleBuilder().implement(presenterImplementation, presenterImplementation).
        build(factoryInterface));
	}

	protected <D> void bindDisplay(Class<D> displayInterface, Class<? extends D> displayImplementation) {
		bind(displayInterface).to(displayImplementation);
	}
}
