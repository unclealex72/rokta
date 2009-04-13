package uk.co.unclealex.rokta.gwt.client;

import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RoktaAwareComposite extends Composite implements GameFilterListener {

	private RoktaAdaptor i_roktaAdaptor;
	
	protected RoktaAwareComposite(RoktaAdaptor roktaAdaptor) {
		super();
		i_roktaAdaptor = roktaAdaptor;
		roktaAdaptor.addGameFilterListener(this);
	}

	public void initWidget(String id, Widget widget) {
		super.initWidget(widget);
		getElement().setId(id);
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		onVisibilityChange(visible);
	}
	
	public void onGameFilterChange(GameFilter newGameFilter) {
		// The default is to do nothing.
	}
	
	public void onVisibilityChange(boolean isVisible) {
		// The default is to do nothing.
	}
	
	public RoktaAdaptor getRoktaAdaptor() {
		return i_roktaAdaptor;
	}


}
