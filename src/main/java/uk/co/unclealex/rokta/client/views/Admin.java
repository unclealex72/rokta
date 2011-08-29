package uk.co.unclealex.rokta.client.views;

import java.util.List;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.AdminPresenter.Display;
import uk.co.unclealex.rokta.client.util.CanWait;
import uk.co.unclealex.rokta.client.util.CanWaitSupport;
import uk.co.unclealex.rokta.shared.model.ColourView;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionModel;
import com.google.inject.Provider;

public class Admin extends Composite implements Display {

	public interface Style extends CssResource {
		public String dark();
		public String light();
		public String colourCell();
	}
	
	private final Provider<CanWaitSupport> i_canWaitSupportProvider;
	
	@UiField Button changeColourButton;
	@UiField Button changePasswordButton;
	@UiField HasText password;
	@UiField AcceptsOneWidget colourListPanel;
	@UiField Button deleteLastGameButton;
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
	public void initialiseColours(SelectionModel<ColourView> selectionModel, List<ColourView> colourViews) {
		Cell<ColourView> colourViewCell = new AbstractCell<ColourView>() {
			@Override
			public void render(Context context, ColourView value, SafeHtmlBuilder sb) {
				String backgroundColour = value.getHtmlName();
				Style style = getStyle();
				String foregroundClass = value.isDark()?style.dark():style.light();
				sb.appendHtmlConstant(
					"<div class='" + style.colourCell() + " " + foregroundClass + "' style='background-color: " + backgroundColour + ";'>" + 
					value.getName() + "</div>");
			}
		};
		CellList<ColourView> cellList = new CellList<ColourView>(colourViewCell);
		cellList.setSelectionModel(selectionModel);
		cellList.setRowCount(colourViews.size());
		cellList.setRowData(0, colourViews);
		getColourListPanel().setWidget(cellList);
		cellList.redraw();
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

	public AcceptsOneWidget getColourListPanel() {
		return colourListPanel;
	}

	public Style getStyle() {
		return style;
	}

	public Button getDeleteLastGameButton() {
		return deleteLastGameButton;
	}

}
