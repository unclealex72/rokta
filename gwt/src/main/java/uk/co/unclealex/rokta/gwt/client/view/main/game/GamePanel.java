package uk.co.unclealex.rokta.gwt.client.view.main.game;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.LoadingNotifier;
import uk.co.unclealex.rokta.gwt.client.view.LoadingAwareComposite;
import uk.co.unclealex.rokta.pub.views.Game;
import uk.co.unclealex.rokta.pub.views.Game.State;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class GamePanel extends LoadingAwareComposite<Game, DeckPanel> {

	private SelectPlayersPanel i_selectPlayersPanel;
	private RoundPanel i_roundPanel;
	private SubmitPanel i_submitPanel;
	
	private DeckPanel i_deckPanel;
	
	public GamePanel(RoktaController roktaController, LoadingNotifier<Game> notifier,
			SelectPlayersPanel selectPlayersPanel, RoundPanel roundPanel, SubmitPanel submitPanel) {
		super(roktaController, notifier);
		i_selectPlayersPanel = selectPlayersPanel;
		i_roundPanel = roundPanel;
		i_submitPanel = submitPanel;
	}

	@Override
	protected DeckPanel create() {
		DeckPanel deckPanel = new DeckPanel();
		deckPanel.add(getSelectPlayersPanel());
		deckPanel.add(getRoundPanel());
		deckPanel.add(getSubmitPanel());
		return deckPanel;
	}

	protected <W extends Widget> W addToPanel(Panel parent, W child) {
		parent.add(child);
		return child;
	}

	public void onValueChanged(Game game) {
		State gameState = game.getGameState();
		Widget widgetToDisplay = null;
		if (gameState == State.CHOOSE_PLAYERS) {
			widgetToDisplay = getSelectPlayersPanel();
		}
		else if (gameState == State.PLAYING) {
			widgetToDisplay = getRoundPanel();
		}
		else if (gameState == State.FINISHED) {
			widgetToDisplay = getSubmitPanel();
		}
		if (widgetToDisplay != null) {
			DeckPanel deckPanel = getDeckPanel();
			deckPanel.showWidget(deckPanel.getWidgetIndex(widgetToDisplay));
		}
	}
	
	protected SelectPlayersPanel getSelectPlayersPanel() {
		return i_selectPlayersPanel;
	}

	protected RoundPanel getRoundPanel() {
		return i_roundPanel;
	}

	protected SubmitPanel getSubmitPanel() {
		return i_submitPanel;
	}

	public DeckPanel getDeckPanel() {
		return i_deckPanel;
	}
}
