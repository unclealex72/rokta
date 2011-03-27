package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.gwt.client.view.LoadingAwareComposite;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;

public abstract class GameFilterProducerComposite<W extends Widget> 
	extends LoadingAwareComposite<InitialDatesView, W> implements GameFilterProducer {

	private List<GameFilterProducerListener> i_gameFilterProducerListeners = new ArrayList<GameFilterProducerListener>();
	private GameFilter i_gameFilter;
	
	protected GameFilterProducerComposite(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(roktaController, model);
		List<GameFilterProducerListener> existingGameFilterProducerListeners = getGameFilterProducerListeners();
		for (GameFilterProducerListener gameFilterProducerListener : gameFilterProducerListeners) {
			existingGameFilterProducerListeners.add(gameFilterProducerListener);
		}
	}

	protected GameFilterProducerComposite(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener gameFilterProducerListener) {
		this(roktaController, model, new GameFilterProducerListener[] { gameFilterProducerListener });
	}

	@Override
	public void onVisibilityChange(boolean isVisible) {
		if (isVisible) {
			setGameFilter(createGameFilter());
		}
		else {
			setGameFilter(null);
		}
	}
	
	protected abstract GameFilter createGameFilter();
	
	public void addGameFilterProducerListener(GameFilterProducerListener gameFilterProducerListener) {
		getGameFilterProducerListeners().add(gameFilterProducerListener);
	}

	public void removeGameFilterProducerListener(GameFilterProducerListener gameFilterProducerListener) {
		getGameFilterProducerListeners().remove(gameFilterProducerListener);
	}

	public List<GameFilterProducerListener> getGameFilterProducerListeners() {
		return i_gameFilterProducerListeners;
	}

	public GameFilter getGameFilter() {
		return i_gameFilter;
	}

	protected void setGameFilter(GameFilter gameFilter) {
		i_gameFilter = gameFilter;
		GameFilterProducerEvent gameFilterProducerEvent = new GameFilterProducerEvent(this, gameFilter);
		for (GameFilterProducerListener gameFilterProducerListener : getGameFilterProducerListeners()) {
			gameFilterProducerListener.onGameFilterProduced(gameFilterProducerEvent);
		}
	}

}
