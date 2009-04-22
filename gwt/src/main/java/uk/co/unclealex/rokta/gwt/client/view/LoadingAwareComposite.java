package uk.co.unclealex.rokta.gwt.client.view;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.listener.LoadingListener;
import uk.co.unclealex.rokta.gwt.client.model.LoadingNotifier;

public abstract class LoadingAwareComposite<V> extends RoktaAwareComposite implements LoadingListener<V> {


	public LoadingAwareComposite(RoktaController roktaController, LoadingNotifier<V> notifier) {
		super(roktaController);
		notifier.addListener(this);
	}

	public void onLoading() {
		// TODO Auto-generated method stub
	}
	
	public void onLoaded() {
		// TODO Auto-generated method stub
		
	}
}
