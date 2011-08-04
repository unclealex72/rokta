package uk.co.unclealex.rokta.client.controller;

import java.util.Date;
import java.util.Map;
import java.util.SortedSet;

import uk.co.unclealex.rokta.client.views.Hand;

public interface GameController {

	public void initialise(Date date);

	public void selectPlayers(String instigator, SortedSet<String> players, Date date);

	public void addRound(Map<String, Hand> round);

	public void back();

	public void submitGame();

}