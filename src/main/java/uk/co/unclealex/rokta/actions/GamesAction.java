package uk.co.unclealex.rokta.actions;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.ComparatorUtils;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.views.GameView;

public class GamesAction extends RoktaAction {

	private Iterator<GameView> i_gameViews;
	
	@Override
	public String executeInternal() {
		Comparator<Game> reverseComparator =
			ComparatorUtils.reversedComparator(ComparatorUtils.NATURAL_COMPARATOR);
		SortedSet<Game> reversedGames = new TreeSet<Game>(reverseComparator);
		reversedGames.addAll(getGames());
		final Iterator<Game> gameIter = reversedGames.iterator();
		final Iterator<GameView> iter = new Iterator<GameView>() {
			public boolean hasNext() {
				return gameIter.hasNext();
			}

			public GameView next() {
				return new GameView(gameIter.next());
			}

			public void remove() {
			}
		};
		setGameViews(iter);
		return SUCCESS;
	}

	public Iterator<GameView> getGameViews() {
		return i_gameViews;
	}

	public void setGameViews(Iterator<GameView> gameViews) {
		i_gameViews = gameViews;
	}

}
