package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.controller.RoktaControllerCallback;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;
import uk.co.unclealex.rokta.gwt.client.view.side.HeaderElement;

import com.google.gwt.user.client.ui.VerticalPanel;

public class StreaksPanel extends RoktaAwareComposite {

	private RoktaControllerCallback i_callback;
	
	public StreaksPanel(
			RoktaController roktaController, RoktaControllerCallback callback,
			StreaksWidget streaksWidget, StreaksWidget currentStreaksWidget, String currentStreaksTitle) {
		super(roktaController);
		i_callback = callback;
		VerticalPanel panel = new VerticalPanel();
		panel.add(streaksWidget);
		panel.add(new HeaderElement(currentStreaksTitle, 1));
		panel.add(currentStreaksWidget);
		initWidget(panel);
		setStylePrimaryName("streaksPanel");
	}

	@Override
	public void onVisibilityChange(boolean isVisible) {
		if (isVisible) {
			getCallback().execute(getRoktaController());
		}
	}
	
	public RoktaControllerCallback getCallback() {
		return i_callback;
	}

	
}
