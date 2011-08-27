package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.factories.ClickHandlerFactory;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.presenters.StreaksTablePresenter.Display;
import uk.co.unclealex.rokta.client.views.renderer.CellRenderer;
import uk.co.unclealex.rokta.client.views.renderer.DateColumnRenderer;
import uk.co.unclealex.rokta.client.views.renderer.StyleDecorator;
import uk.co.unclealex.rokta.client.views.renderer.SubstituteRepeatColumnRenderer;
import uk.co.unclealex.rokta.client.views.renderer.TableRenderer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

public class StreaksTable extends Composite implements Display {

	public interface Style extends CssResource {
		String header();
	}

  @UiTemplate("StreaksTable.ui.xml")
	public interface Binder extends UiBinder<Widget, StreaksTable> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField Grid streaks;
	@UiField Style style;
	
	public StreaksTable() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(Table table, TitleFactory titleFactory, ClickHandlerFactory clickHandlerFactory) {
		CellRenderer dateRenderer = new DateColumnRenderer(DateTimeFormat.getFormat("EEE dd/MM/yyyy HH:mm"));
		new TableRenderer(table, titleFactory, clickHandlerFactory)
		.addColumnRenderer(0, new SubstituteRepeatColumnRenderer("="))
		.addColumnRenderer(3, dateRenderer)
		.addColumnRenderer(4, dateRenderer)
		.addRowDecorator(HEADER, new StyleDecorator(getStyle().header()))
		.draw(getStreaks());
	}
	
	public Grid getStreaks() {
		return streaks;
	}

	public Style getStyle() {
		return style;
	}
}
