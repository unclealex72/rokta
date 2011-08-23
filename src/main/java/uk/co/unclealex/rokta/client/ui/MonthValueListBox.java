package uk.co.unclealex.rokta.client.ui;

import java.io.IOException;

import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueListBox;

public class MonthValueListBox extends ValueListBox<Integer> {

	private static final String[] MONTHS = 
		new String[] {
			"January", "February", "March", "April", "May", "June", 
			"July", "August", "September", "October", "November", "December" };
	
	public MonthValueListBox() {
		super(new Renderer<Integer>() {
			public String render(Integer month) {
				return MONTHS[month];
			}
			@Override
			public void render(Integer month, Appendable appendable) throws IOException {
				appendable.append(render(month));
			}
		});
	}

}
