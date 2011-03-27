package uk.co.unclealex.rokta.gwt.client.view.main.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.GameModel;
import uk.co.unclealex.rokta.pub.views.Game;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.Game.State;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;

public class RoundPanel extends AbstractGamePanel<Grid> {

	private int i_rowCount;
	
	public RoundPanel(RoktaController roktaController, GameModel notifier) {
		super(roktaController, notifier);
	}

	@Override
	protected Grid createTopWidget() {
		return new Grid();
	}

	@Override
	protected void next() {
		Map<String, Hand> round = new HashMap<String, Hand>();
		Grid grid = getTopWidget();
		final int rowCount = getRowCount();
		for (int row = 0; row < rowCount; row++) {
			String player = grid.getText(row, 0);
			ListBox handListBox = (ListBox) grid.getWidget(row, 1);
			String hand = handListBox.getItemText(handListBox.getSelectedIndex());
			round.put(player, Hand.valueOf(hand));
		}
		getRoktaGameController().addRound(round);
	}

	public void doOnValueChanged(Game game) {
		List<String> players = game.getPlayers();
		int rowCount = players.size();
		setRowCount(rowCount);
		Grid grid = getTopWidget();
		grid.resize(rowCount, 2);
		int row = 0;
		for (String player : players) {
			grid.setText(row, 0, player);
			ListBox handListBox = new ListBox(false);
			for (Hand hand : Hand.getAllHands()) {
				handListBox.addItem(hand.getDescription());
			}
			grid.setWidget(row, 1, handListBox);
		}
	}

	@Override
	protected State[] getStatesToListenFor() {
		return new State[] { State.PLAYING };
	}
	
	public int getRowCount() {
		return i_rowCount;
	}

	public void setRowCount(int rowCount) {
		i_rowCount = rowCount;
	}
}
