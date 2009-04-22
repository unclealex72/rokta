package uk.co.unclealex.rokta.gwt.client.view;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.listener.Listener;
import uk.co.unclealex.rokta.gwt.client.model.Notifier;

public abstract class ModelAwareComposite<V> extends RoktaAwareComposite implements Listener<V> {


	public ModelAwareComposite(RoktaController roktaController, Notifier<V> notifier) {
		super(roktaController);
		notifier.addListener(this);
	}

}
