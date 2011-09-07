package uk.co.unclealex.rokta.client.presenters;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.places.GamePlace;
import uk.co.unclealex.rokta.client.presenters.NewGamePresenter.Display;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.InitialPlayers;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;

public class NewGamePresenter extends AllUsersAwarePresenter<Display> {

	public static interface Display extends IsWidget {
		HasText getExemptPlayer();
		ListBox getInstigatorListBox();
		CellList<String> getPlayersListBox();
		Button getStartButton();
	}
	
	private final Display i_display;
	private final PlaceController i_placeController;
	private final TitleMessages i_titleMessages;
	
	@Inject
	public NewGamePresenter(Display display, TitleMessages titleMessages,
			AsyncCallbackExecutor asyncCallbackExecutor, PlaceController placeController) {
		super(asyncCallbackExecutor);
		i_display = display;
		i_titleMessages = titleMessages;
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
		TitleMessages titleMessages = getTitleMessages();
		display.getExemptPlayer().setText(exemptPlayer==null?titleMessages.nooneExempt():titleMessages.exempt(exemptPlayer));
		final List<String> allUserList = Lists.newArrayList();
		List<String> allBarExemptPlayers = initialPlayers.getAllBarExemptPlayers();
		allUserList.addAll(allBarExemptPlayers);
		List<String> nonPlayers = Lists.newArrayList(initialPlayers.getAllUsers());
		nonPlayers.removeAll(allBarExemptPlayers);
		allUserList.addAll(nonPlayers);
		ListBox instigatorListBox = display.getInstigatorListBox();
		for (String name : usernames) {
			instigatorListBox.addItem(name);
		}
		CellList<String> playersListBox = display.getPlayersListBox();
		if (exemptPlayer != null) {
			allUserList.remove(exemptPlayer);
		}
    final SelectionModel<String> selectionModel = new MultiSelectionModel<String>();
    playersListBox.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
				enableOrDisableStartButton(allUserList);
      }
    });
		playersListBox.setRowData(allUserList);
		display.getStartButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startGame(allUserList);
			}
		});
		enableOrDisableStartButton(allUserList);
	}

	protected SortedSet<String> getSelectedPlayerNames(List<String> allUserList) {
		final SelectionModel<? super String> selectionModel = getDisplay().getPlayersListBox().getSelectionModel();
		Predicate<String> selectedPredicate = new Predicate<String>() {
			@Override
			public boolean apply(String playerName) {
				return selectionModel.isSelected(playerName);
			}
		};
		return Sets.newTreeSet(Iterables.filter(allUserList, selectedPredicate));
	}
	
	protected void enableOrDisableStartButton(List<String> allUserList) {
		getDisplay().getStartButton().setEnabled(getSelectedPlayerNames(allUserList).size() > 1);
	}
	
	protected void startGame(List<String> allUserList) {
		ListBox instigatorListBox = getDisplay().getInstigatorListBox();
		String instigator = instigatorListBox.getValue(instigatorListBox.getSelectedIndex());
		SortedSet<String> selectedPlayerNames = getSelectedPlayerNames(allUserList);
		Game game = new Game(instigator, selectedPlayerNames);
		getPlaceController().goTo(new GamePlace(game));
	}
	
	public Display getDisplay() {
		return i_display;
	}

	public PlaceController getPlaceController() {
		return i_placeController;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

}
