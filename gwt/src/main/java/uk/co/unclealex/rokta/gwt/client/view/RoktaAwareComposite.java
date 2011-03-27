package uk.co.unclealex.rokta.gwt.client.view;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;

import com.google.gwt.user.client.ui.Widget;

public abstract class RoktaAwareComposite<W extends Widget> extends VisibilityAwareComposite {

	private RoktaController i_roktaController;
	
	public RoktaAwareComposite(RoktaController roktaController) {
		super();
		i_roktaController = roktaController;
	}

	public void initialise() {
		W widget = create();
		initWidget(widget);
		postCreate(widget);
	}
	
	protected abstract W create();
	
	protected void postCreate(W widget) {
		// Do nothing by default.
	}

	public RoktaController getRoktaController() {
		return i_roktaController;
	}
	
}
