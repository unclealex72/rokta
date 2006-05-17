package uk.co.unclealex.rokta.model.dao;

import java.util.Date;
import java.util.List;

import uk.co.unclealex.rokta.model.Game;

public interface GameDAO {

	public void store(Game game);

	public List<Game> getAllGames();
	
	public List<Game> getAllGamesSince(Date date);
}