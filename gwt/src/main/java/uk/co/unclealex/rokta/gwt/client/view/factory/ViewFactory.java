package uk.co.unclealex.rokta.gwt.client.view.factory;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;

public class ViewFactory {

	private RoktaController i_roktaController;
	private RoktaModel i_roktaModel;
	
	public ViewFactory(RoktaController roktaController, RoktaModel roktaModel) {
		i_roktaController = roktaController;
		i_roktaModel = roktaModel;
	}

	public RoktaController getRoktaController() {
		return i_roktaController;
	}
	
	public RoktaModel getRoktaModel() {
		return i_roktaModel;
	}
}
