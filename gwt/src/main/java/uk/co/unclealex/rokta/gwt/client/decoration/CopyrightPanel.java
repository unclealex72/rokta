package uk.co.unclealex.rokta.gwt.client.decoration;

import uk.co.unclealex.rokta.gwt.client.ErrorHandler;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CopyrightPanel extends RoktaAwareComposite implements AsyncCallback<String> {

	private RoktaAdaptor i_roktaAdaptor;
	private Label i_copyrightLabel = new Label();
	
	public CopyrightPanel(String id, RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		panel.add(getCopyrightLabel());
		initWidget(id, panel);
		roktaAdaptor.getCopyright(this);
	}

	public void onFailure(Throwable t) {
		ErrorHandler.log(t);
	}
	
	public void onSuccess(String copyrightMessage) {
		getCopyrightLabel().setText(copyrightMessage);
	}
	
	public RoktaAdaptor getRoktaAdaptor() {
		return i_roktaAdaptor;
	}

	public Label getCopyrightLabel() {
		return i_copyrightLabel;
	}
	
}
