package uk.co.unclealex.rokta.client.controller;

import java.util.Date;

import uk.co.unclealex.rokta.shared.model.Game;

public interface GameInitialiser {

	public void initialiseGame(Game game, Date date);
	
	public void startGame(Game game, String instigator, Date dateStarted);
}
