package uk.co.unclealex.rokta.gwt.client.side;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LinksPanel extends RoktaAwareComposite implements ClickHandler {

	private Map<Widget, Command> i_commandsByWidget = new HashMap<Widget, Command>();
	private VerticalPanel i_panel;
	
	public LinksPanel(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		VerticalPanel panel = new VerticalPanel();
		setPanel(panel);
	}

	protected void initialise(String header, LinkedHashMap<String, Command> commandsByLink) {
		initialise(null, header, commandsByLink);
	}
	
	protected void initialise(String id, String header, LinkedHashMap<String, Command> commandsByLink) {
		VerticalPanel panel = getPanel();
		panel.add(new HeaderElement(header, 1));
		for (Map.Entry<String, Command> entry : commandsByLink.entrySet()) {
			addLink(entry.getKey(), entry.getValue());
		}
		initWidget(id, panel);
	}
	
	protected void addLink(String text, Command command) {
		Hyperlink hyperlink = new Hyperlink();
		hyperlink.setText(text);
		getCommandsByWidget().put(hyperlink, command);
		hyperlink.addClickHandler(this);
		getPanel().add(hyperlink);
	}

	public void onClick(ClickEvent event) {
		Command command = getCommandsByWidget().get(event.getSource());
		if (command != null) {
			command.execute();
		}
	}
	
	public Map<Widget, Command> getCommandsByWidget() {
		return i_commandsByWidget;
	}

	public VerticalPanel getPanel() {
		return i_panel;
	}

	public void setPanel(VerticalPanel panel) {
		i_panel = panel;
	}

}
