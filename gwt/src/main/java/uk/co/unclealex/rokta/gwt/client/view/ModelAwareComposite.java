package uk.co.unclealex.rokta.gwt.client.view;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.listener.Listener;
import uk.co.unclealex.rokta.gwt.client.model.Notifier;

import com.google.gwt.user.client.ui.Widget;

public abstract class ModelAwareComposite<V, W extends Widget> extends RoktaAwareComposite<W> implements Listener<V> {


	public ModelAwareComposite(RoktaController roktaController, Notifier<V> notifier) {
		super(roktaController);
		notifier.addListener(this);
	}
}
