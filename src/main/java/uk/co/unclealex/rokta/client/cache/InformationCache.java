package uk.co.unclealex.rokta.client.cache;

import com.google.gwt.user.client.rpc.AsyncCallback;

import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;

public interface InformationCache {

	public void useCurrentInformation(AsyncCallback<CurrentInformation> callback, GameFilter gameFilter, int targetStreaksSize);

	public void clearCache();
}
