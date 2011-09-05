package uk.co.unclealex.rokta.client.presenters;

import uk.co.unclealex.rokta.client.filter.GameFilter;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class AbstractGameFilterActivity<D extends IsWidget> extends AbstractActivity implements HasDisplay<D> {

	private final GameFilter i_gameFilter;
	
	public AbstractGameFilterActivity(GameFilter gameFilter) {
		super();
		i_gameFilter = gameFilter;
	}

	@Override
	public final void start(AcceptsOneWidget panel, EventBus eventBus) {
		start(getGameFilter(), panel, eventBus);
	}

	public abstract void start(GameFilter gameFilter, AcceptsOneWidget panel, EventBus eventBus);

	public GameFilter getGameFilter() {
		return i_gameFilter;
	}
}
