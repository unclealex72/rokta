package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.factories.GameFinishedPresenterFactory;
import uk.co.unclealex.rokta.client.factories.NextRoundPresenterFactory;
import uk.co.unclealex.rokta.client.presenters.GamePresenter.Display;
import uk.co.unclealex.rokta.shared.model.Game;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;

public class GamePresenter extends AbstractActivity implements HasDisplay<Display> {

	public static interface Display extends IsWidget {
		HasOneWidget getMainPanel();
	}

	private final Display i_display;
	private final Game i_game;
	private final Provider<NewGamePresenter> i_newGamePresenterProvider;
	private final NextRoundPresenterFactory i_newRoundPresenterFactory;
	private final GameFinishedPresenterFactory i_gameFinishedPresenterFactory;
	
	@Inject
	public GamePresenter(Display display, @Assisted Game game, Provider<NewGamePresenter> newGamePresenterProvider,
			NextRoundPresenterFactory newRoundPresenterFactory, GameFinishedPresenterFactory gameFinishedPresenterFactory) {
		super();
		i_display = display;
		i_game = game;
		i_newGamePresenterProvider = newGamePresenterProvider;
		i_newRoundPresenterFactory = newRoundPresenterFactory;
		i_gameFinishedPresenterFactory = gameFinishedPresenterFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Game game = getGame();
		Presenter<?> subPresenter;
		if (!game.isStarted()) {
			subPresenter = getNewGamePresenterProvider().get();
		}
		else if (game.isLost()) {
			subPresenter = getGameFinishedPresenterFactory().createGameFinishedPresenter(game);
		}
		else {
			subPresenter = getNewRoundPresenterFactory().createNextRoundPresenter(game);
		}
		Display display = getDisplay();
		panel.setWidget(display);
		subPresenter.show(display.getMainPanel());
	}

	public Display getDisplay() {
		return i_display;
	}

	public Game getGame() {
		return i_game;
	}

	public Provider<NewGamePresenter> getNewGamePresenterProvider() {
		return i_newGamePresenterProvider;
	}

	public NextRoundPresenterFactory getNewRoundPresenterFactory() {
		return i_newRoundPresenterFactory;
	}

	public GameFinishedPresenterFactory getGameFinishedPresenterFactory() {
		return i_gameFinishedPresenterFactory;
	}

}
