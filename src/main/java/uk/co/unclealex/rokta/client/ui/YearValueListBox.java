package uk.co.unclealex.rokta.client.ui;

import java.io.IOException;

import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueListBox;

public class YearValueListBox extends ValueListBox<Integer> {

	public YearValueListBox() {
		super(new Renderer<Integer>() {
			public String render(Integer year) {
				return Integer.toString(year);
			}
			@Override
			public void render(Integer year, Appendable appendable) throws IOException {
				appendable.append(render(year));
			}
		});
	}

}
