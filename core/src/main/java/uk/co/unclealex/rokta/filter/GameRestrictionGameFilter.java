package uk.co.unclealex.rokta.filter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.process.restriction.GameRestriction;

public abstract class GameRestrictionGameFilter extends AbstractGameFilter {

	public SortedSet<Game> getGames() {
		return getGameDao().getGamesByRestriction(getGameRestriction());
	}

	protected DateFormat createDisplayDateFormat() {
		return new SimpleDateFormat("d MMMM, yyyy");
	}
	
	protected DateFormat createEncodingDateFormat() {
		return new SimpleDateFormat("ddMMyyyy");
	}
	protected abstract GameRestriction getGameRestriction();
	
}
