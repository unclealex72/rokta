package uk.co.unclealex.rokta.client.util;

import java.util.Collection;

public interface WaitingController {

	public void addWaitingListener(WaitingListener waitingListener);
	public void removeWaitingListener(WaitingListener waitingListener);

	public void startWaiting(Collection<CanWait> canWaits);
	public void stopWaiting(Collection<CanWait> canWaits);
}
