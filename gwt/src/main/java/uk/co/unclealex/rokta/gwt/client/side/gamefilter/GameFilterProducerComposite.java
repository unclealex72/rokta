package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.ArrayList;
import java.util.List;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public abstract class GameFilterProducerComposite extends RoktaAwareComposite implements GameFilterProducer {

	private List<GameFilterProducerListener> i_gameFilterProducerListeners = new ArrayList<GameFilterProducerListener>();
	private GameFilter i_gameFilter;
	
	protected GameFilterProducerComposite(RoktaAdaptor roktaAdaptor, GameFilterProducerListener[] gameFilterProducerListeners) {
		super(roktaAdaptor);
		List<GameFilterProducerListener> existingGameFilterProducerListeners = getGameFilterProducerListeners();
		for (GameFilterProducerListener gameFilterProducerListener : gameFilterProducerListeners) {
			existingGameFilterProducerListeners.add(gameFilterProducerListener);
		}
	}

	protected GameFilterProducerComposite(RoktaAdaptor roktaAdaptor, GameFilterProducerListener gameFilterProducerListener) {
		this(roktaAdaptor, new GameFilterProducerListener[] { gameFilterProducerListener });
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
