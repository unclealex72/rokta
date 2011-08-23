package uk.co.unclealex.rokta.client.presenters;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.util.FailureAsPopupAsyncCallback;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class InformationPresenter<I> extends AbstractGameFilterActivity {

	private final InformationCache i_informationCache;

	public InformationPresenter(GameFilter gameFilter, InformationCache informationCache) {
		super(gameFilter);
		i_informationCache = informationCache;
	}

	@Override
	public void start(final GameFilter gameFilter, final AcceptsOneWidget panel, EventBus eventBus) {
		AsyncCallback<CurrentInformation> callback = new FailureAsPopupAsyncCallback<CurrentInformation>() {
			@Override
			public void onSuccess(CurrentInformation currentInformation) {
				show(gameFilter, panel, asSpecificInformation(currentInformation));
			}
		};
		getInformationCache().useCurrentInformation(callback, gameFilter, 10);
	}

	protected abstract I asSpecificInformation(CurrentInformation currentInformation);
	
	protected abstract void show(GameFilter gameFilter, AcceptsOneWidget panel, I currentInformation);
	
	public InformationCache getInformationCache() {
		return i_informationCache;
	}

	
}
