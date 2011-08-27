package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.factories.ClickHandlerFactory;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.presenters.HeadToHeadsPresenter.Display;
import uk.co.unclealex.rokta.client.views.renderer.CellRenderer;
import uk.co.unclealex.rokta.client.views.renderer.PercentageColumnRenderer;
import uk.co.unclealex.rokta.client.views.renderer.StyleDecorator;
import uk.co.unclealex.rokta.client.views.renderer.TableRenderer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

public class HeadToHeads extends Composite implements Display {

	public interface Style extends CssResource {
		String header();
	}
  @UiTemplate("HeadToHeads.ui.xml")
	public interface Binder extends UiBinder<Widget, HeadToHeads> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField Grid headToHeads;
	@UiField Style style;
	
	public HeadToHeads() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(Table table, TitleFactory titleFactory, ClickHandlerFactory clickHandlerFactory) {
		int size = table.getRows().size();
		TableRenderer renderer = new TableRenderer(table, titleFactory, clickHandlerFactory);
		CellRenderer percentageRender = new PercentageColumnRenderer(NumberFormat.getFormat("#.##"));
		for (int col = 1; col < size; col++) {
			renderer.addColumnRenderer(col, percentageRender);
		}
		StyleDecorator headerDecorator = new StyleDecorator(getStyle().header());
		renderer.addRowDecorator(HEADER, headerDecorator);
		renderer.addColumnDecorator(0, headerDecorator);
		renderer.draw(getHeadToHeads());
	}
	
	public Grid getHeadToHeads() {
		return headToHeads;
	}

	public Style getStyle() {
		return style;
	}
}
