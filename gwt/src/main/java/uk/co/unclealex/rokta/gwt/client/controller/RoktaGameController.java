package uk.co.unclealex.rokta.gwt.client.controller;

import java.util.Date;
import java.util.Map;
import java.util.SortedSet;

import uk.co.unclealex.rokta.gwt.client.model.AbstractLoadingModel;
import uk.co.unclealex.rokta.gwt.client.model.CurrentlyLoadingModel;
import uk.co.unclealex.rokta.gwt.client.model.GameModel;
import uk.co.unclealex.rokta.gwt.client.view.GwtRoktaFacadeAsync;
import uk.co.unclealex.rokta.pub.controller.GameController;
import uk.co.unclealex.rokta.pub.controller.GameInitialiser;
import uk.co.unclealex.rokta.pub.controller.GameSubmitter;
import uk.co.unclealex.rokta.pub.controller.SimpleGameController;
import uk.co.unclealex.rokta.pub.views.Game;
import uk.co.unclealex.rokta.pub.views.Hand;
import uk.co.unclealex.rokta.pub.views.InitialPlayers;

public class RoktaGameController extends AbstractLoadingModel<InitialPlayers> implements GameController, GameInitialiser, GameSubmitter {

	private GameController i_gameController;
	private GameModel i_gameModel;
	private CurrentlyLoadingModel i_currentlyLoadingModel;
	private GwtRoktaFacadeAsync i_gwtRoktaFacadeAsync;
	
	public RoktaGameController(
			GameModel gameModel, CurrentlyLoadingModel currentlyLoadingModel, GwtRoktaFacadeAsync gwtRoktaFacadeAsync) {
		super();
		i_gwtRoktaFacadeAsync = gwtRoktaFacadeAsync;
		i_gameModel = gameModel;
		i_currentlyLoadingModel = currentlyLoadingModel;
		i_gameController = new SimpleGameController(gameModel.getValue(), this, this);
	}

	public void initialise(Date date) {
		getGameController().initialise(date);
		registerUpdate();
	}

	public void initialiseGame(Game game, Date date) {
		LoadingModelAsyncCallback<InitialPlayers> callback =
			new LoadingModelAsyncCallback<InitialPlayers>(getCurrentlyLoadingModel(), this);
		getGwtRoktaFacadeAsync().getInitialPlayers(date, callback);
	}

	public void startGame(Game game, String instigator, Date dateStarted) {
		game.setDatePlayed(dateStarted);
		game.setInstigator(instigator);
	}
	
	@Override
	public void setValue(InitialPlayers initialPlayers) {
		getGameModel().getValue().initialise(
				initialPlayers.getAllUsers(),
				initialPlayers.getAllBarExemptPlayers(),
				initialPlayers.getExemptPlayer());
	}
	
	public void addRound(Map<String, Hand> round) {
		getGameController().addRound(round);
		registerUpdate();
	}

	public void back() {
		getGameController().back();
		registerUpdate();
	}


	public void selectPlayers(String instigator, SortedSet<String> players, Date date) {
		getGameController().selectPlayers(instigator, players, date);
		registerUpdate();
	}

	public void submitGame() {
		submitGame(getGameModel().getValue());
	}

	public void submitGame(Game game) {
		getGwtRoktaFacadeAsync().submitGame(game, new ModelAsyncCallback<Game, GameModel>(getGameModel()));
		registerUpdate();
	}

	private void registerUpdate() {
		GameModel gameModel = getGameModel();
		gameModel.setValue(gameModel.getValue());
	}

	protected GameController getGameController() {
		return i_gameController;
	}

	protected GameModel getGameModel() {
		return i_gameModel;
	}

	protected GwtRoktaFacadeAsync getGwtRoktaFacadeAsync() {
		return i_gwtRoktaFacadeAsync;
	}

	public CurrentlyLoadingModel getCurrentlyLoadingModel() {
		return i_currentlyLoadingModel;
	}
}
