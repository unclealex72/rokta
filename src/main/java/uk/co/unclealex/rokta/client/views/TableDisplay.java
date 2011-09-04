package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.factories.ClickHandlerFactory;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.user.client.ui.IsWidget;

public interface TableDisplay extends IsWidget {

	void draw(Table table, TitleFactory titleFactory, ClickHandlerFactory clickHandlerFactory);

}
