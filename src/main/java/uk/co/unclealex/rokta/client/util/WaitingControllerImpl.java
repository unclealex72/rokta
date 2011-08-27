package uk.co.unclealex.rokta.client.util;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class WaitingControllerImpl implements WaitingController {

	private List<WaitingListener> i_waitingListeners = Lists.newArrayList();
	private int i_waitRequests = 0;
	
	@Override
	public void addWaitingListener(WaitingListener waitingListener) {
		getWaitingListeners().add(waitingListener);
	}

	@Override
	public void removeWaitingListener(WaitingListener waitingListener) {
		getWaitingListeners().remove(waitingListener);
	}

	@Override
	public void startWaiting(Collection<CanWait> canWaits) {
		int waitRequests = getWaitRequests();
		if (waitRequests == 0) {
			fireStartWaiting(getWaitingListeners());
		}
		setWaitRequests(waitRequests + 1);
		fireStartWaiting(canWaits);
	}

	@Override
	public void stopWaiting(Collection<CanWait> canWaits) {
		fireStopWaiting(canWaits);
		int waitRequests = getWaitRequests() - 1;
		setWaitRequests(waitRequests);
		if (waitRequests == 0) {
			fireStopWaiting(getWaitingListeners());
		}
	}

	protected <C extends CanWait> void fireStartWaiting(Collection<C> canWaits) {
		for (C canWait : canWaits) {
			canWait.startWaiting();
		}
	}
	
	protected <C extends CanWait> void fireStopWaiting(Collection<C> canWaits) {
		for (C canWait : canWaits) {
			canWait.stopWaiting();
		}
	}

	public List<WaitingListener> getWaitingListeners() {
		return i_waitingListeners;
	}

	public void setWaitingListeners(List<WaitingListener> waitingListeners) {
		i_waitingListeners = waitingListeners;
	}

	public int getWaitRequests() {
		return i_waitRequests;
	}

	public void setWaitRequests(int waitRequests) {
		i_waitRequests = waitRequests;
	}

}
