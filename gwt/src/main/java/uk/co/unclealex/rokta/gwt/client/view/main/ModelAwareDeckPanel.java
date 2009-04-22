package uk.co.unclealex.rokta.gwt.client.view.main;

import java.util.HashMap;
import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.Notifier;
import uk.co.unclealex.rokta.gwt.client.view.ModelAwareComposite;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModelAwareDeckPanel<V> extends ModelAwareComposite<V> {

	private DeckPanel i_panel = new DeckPanel();
	private Map<V, Widget> i_widgetsByModelValue = new HashMap<V, Widget>();
	
	public ModelAwareDeckPanel(
			RoktaController roktaController, Notifier<V> notifier, String stylePrimaryName, Map<V, Widget> widgetsByModelValue) {
		super(roktaController, notifier);
		i_widgetsByModelValue = widgetsByModelValue;
		DeckPanel panel = getPanel();
		panel.setAnimationEnabled(true);
		initWidget(panel);
		for (Widget widget : widgetsByModelValue.values()) {
			panel.add(widget);
		}
		setStylePrimaryName(stylePrimaryName);
	}

	public void onValueChanged(V value) {
		Widget widget = getWidgetsByModelValue().get(value);
		if (widget != null) {
			DeckPanel panel = getPanel();
			panel.showWidget(panel.getWidgetIndex(widget));
		}
	}
	
	public DeckPanel getPanel() {
		return i_panel;
	}

	public Map<V, Widget> getWidgetsByModelValue() {
		return i_widgetsByModelValue;
	}

}
