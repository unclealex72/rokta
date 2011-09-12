package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.AdminPresenter.Display;
import uk.co.unclealex.rokta.client.util.CanWait;
import uk.co.unclealex.rokta.client.util.CanWaitSupport;
import uk.co.unclealex.rokta.shared.model.Colour;
import uk.co.unclealex.rokta.shared.model.Colour.Group;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

public class Admin extends Composite implements Display {

	public interface Style extends CssResource {
		public String selected();
		public String colour();
		public String dark();
		public String light();
	}
	
	private final Provider<CanWaitSupport> i_canWaitSupportProvider;
	
	@UiField Button changeColourButton;
	@UiField Button changePasswordButton;
	@UiField HasText password;
	@UiField HasWidgets colourListPanel;
	@UiField Button deleteLastGameButton;
	@UiField Button clearCacheButton;
	@UiField Style style;
	
  @UiTemplate("Admin.ui.xml")
	public interface Binder extends UiBinder<Widget, Admin> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@Inject
	public Admin(Provider<CanWaitSupport> canWaitSupportProvider) {
		super();
		i_canWaitSupportProvider = canWaitSupportProvider;
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void addColourGroup(Group colourGroup) {
		// Groups are not shown.
	}
	
	@Override
	public HasClickHandlers addColour(Colour colour) {
		Anchor anchor = new Anchor(true);
		Style style = getStyle();
		anchor.addStyleName(style.colour());
		anchor.addStyleName(colour.isDark()?style.dark():style.light());
		anchor.setTitle(Joiner.on(' ').join(colour.getDescriptiveWords()));
		getColourListPanel().add(anchor);
		anchor.getElement().getStyle().setBackgroundColor(colour.getRgb());
		return anchor;
	}
	
	@Override
	public void selectColour(HasClickHandlers colourSource) {
		((Widget) colourSource).addStyleName(getStyle().selected());
	}
	
	@Override
	public void deselectColour(HasClickHandlers colourSource) {
		((Widget) colourSource).removeStyleName(getStyle().selected());
	}
	
	@Override
	public CanWait getChangeColourCanWait() {
		return getCanWait(getChangeColourButton());
	}
	
	@Override
	public CanWait getChangePasswordCanWait() {
		return getCanWait(getChangePasswordButton());
	}
	
	@Override
	public CanWait getDeleteLastGameCanWait() {
		return getCanWait(getDeleteLastGameButton());
	}
	
	@Override
	public CanWait getClearCacheCanWait() {
		return getCanWait(getClearCacheButton());
	}
	
	protected CanWait getCanWait(HasEnabled... enableds) {
		return getCanWaitSupportProvider().get().wrap(enableds);
	}
	
	public Provider<CanWaitSupport> getCanWaitSupportProvider() {
		return i_canWaitSupportProvider;
	}

	public Button getChangeColourButton() {
		return changeColourButton;
	}

	public Button getChangePasswordButton() {
		return changePasswordButton;
	}

	public HasText getPassword() {
		return password;
	}

	public HasWidgets getColourListPanel() {
		return colourListPanel;
	}

	public Style getStyle() {
		return style;
	}

	public Button getDeleteLastGameButton() {
		return deleteLastGameButton;
	}

	public Button getClearCacheButton() {
		return clearCacheButton;
	}

}
