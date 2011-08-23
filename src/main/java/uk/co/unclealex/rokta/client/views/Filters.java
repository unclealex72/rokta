package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.filter.Modifier;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.presenters.FiltersPresenter.Display;
import uk.co.unclealex.rokta.client.ui.ModifierValueListBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class Filters extends SimplePanel implements Display {

  @UiTemplate("Filters.ui.xml")
	public interface Binder extends UiBinder<Widget, Filters> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	private final TitleMessages i_titleMessages;
	
	@UiField DialogBox popupPanel;
	
	@UiField HasClickHandlers submitButton;
	@UiField HasClickHandlers cancelButton;
	
	// GameFilters
	@UiField HasValue<Boolean> yearButton;
	@UiField ValueListBox<Integer> yearSelect;
	@UiField HasValue<Boolean> monthButton;
	@UiField ValueListBox<Integer> monthSelect;
	@UiField ValueListBox<Integer> monthYearSelect;
	@UiField HasValue<Boolean> weekButton;
	@UiField Anchor weekAnchor;
	@UiField HasValue<Boolean> allButton;
	@UiField HasValue<Boolean> betweenButton;
	@UiField Anchor fromAnchor;
	@UiField Anchor toAnchor;
	@UiField HasValue<Boolean> sinceButton;
	@UiField Anchor sinceAnchor;
	@UiField HasValue<Boolean> beforeButton;    		
	@UiField Anchor beforeAnchor;

	@UiField ValueListBox<Modifier> modifierSelect;

	@Inject
	public Filters(TitleMessages titleMessages) {
		i_titleMessages = titleMessages;
		add(binder.createAndBindUi(this));
	}
	
	@UiFactory
	public Anchor createAnchor() {
		return new Anchor(true);
	}

	@UiFactory
	public ModifierValueListBox createModifierValueListBox() {
		return new ModifierValueListBox(getTitleMessages());
	}
	
	public DialogBox getPopupPanel() {
		return popupPanel;
	}

	public HasClickHandlers getSubmitButton() {
		return submitButton;
	}

	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}

	public HasValue<Boolean> getYearButton() {
		return yearButton;
	}

	public ValueListBox<Integer> getYearSelect() {
		return yearSelect;
	}

	public HasValue<Boolean> getMonthButton() {
		return monthButton;
	}

	public ValueListBox<Integer> getMonthSelect() {
		return monthSelect;
	}

	public ValueListBox<Integer> getMonthYearSelect() {
		return monthYearSelect;
	}

	public HasValue<Boolean> getWeekButton() {
		return weekButton;
	}

	public Anchor getWeekAnchor() {
		return weekAnchor;
	}

	public HasValue<Boolean> getAllButton() {
		return allButton;
	}

	public HasValue<Boolean> getBetweenButton() {
		return betweenButton;
	}

	public Anchor getFromAnchor() {
		return fromAnchor;
	}

	public Anchor getToAnchor() {
		return toAnchor;
	}

	public HasValue<Boolean> getSinceButton() {
		return sinceButton;
	}

	public Anchor getSinceAnchor() {
		return sinceAnchor;
	}

	public HasValue<Boolean> getBeforeButton() {
		return beforeButton;
	}

	public Anchor getBeforeAnchor() {
		return beforeAnchor;
	}

	public ValueListBox<Modifier> getModifierSelect() {
		return modifierSelect;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

}
