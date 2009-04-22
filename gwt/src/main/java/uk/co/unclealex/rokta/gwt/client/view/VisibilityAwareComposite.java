package uk.co.unclealex.rokta.gwt.client.view;

import com.google.gwt.user.client.ui.Composite;

public class VisibilityAwareComposite extends Composite {

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		onVisibilityChange(visible);
	}
	
	public void onVisibilityChange(boolean isVisible) {
		// The default is to do nothing.
	}
}
