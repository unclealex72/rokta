package uk.co.unclealex.rokta.client.cache;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.util.FailureAsPopupAsyncCallback;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;

import com.google.common.base.Function;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class InformationServiceImpl implements InformationService {

	private final InformationCache i_informationCache;

	@Inject
	public InformationServiceImpl(InformationCache informationCache) {
		super();
		i_informationCache = informationCache;
	}
	
	@Override
	public <I> void execute(
			GameFilter gameFilter, 
			final Function<CurrentInformation, I> currentInformationFunction, 
			final InformationCallback<I> informationCallback) {
		AsyncCallback<CurrentInformation> callback = new FailureAsPopupAsyncCallback<CurrentInformation>() {
			@Override
			public void onSuccess(CurrentInformation currentInformation) {
				I information = currentInformationFunction.apply(currentInformation);
				informationCallback.execute(information);
			}
		};
		getInformationCache().useCurrentInformation(callback, gameFilter, 10);

	}
	
	public InformationCache getInformationCache() {
		return i_informationCache;
	}
}
