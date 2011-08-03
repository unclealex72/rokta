package uk.co.unclealex.rokta.pub.controller;

import java.util.Date;

import uk.co.unclealex.rokta.pub.views.Game;

public interface GameInitialiser {

	public void initialiseGame(Game game, Date date);
	
	public void startGame(Game game, String instigator, Date dateStarted);
}
