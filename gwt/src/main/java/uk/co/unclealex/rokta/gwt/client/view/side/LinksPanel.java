package uk.co.unclealex.rokta.gwt.client.view.side;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.view.RoktaAwareComposite;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class LinksPanel<I> extends RoktaAwareComposite<VerticalPanel> implements ClickListener {

	private Map<Widget, Command> i_commandsByWidget = new HashMap<Widget, Command>();
	private I i_initialisationObject;

	public LinksPanel(RoktaController roktaController, I initialisationObject) {
		super(roktaController);
		i_initialisationObject = initialisationObject;
	}

	@Override
	protected VerticalPanel create() {
		VerticalPanel panel = new VerticalPanel();
		I initialisationObject = getInitialisationObject();
		panel.add(new HeaderElement(createHeader(initialisationObject), 1));
		for (Map.Entry<String, Command> entry : createCommandsByLink(initialisationObject).entrySet()) {
			Hyperlink hyperlink = new Hyperlink();
			hyperlink.setText(entry.getKey());
			getCommandsByWidget().put(hyperlink, entry.getValue());
			hyperlink.addClickListener(this);
			panel.add(hyperlink);
		}
		return panel;
	}
	
	public void onClick(Widget source) {
		Command command = getCommandsByWidget().get(source);
		if (command != null) {
			command.execute();
		}
	}
	
	public abstract LinkedHashMap<String, Command> createCommandsByLink(I initialisationObject);

	public abstract String createHeader(I initialisationObject);

	public Map<Widget, Command> getCommandsByWidget() {
		return i_commandsByWidget;
	}

	public I getInitialisationObject() {
		return i_initialisationObject;
	}
}
