package uk.co.unclealex.rokta.client.presenters;

import java.util.List;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.places.GamePlace;
import uk.co.unclealex.rokta.client.presenters.NextRoundPresenter.Display;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.Hand;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.inject.assistedinject.Assisted;

public class NextRoundPresenter extends AllUsersAwarePresenter<Display> {

	public static interface Display extends IsWidget {
		void addPlayer(String playerName, ListBox handListBox);
		ListBox getCounterListBox();
		HasClickHandlers getNextButton();
		HasClickHandlers getBackButton();
	}
	
	private final Display i_display;
	private final PlaceController i_placeController;
	private final Game i_game;
	private List<ListBox> i_handListBoxes = Lists.newArrayList();
	
	@Inject
	public NextRoundPresenter(Display display, @Assisted Game game, AsyncCallbackExecutor asyncCallbackExecutor, PlaceController placeController) {
		super(asyncCallbackExecutor);
		i_game = game;
		i_display = display;
		i_placeController = placeController;
	}

	@Override
	public void show(final AcceptsOneWidget container, final List<String> usernames) {
		final Display display = getDisplay();
		container.setWidget(display);
		ListBox counterListBox = display.getCounterListBox();
		for (String name : usernames) {
			counterListBox.addItem(name);
		}
		String lastCounter = getGame().getLastCounter();
		if (lastCounter != null) {
			counterListBox.setSelectedIndex(usernames.indexOf(lastCounter));
		}
		for (String player : getGame().getCurrentPlayers()) {
			ListBox handListBox = createHandListBox();
			display.addPlayer(player, handListBox);
			getHandListBoxes().add(handListBox);
		}
		display.getNextButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				next();
			}
		});
		display.getBackButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.back();
			}
		});
	}
	
	protected ListBox createHandListBox() {
		ListBox listBox = new ListBox();
		for (Hand hand : Hand.values()) {
			listBox.addItem(hand.getDescription(), hand.name());
		}
		return listBox;
	}

	protected void next() {
		ListBox counterListBox = getDisplay().getCounterListBox();
		String counter = counterListBox.getValue(counterListBox.getSelectedIndex());
		Game game = getGame();
		game = game.addRound(counter, Iterables.transform(getHandListBoxes(), handListBoxFunction()));
		getPlaceController().goTo(new GamePlace(game));
	}

	protected Function<ListBox, Hand> handListBoxFunction() {
		return new Function<ListBox, Hand>() {
			@Override
			public Hand apply(ListBox listBox) {
				return Hand.valueOf(listBox.getValue(listBox.getSelectedIndex()));
			}
		};
	}

	public Display getDisplay() {
		return i_display;
	}

	public PlaceController getPlaceController() {
		return i_placeController;
	}

	public Game getGame() {
		return i_game;
	}

	public List<ListBox> getHandListBoxes() {
		return i_handListBoxes;
	}

}
