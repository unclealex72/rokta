package uk.co.unclealex.rokta.client.presenters;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.presenters.AdminPresenter.Display;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.CanWait;
import uk.co.unclealex.rokta.client.util.ClickHandlerAndFailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.model.Colour;
import uk.co.unclealex.rokta.shared.model.GameSummary;
import uk.co.unclealex.rokta.shared.model.LoggedInUser;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

public class AdminPresenter extends AbstractGameFilterActivity<Display> {

	public static interface Display extends IsWidget {
		void addColourGroup(Colour.Group colourGroup);
		HasClickHandlers addColour(Colour colour);
		void deselectColour(HasClickHandlers colourSource);
		void selectColour(HasClickHandlers colourSource);
		Button getChangeColourButton();
		CanWait getChangeColourCanWait();

		CanWait getDeleteLastGameCanWait();
		HasClickHandlers getDeleteLastGameButton();

		CanWait getClearCacheCanWait();
		HasClickHandlers getClearCacheButton();
}

	private final Display i_display;
	private final TitleMessages i_titleMessages;
	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	private InformationCache i_informationCache;
	private Colour i_selectedColour;
	private Map<Colour, HasClickHandlers> i_hasClickHandlersByColour;
	
	@Inject
	public AdminPresenter(@Assisted GameFilter gameFilter, 
			Display display, AsyncCallbackExecutor asyncCallbackExecutor, InformationCache informationCache, TitleMessages titleMessages) {
		super(gameFilter);
		i_titleMessages = titleMessages;
		i_display = display;
		i_informationCache = informationCache;
		i_asyncCallbackExecutor = asyncCallbackExecutor;
	}


	@Override
	public void start(GameFilter gameFilter, final AcceptsOneWidget panel, EventBus eventBus) {
		ExecutableAsyncCallback<LoggedInUser> callback = new FailureAsPopupExecutableAsyncCallback<LoggedInUser>() {
			@Override
			public void onSuccess(LoggedInUser loggedInUser) {
				initialiseColours(loggedInUser);
				initialiseColoursButton(loggedInUser);
				initialiseGameAdminButtons();
				panel.setWidget(getDisplay());
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<LoggedInUser> callback) {
				userRoktaService.getCurrentlyLoggedInUser(callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Checking who is currently logged in");
	}


	protected void initialiseGameAdminButtons() {
		Display display = getDisplay();
		ClickHandler deleteLastGameClickHandler = 
				new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<GameSummary>(
			getAsyncCallbackExecutor(), "Getting details for the last game played", display.getDeleteLastGameCanWait()) {
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<GameSummary> callback) {
				anonymousRoktaService.getLastGameSummary(callback);
			}
			@Override
			public void onSuccess(GameSummary gameSummary) {
				if (Window.confirm(getTitleMessages().deleteLastGame(gameSummary.getDatePlayed(), gameSummary.getLoser()))) {
					deleteLastGame();
				}
			}
		};
		display.getDeleteLastGameButton().addClickHandler(deleteLastGameClickHandler);
		ClickHandler clearCacheClickHandler = 
				new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<Void>(
				getAsyncCallbackExecutor(), "Clearing the cache", display.getClearCacheCanWait()) {
				@Override
				public void onSuccess(Void result) {
					getInformationCache().clearCache();
				}
				@Override
				public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
						AsyncCallback<Void> callback) {
					userRoktaService.clearCache(callback);
				}
			};
			display.getClearCacheButton().addClickHandler(clearCacheClickHandler);
	}


	protected void deleteLastGame() {
		ExecutableAsyncCallback<Void> callback = new FailureAsPopupExecutableAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Void> callback) {
				userRoktaService.removeLastGame(callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Removing the last game", getDisplay().getDeleteLastGameCanWait());
	}


	protected void initialiseColours(final LoggedInUser loggedInUser) {
		SortedMap<Colour.Group, Collection<Colour>> map = Maps.newTreeMap();
		Supplier<SortedSet<Colour>> factory = new Supplier<SortedSet<Colour>>() {
			@Override
			public SortedSet<Colour> get() {
				return Sets.newTreeSet();
			}
		};
		SortedSetMultimap<Colour.Group, Colour> coloursByGroup = Multimaps.newSortedSetMultimap(map, factory);
		for (Colour colour : Colour.values()) {
			coloursByGroup.put(colour.getGroup(), colour);
		}
		final Display display = getDisplay();
		final BiMap<HasClickHandlers, Colour> coloursByHasClickHandler = HashBiMap.create();
		ClickHandler handler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				HasClickHandlers hasClickHandlers = (HasClickHandlers) event.getSource();
				onColourSelected(coloursByHasClickHandler.get(hasClickHandlers));
			}
		};
		for (Entry<Colour.Group, Collection<Colour>> entry : map.entrySet()) {
			display.addColourGroup(entry.getKey());
			for (Colour colour : entry.getValue()) {
				HasClickHandlers hasClickHandlers = display.addColour(colour);
				hasClickHandlers.addClickHandler(handler);
				coloursByHasClickHandler.put(hasClickHandlers, colour);
			}
		}
		setHasClickHandlersByColour(coloursByHasClickHandler.inverse());
		onColourSelected(loggedInUser.getColour());
	}

	void onColourSelected(Colour colour) {
		Colour currentlySelectedColour = getSelectedColour();
		Display display = getDisplay();
		if (currentlySelectedColour != null) {
			display.deselectColour(getHasClickHandlersByColour().get(currentlySelectedColour));
		}
		display.selectColour(getHasClickHandlersByColour().get(colour));
		setSelectedColour(colour);
	}
	
	protected void initialiseColoursButton(final LoggedInUser loggedInUser) {
		final Display display = getDisplay();
		final String username = loggedInUser.getUsername();
		ClickHandler clickHandler = new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<Void>(
				getAsyncCallbackExecutor(),
				"Updating " + username + "'s colour to " + Joiner.on(' ').join(getSelectedColour().getDescriptiveWords()), 
				display.getChangeColourCanWait()) {
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Void> callback) {
				userRoktaService.updateColour(username, getSelectedColour(), callback);
			}
		};
		final Button changeColourButton = display.getChangeColourButton();
		changeColourButton.addClickHandler(clickHandler);
	}

	public Display getDisplay() {
		return i_display;
	}

	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}


	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}


	public Colour getSelectedColour() {
		return i_selectedColour;
	}


	public void setSelectedColour(Colour selectedColour) {
		i_selectedColour = selectedColour;
	}


	public Map<Colour, HasClickHandlers> getHasClickHandlersByColour() {
		return i_hasClickHandlersByColour;
	}


	public void setHasClickHandlersByColour(Map<Colour, HasClickHandlers> hasClickHandlersByColour) {
		i_hasClickHandlersByColour = hasClickHandlersByColour;
	}


	public InformationCache getInformationCache() {
		return i_informationCache;
	}


	public void setInformationCache(InformationCache informationCache) {
		i_informationCache = informationCache;
	}
}
