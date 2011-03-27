package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.controller.RoktaControllerCallback;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;
import uk.co.unclealex.rokta.gwt.client.view.side.HeaderElement;

import com.google.gwt.user.client.ui.VerticalPanel;

public class StreaksPanel extends RoktaAwareComposite<VerticalPanel> {

	private RoktaControllerCallback i_callback;
	private StreaksWidget i_streaksWidget;
	private StreaksWidget i_currentStreaksWidget;
	private String i_currentStreaksTitle;
	
	public StreaksPanel(
			RoktaController roktaController, RoktaControllerCallback callback,
			StreaksWidget streaksWidget, StreaksWidget currentStreaksWidget, String currentStreaksTitle) {
		super(roktaController);
		i_callback = callback;
		i_streaksWidget = streaksWidget;
		i_currentStreaksWidget = currentStreaksWidget;
		i_currentStreaksTitle = currentStreaksTitle;
	}

	@Override
	protected VerticalPanel create() {
		VerticalPanel panel = new VerticalPanel();
		panel.add(getStreaksWidget());
		panel.add(new HeaderElement(getCurrentStreaksTitle(), 1));
		panel.add(getCurrentStreaksWidget());
		return panel;
	}

	@Override
	protected void postCreate(VerticalPanel widget) {
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

	public StreaksWidget getStreaksWidget() {
		return i_streaksWidget;
	}

	public StreaksWidget getCurrentStreaksWidget() {
		return i_currentStreaksWidget;
	}

	public String getCurrentStreaksTitle() {
		return i_currentStreaksTitle;
	}

	
}
