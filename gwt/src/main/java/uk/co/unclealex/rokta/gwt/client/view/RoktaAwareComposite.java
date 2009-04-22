package uk.co.unclealex.rokta.gwt.client.view;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;

public class RoktaAwareComposite extends VisibilityAwareComposite {

	private RoktaController i_roktaController;
	
	public RoktaAwareComposite(RoktaController roktaController) {
		super();
		i_roktaController = roktaController;
	}

	public RoktaController getRoktaController() {
		return i_roktaController;
	}
	
}
