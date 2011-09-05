package uk.co.unclealex.rokta.client.presenters;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.HandCountPresenter.Display;
import uk.co.unclealex.rokta.shared.model.Hand;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

public class HandCountPresenter implements Presenter<Display> {

	public static interface Display extends IsWidget {

		void draw(String title, Map<String, Long> countsByHand);
	}
	
	private final Display i_display;
	private final Map<Hand, Long> i_handCounts;
	private final String i_title;
	
	@Inject
	public HandCountPresenter(Display display, @Assisted String title, @Assisted Map<Hand, Long> handCounts) {
		super();
		i_display = display;
		i_handCounts = handCounts;
		i_title = title;
	}

	@Override
	public void show(AcceptsOneWidget container) {
		Display display = getDisplay();
		container.setWidget(display);
		Map<String, Long> handCounts = Maps.newTreeMap();
		for (Entry<Hand, Long> entry : getHandCounts().entrySet()) {
			handCounts.put(entry.getKey().getDescription(), entry.getValue());
		}
		display.draw(getTitle(), handCounts);
	}

	public Display getDisplay() {
		return i_display;
	}

	public Map<Hand, Long> getHandCounts() {
		return i_handCounts;
	}

	public String getTitle() {
		return i_title;
	}
}
