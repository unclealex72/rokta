package uk.co.unclealex.rokta.gwt.client.view.main.game;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.GameModel;
import uk.co.unclealex.rokta.pub.views.Game;
import uk.co.unclealex.rokta.pub.views.Game.State;

import com.google.gwt.user.client.ui.Label;

public class SubmitPanel extends AbstractGamePanel<Label> {

	private Label i_loserLabel;
	
	public SubmitPanel(RoktaController roktaController, GameModel notifier) {
		super(roktaController, notifier);
	}

	@Override
	protected Label createTopWidget() {
		final Label loserLabel = new Label();
		setLoserLabel(loserLabel);
		return loserLabel;
	}

	@Override
	public void doOnValueChanged(Game game) {
		getLoserLabel().setText(getGameMessages().loser(game.getPlayers().get(0)));
	}

	@Override
	protected void next() {
		getRoktaGameController().submitGame();
	}

	@Override
	protected State[] getStatesToListenFor() {
		return new State[] { State.FINISHED };
	}

	public Label getLoserLabel() {
		return i_loserLabel;
	}

	public void setLoserLabel(Label loserLabel) {
		i_loserLabel = loserLabel;
	}
}
