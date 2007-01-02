package uk.co.unclealex.rokta.actions;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.ComparatorUtils;

import uk.co.unclealex.rokta.model.Game;

public class GamesAction extends RoktaAction {

	private SortedSet<Game> i_reversedGames;
	
	@Override
	public String executeInternal() {
		Comparator<Game> reverseComparator =
			ComparatorUtils.reversedComparator(ComparatorUtils.NATURAL_COMPARATOR);
		SortedSet<Game> reversedGames = new TreeSet<Game>(reverseComparator);
		reversedGames.addAll(getGames());
		setReversedGames(reversedGames);
		return SUCCESS;
	}
	
	public SortedSet<Game> getReversedGames() {
		return i_reversedGames;
	}

	public void setReversedGames(SortedSet<Game> reversedGames) {
		i_reversedGames = reversedGames;
	}
}
