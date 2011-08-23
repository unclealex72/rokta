package uk.co.unclealex.rokta.client.ui;

import java.io.IOException;

import uk.co.unclealex.rokta.client.filter.FirstGameOfTheDayModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheMonthModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheWeekModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheYearModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheDayModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheMonthModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheWeekModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheYearModifier;
import uk.co.unclealex.rokta.client.filter.Modifier;
import uk.co.unclealex.rokta.client.filter.ModifierVistor;
import uk.co.unclealex.rokta.client.filter.NoOpModifier;
import uk.co.unclealex.rokta.client.messages.TitleMessages;

import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueListBox;

public class ModifierValueListBox extends ValueListBox<Modifier> {

	protected static class ModifierRenderer implements Renderer<Modifier>, ModifierVistor<String> {
		
		private final TitleMessages i_titleMessages;
		
		public ModifierRenderer(TitleMessages titleMessages) {
			super();
			i_titleMessages = titleMessages;
		}

		@Override
		public String render(Modifier modifier) {
			String uncapitalised = modifier.accept(this);
			return Character.toUpperCase(uncapitalised.charAt(0)) + uncapitalised.substring(1);
		}

		@Override
		public void render(Modifier modifier, Appendable appendable) throws IOException {
			appendable.append(render(modifier));
		}
		
		@Override
		public String visit(Modifier modifier) {
			return null;
		}
		@Override
		public String visit(FirstGameOfTheYearModifier firstGameOfTheYearModifier) {
			return getTitleMessages().firstGameOfTheYear();
		}
		@Override
		public String visit(FirstGameOfTheMonthModifier firstGameOfTheMonthModifier) {
			return getTitleMessages().firstGameOfTheMonth();
		}
		@Override
		public String visit(FirstGameOfTheWeekModifier firstGameOfTheWeekModifier) {
			return getTitleMessages().firstGameOfTheWeek();
		}
		@Override
		public String visit(FirstGameOfTheDayModifier firstGameOfTheDayModifier) {
			return getTitleMessages().firstGameOfTheDay();
		}
		public String visit(LastGameOfTheYearModifier lastGameOfTheYearModifier) {
			return getTitleMessages().lastGameOfTheYear();
		}
		@Override
		public String visit(LastGameOfTheMonthModifier lastGameOfTheMonthModifier) {
			return getTitleMessages().lastGameOfTheMonth();
		}
		@Override
		public String visit(LastGameOfTheWeekModifier lastGameOfTheWeekModifier) {
			return getTitleMessages().lastGameOfTheWeek();
		}
		@Override
		public String visit(LastGameOfTheDayModifier lastGameOfTheDayModifier) {
			return getTitleMessages().lastGameOfTheDay();
		}
		@Override
		public String visit(NoOpModifier noOpModifier) {
			return getTitleMessages().noop();
		}

		public TitleMessages getTitleMessages() {
			return i_titleMessages;
		}
	};
	
	public ModifierValueListBox(TitleMessages titleMessages) {
		super(new ModifierRenderer(titleMessages));
	}

}
