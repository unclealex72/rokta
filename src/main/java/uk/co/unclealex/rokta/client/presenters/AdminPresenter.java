package uk.co.unclealex.rokta.client.presenters;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.presenters.AdminPresenter.Display;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.CanWait;
import uk.co.unclealex.rokta.client.util.ClickHandlerAndFailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.model.ColourView;
import uk.co.unclealex.rokta.shared.model.GameSummary;
import uk.co.unclealex.rokta.shared.model.LoggedInUser;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.assistedinject.Assisted;

public class AdminPresenter extends AbstractGameFilterActivity<Display> {

	public static interface Display extends IsWidget {
		void initialiseColours(SelectionModel<ColourView> selectionModel, List<ColourView> colourViews);
		Button getChangeColourButton();
		CanWait getChangeColourCanWait();

		HasText getPassword();
		HasClickHandlers getChangePasswordButton();
		CanWait getChangePasswordCanWait();
		
		CanWait getDeleteLastGameCanWait();
		HasClickHandlers getDeleteLastGameButton();
	}

	private final Display i_display;
	private final TitleMessages i_titleMessages;
	private final AsyncCallbackExecutor i_asyncCallbackExecutor;

	@Inject
	public AdminPresenter(@Assisted GameFilter gameFilter, 
			Display display, AsyncCallbackExecutor asyncCallbackExecutor, TitleMessages titleMessages) {
		super(gameFilter);
		i_titleMessages = titleMessages;
		i_display = display;
		i_asyncCallbackExecutor = asyncCallbackExecutor;
	}


	@Override
	public void start(GameFilter gameFilter, final AcceptsOneWidget panel, EventBus eventBus) {
		ExecutableAsyncCallback<LoggedInUser> callback = new FailureAsPopupExecutableAsyncCallback<LoggedInUser>() {
			@Override
			public void onSuccess(LoggedInUser loggedInUser) {
				initialiseColours(loggedInUser);
				initialisePassword(loggedInUser);
				initialiseDeleteLastGame();
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


	protected void initialiseDeleteLastGame() {
		Display display = getDisplay();
		ClickHandler clickHandler = new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<GameSummary>(
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
		display.getDeleteLastGameButton().addClickHandler(clickHandler);
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
		ExecutableAsyncCallback<ColourView[]> callback = new FailureAsPopupExecutableAsyncCallback<ColourView[]>() {
			@Override
			public void onSuccess(ColourView[] result) {
		    Display display = getDisplay();
				List<ColourView> colourViews = Arrays.asList(result);
		    SelectionModel<ColourView> selectionModel = new SingleSelectionModel<ColourView>();
				display.initialiseColours(selectionModel, colourViews);
				initialiseColoursButton(selectionModel, colourViews, loggedInUser);
				selectionModel.setSelected(loggedInUser.getColourView(), true);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<ColourView[]> callback) {
				anonymousRoktaService.getAllColourViews(callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Finding all available colours");
	}

	protected void initialiseColoursButton(
			final SelectionModel<ColourView> selectionModel, final List<ColourView> colourViews, final LoggedInUser loggedInUser) {
		final Display display = getDisplay();
		final ColourView colourView = getSelectedColourView(selectionModel, colourViews);
		final String username = loggedInUser.getUsername();
		ClickHandler clickHandler = new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<Void>(
				getAsyncCallbackExecutor(),
				"Updating " + username + "'s colour to " + colourView.getName(), 
				display.getChangeColourCanWait()) {
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Void> callback) {
				userRoktaService.updateColour(username, colourView, callback);
			}
		};
		final Button changeColourButton = display.getChangeColourButton();
		changeColourButton.addClickHandler(clickHandler);
		Handler selectionHandler = new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				changeColourButton.setText("Change colour to " + getSelectedColourView(selectionModel, colourViews).getName());
			}
			
		};
		selectionModel.addSelectionChangeHandler(selectionHandler );
	}

	protected ColourView getSelectedColourView(
			final SelectionModel<ColourView> selectionModel, final List<ColourView> colourViews) {
		Predicate<ColourView> predicate = new Predicate<ColourView>() {
			@Override
			public boolean apply(ColourView colourView) {
				return selectionModel.isSelected(colourView);
			}
		};
		return Iterables.find(colourViews, predicate);
	}
	
	protected void initialisePassword(final LoggedInUser loggedInUser) {
		final Display display = getDisplay();
		final String username = loggedInUser.getUsername();
		ClickHandlerAndFailureAsPopupExecutableAsyncCallback<Void> callback = 
			new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<Void>(
				getAsyncCallbackExecutor(), "Updating " + username + "'s password", display.getChangePasswordCanWait()) {
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Void> callback) {
				userRoktaService.updatePassword(username, display.getPassword().getText(), callback);
			}
		};
		display.getChangePasswordButton().addClickHandler(callback);
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
}
