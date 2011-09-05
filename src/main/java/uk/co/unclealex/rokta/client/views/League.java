package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.factories.ClickHandlerFactory;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.presenters.LeaguePresenter.Display;
import uk.co.unclealex.rokta.client.views.renderer.CellRenderer;
import uk.co.unclealex.rokta.client.views.renderer.FunctionBasedRenderer;
import uk.co.unclealex.rokta.client.views.renderer.NumericColumnRenderer;
import uk.co.unclealex.rokta.client.views.renderer.OnlyForClassColumnRenderer;
import uk.co.unclealex.rokta.client.views.renderer.PercentageColumnRenderer;
import uk.co.unclealex.rokta.client.views.renderer.StyleDecorator;
import uk.co.unclealex.rokta.client.views.renderer.TableRenderer;
import uk.co.unclealex.rokta.shared.model.InfiniteInteger;

import com.google.common.base.Function;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class League extends Composite implements Display {

	public interface Style extends CssResource {
		String header();
		String exempt();
		String notPlaying();
		String movementImage();
	}

	public interface Images extends ClientBundle {
  	@Source("images/league/up.png")
  	ImageResource up();
  	@Source("images/league/down.png")
  	ImageResource down();
  	@Source("images/league/same.png")
  	ImageResource same();
	}
	
  @UiTemplate("League.ui.xml")
	public interface Binder extends UiBinder<Widget, League> {
  	// No methods required.
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	private final Images i_images;
	
	@UiField Grid league;
	@UiField HasText exemptPlayer;
	@UiField Style style;
	
	@Inject
	public League(Images images) {
		i_images = images;
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void draw(Table table, TitleFactory titleFactory, ClickHandlerFactory clickHandlerFactory) {
		NumberFormat twoDecimalPlacesFormat = NumberFormat.getFormat("#.##");
		CellRenderer twoDecimalPlacesRenderer = new NumericColumnRenderer(twoDecimalPlacesFormat);
		CellRenderer infiteIntegerRenderer = new OnlyForClassColumnRenderer<InfiniteInteger>(InfiniteInteger.class) {
			@Override
			protected String doRender(InfiniteInteger cell, Table table, int row, int column) {
				return cell.isInfinite()?null:Integer.toString(cell.getValue());
			}
		};
		final Images images = getImages();
		final Style style = getStyle();
		Function<Integer, Image> movementFunction = new Function<Integer, Image>() {
			@Override
			public Image apply(Integer movement) {
				ImageResource resource = movement < 0?images.down():(movement > 0?images.up():images.same());
				Image image = new Image(resource);
				image.addStyleName(style.movementImage());
				return image;
			}
		};
		CellRenderer movementRenderer = FunctionBasedRenderer.create(Integer.class, movementFunction);
		new TableRenderer(table, titleFactory, clickHandlerFactory)
			.addColumnRenderer(0, movementRenderer)
			.addColumnRenderer(5, twoDecimalPlacesRenderer)
			.addColumnRenderer(6, twoDecimalPlacesRenderer)
			.addColumnRenderer(7, new PercentageColumnRenderer(twoDecimalPlacesFormat))
			.addColumnRenderer(8, infiteIntegerRenderer)
			.addRowDecorator(HEADER, new StyleDecorator(style.header()))
			.addRowDecorator(EXEMPT, new StyleDecorator(style.exempt()))
			.addRowDecorator(NOT_PLAYING, new StyleDecorator(style.notPlaying()))
			.draw(getLeague());
	}

	public Grid getLeague() {
		return league;
	}

	public HasText getExemptPlayer() {
		return exemptPlayer;
	}

	public Style getStyle() {
		return style;
	}

	public Images getImages() {
		return i_images;
	}
	
}
