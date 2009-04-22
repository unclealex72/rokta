package uk.co.unclealex.rokta.gwt.client.view.decoration;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CopyrightPanel extends Composite {

	public CopyrightPanel() {
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		CopyrightMessages messages = GWT.create(CopyrightMessages.class);
		panel.add(new Label(messages.getCopyright(new Date())));
		initWidget(panel);
		setStylePrimaryName("copyright");
	}	
}
