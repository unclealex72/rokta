package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.GameFilterFactory;
import uk.co.unclealex.rokta.client.places.LeaguePlace;
import uk.co.unclealex.rokta.client.presenters.GameFinishedPresenter.Display;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

public class GameFinishedPresenter implements Presenter<Display> {

	public static interface Display extends IsWidget {
		HasText getLoser();
		HasClickHandlers getSubmitButton();
	}
	
	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	private final Game i_game;
	private final Display i_display;
	private final PlaceController i_placeController;
	
	@Inject
	public GameFinishedPresenter(
			PlaceController placeController, AsyncCallbackExecutor asyncCallbackExecutor, @Assisted Game game, Display display) {
		super();
		i_placeController = placeController;
		i_asyncCallbackExecutor = asyncCallbackExecutor;
		i_game = game;
		i_display = display;
	}

	@Override
	public void show(AcceptsOneWidget container) {
		Display display = getDisplay();
		container.setWidget(display);
		display.getLoser().setText(getGame().getLoser());
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				submitGame();
			}
		});
	}

	protected void submitGame() {
		ExecutableAsyncCallback<Void> callback = new FailureAsPopupExecutableAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getPlaceController().goTo(
						new LeaguePlace(GameFilterFactory.createDefaultGameFilter()));
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Void> callback) {
				userRoktaService.submitGame(getGame(), callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Submitting the new game");
	}

	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}

	public Game getGame() {
		return i_game;
	}

	public Display getDisplay() {
		return i_display;
	}

	public PlaceController getPlaceController() {
		return i_placeController;
	}

}
