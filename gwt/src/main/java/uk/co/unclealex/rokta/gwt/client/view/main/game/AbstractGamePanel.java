package uk.co.unclealex.rokta.gwt.client.view.main.game;

import java.util.Arrays;
import java.util.List;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.controller.RoktaGameController;
import uk.co.unclealex.rokta.gwt.client.model.GameModel;
import uk.co.unclealex.rokta.gwt.client.view.LoadingAwareComposite;
import uk.co.unclealex.rokta.pub.views.Game;
import uk.co.unclealex.rokta.pub.views.Game.State;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractGamePanel<W extends Widget> extends LoadingAwareComposite<Game, VerticalPanel> {

	private RoktaGameController i_roktaGameController;
	private GameMessages i_gameMessages = GWT.create(GameMessages.class);
	private Button i_backButton;
	private Button i_nextButton;
	private W i_topWidget;
	private List<State> i_statesToListenForList;
	
	public AbstractGamePanel(RoktaController roktaController, GameModel notifier) {
		super(roktaController, notifier);
		i_roktaGameController = roktaController.getRoktaGameController();
		i_statesToListenForList = Arrays.asList(getStatesToListenFor());
	}

	@Override
	protected final VerticalPanel create() {
		VerticalPanel panel = new VerticalPanel();
		W topWidget = createTopWidget();
		panel.add(topWidget);
		setTopWidget(topWidget);
		HorizontalPanel bottomPanel = new HorizontalPanel();
		if (showBackButton()) {
			ClickListener clickListener = new ClickListener() {
				public void onClick(Widget sender) {
					back();
				}
			};
			setBackButton(createButton(bottomPanel, getGameMessages().back(), clickListener));
		}
		ClickListener clickListener = new ClickListener() {
			public void onClick(Widget sender) {
				next();
			}
		};
		setNextButton(createButton(bottomPanel, getGameMessages().next(), clickListener));
		return panel;
	}
	
	protected Button createButton(HorizontalPanel bottomPanel, String text, ClickListener clickListener) {
		Button button = new Button(text);
		button.addClickListener(clickListener);
		bottomPanel.add(button);
		bottomPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		return button;
	}

	protected abstract W createTopWidget();

	@Override
	protected final void postCreate(VerticalPanel widget) {
		postCreateTopWidget(getTopWidget());
	}
	
	protected void postCreateTopWidget(W topWidget) {
		// Default is to do nothing
	}

	protected void back() {
		getRoktaGameController().back();
	}

	protected abstract void next();

	protected boolean showBackButton() {
		return true;
	}
	
	public final void onValueChanged(Game game) {
		if (getStatesToListenForList().contains(game.getGameState())) {
			doOnValueChanged(game);
		}
	}
	
	public abstract void doOnValueChanged(Game game);
	
	protected abstract Game.State[] getStatesToListenFor();
	
	protected RoktaGameController getRoktaGameController() {
		return i_roktaGameController;
	}

	protected GameMessages getGameMessages() {
		return i_gameMessages;
	}

	public Button getBackButton() {
		return i_backButton;
	}

	public void setBackButton(Button backButton) {
		i_backButton = backButton;
	}

	public Button getNextButton() {
		return i_nextButton;
	}

	public void setNextButton(Button nextButton) {
		i_nextButton = nextButton;
	}

	public W getTopWidget() {
		return i_topWidget;
	}

	public void setTopWidget(W topWidget) {
		i_topWidget = topWidget;
	}

	public List<State> getStatesToListenForList() {
		return i_statesToListenForList;
	}

}
