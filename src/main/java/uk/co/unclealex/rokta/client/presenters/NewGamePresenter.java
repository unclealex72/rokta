package uk.co.unclealex.rokta.client.presenters;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.places.GamePlace;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;

public class NewGamePresenter extends AllUsersAwarePresenter {

	public static interface Display extends IsWidget {
		HasText getExemptPlayer();
		ListBox getInstigatorListBox();
		ListBox getPlayersListBox();
		Button getStartButton();
	}
	
	private final Display i_display;
	private final PlaceController i_placeController;
	
	@Inject
	public NewGamePresenter(Display display, AsyncCallbackExecutor asyncCallbackExecutor, PlaceController placeController) {
		super(asyncCallbackExecutor);
		i_display = display;
		i_placeController = placeController;
	}

	@Override
	public void show(final AcceptsOneWidget container, final List<String> usernames) {
		ExecutableAsyncCallback<InitialPlayers> initialPlayersCallback = new FailureAsPopupExecutableAsyncCallback<InitialPlayers>() {
			@Override
			public void onSuccess(InitialPlayers initialPlayers) {
				show(container, usernames, initialPlayers);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<InitialPlayers> callback) {
				userRoktaService.getInitialPlayers(new Date(), callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(initialPlayersCallback, "Finding the initial players");
	}

	protected void show(AcceptsOneWidget container, List<String> usernames, InitialPlayers initialPlayers) {
		final Display display = getDisplay();
		container.setWidget(display);
		String exemptPlayer = initialPlayers.getExemptPlayer();
		display.getExemptPlayer().setText(exemptPlayer==null?"No one":exemptPlayer);
		List<String> allUserList = Lists.newArrayList();
		List<String> allBarExemptPlayers = initialPlayers.getAllBarExemptPlayers();
		allUserList.addAll(allBarExemptPlayers);
		List<String> nonPlayers = Lists.newArrayList(initialPlayers.getAllUsers());
		nonPlayers.removeAll(allBarExemptPlayers);
		allUserList.addAll(nonPlayers);
		ListBox instigatorListBox = display.getInstigatorListBox();
		for (String name : usernames) {
			instigatorListBox.addItem(name);
		}
		ListBox playersListBox = display.getPlayersListBox();
		if (exemptPlayer != null) {
			allUserList.remove(exemptPlayer);
		}
		for (String name : allUserList) {
			playersListBox.addItem(name);
		}
		playersListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				enableOrDisableStartButton();
			}
		});
		display.getStartButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startGame();
			}
		});
		enableOrDisableStartButton();
	}

	protected SortedSet<String> getSelectedPlayerNames() {
		ListBox playersListBox = getDisplay().getPlayersListBox();
		int size = playersListBox.getItemCount();
		SortedSet<String> selectedPlayerNames = Sets.newTreeSet();
		for (int idx = 0; idx < size; idx++) {
			if (playersListBox.isItemSelected(idx)) {
				selectedPlayerNames.add(playersListBox.getValue(idx));
			}
		}
		return selectedPlayerNames;
	}
	
	protected void enableOrDisableStartButton() {
		getDisplay().getStartButton().setEnabled(getSelectedPlayerNames().size() > 1);
	}
	
	protected void startGame() {
		ListBox instigatorListBox = getDisplay().getInstigatorListBox();
		String instigator = instigatorListBox.getValue(instigatorListBox.getSelectedIndex());
		SortedSet<String> selectedPlayerNames = getSelectedPlayerNames();
		Game game = new Game(instigator, selectedPlayerNames);
		getPlaceController().goTo(new GamePlace(game));
	}
	
	public Display getDisplay() {
		return i_display;
	}

	public PlaceController getPlaceController() {
		return i_placeController;
	}

}
