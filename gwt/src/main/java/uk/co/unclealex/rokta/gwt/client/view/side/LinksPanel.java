package uk.co.unclealex.rokta.gwt.client.view.side;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class LinksPanel<I> extends RoktaAwareComposite implements ClickHandler {

	private Map<Widget, Command> i_commandsByWidget = new HashMap<Widget, Command>();
	
	public LinksPanel(RoktaController roktaController, I initialisationObject) {
		super(roktaController);
		VerticalPanel panel = new VerticalPanel();
		panel.add(new HeaderElement(createHeader(initialisationObject), 1));
		for (Map.Entry<String, Command> entry : createCommandsByLink(initialisationObject).entrySet()) {
			Hyperlink hyperlink = new Hyperlink();
			hyperlink.setText(entry.getKey());
			getCommandsByWidget().put(hyperlink, entry.getValue());
			hyperlink.addClickHandler(this);
			panel.add(hyperlink);
		}
		initWidget(panel);
	}

	public void onClick(ClickEvent event) {
		Command command = getCommandsByWidget().get(event.getSource());
		if (command != null) {
			command.execute();
		}
	}
	
	public abstract LinkedHashMap<String, Command> createCommandsByLink(I initialisationObject);

	public abstract String createHeader(I initialisationObject);

	public Map<Widget, Command> getCommandsByWidget() {
		return i_commandsByWidget;
	}
}
