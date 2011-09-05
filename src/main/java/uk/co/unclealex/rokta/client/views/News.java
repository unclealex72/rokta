package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.NewsPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class News extends Composite implements Display {

  @UiTemplate("News.ui.xml")
	public interface Binder extends UiBinder<Widget, News> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasWidgets currentStreaksPanel;
	@UiField HasWidgets gamePanel;
	@UiField HasWidgets leaguePanel;
	@UiField HasWidgets todaysGamesPanel;
	
	@Inject
	public News() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public IsWidget createLabel(String text) {
		return new InlineLabel(text);
	}
	
	public HasWidgets getCurrentStreaksPanel() {
		return currentStreaksPanel;
	}

	public HasWidgets getGamePanel() {
		return gamePanel;
	}

	public HasWidgets getLeaguePanel() {
		return leaguePanel;
	}

	public HasWidgets getTodaysGamesPanel() {
		return todaysGamesPanel;
	}


}
