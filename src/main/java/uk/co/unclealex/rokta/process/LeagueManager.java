package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.comparators.ComparatorChain;

import uk.co.unclealex.rokta.model.Delta;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;

public class LeagueManager {

	private Collection<Game> i_currentGames;
	private Collection<Game> i_previousGames;
	private Comparator<LeagueRow> i_comparator;
	
	public SortedSet<LeagueRow> generateLeague() {
		SortedSet<LeagueRow> currentLeague = generateLeague(getCurrentGames());
		SortedSet<LeagueRow> previousLeague = generateLeague(getPreviousGames());
		
		Map<Person,Integer> oldPositions = new HashMap<Person, Integer>();
		Map<Person,Integer> newPositions = new HashMap<Person, Integer>();
				
		for (
				IndexingIterator<LeagueRow> iter = new IndexingIterator<LeagueRow>(currentLeague.iterator());
				iter.hasNext(); ) {
			LeagueRow row = iter.next();
			newPositions.put(row.getPerson(), iter.getIndex());
		}
		for (
				IndexingIterator<LeagueRow> iter = new IndexingIterator<LeagueRow>(previousLeague.iterator());
				iter.hasNext(); ) {
			LeagueRow row = iter.next();
			oldPositions.put(row.getPerson(), iter.getIndex());
		}
		
		for (LeagueRow row : currentLeague) {
			Person person = row.getPerson();
			Integer currentPosition = newPositions.get(person);
			Integer previousPosition = oldPositions.get(person);
			if (previousPosition != null) {
				if (currentPosition < previousPosition) {
					row.setDelta(Delta.UP);
				}
				if (previousPosition < currentPosition) {
					row.setDelta(Delta.DOWN);
				}
			}
		}
		
		return currentLeague;
	}
	
	private SortedSet<LeagueRow> generateLeague(Collection<Game> games) {
		int totalGames = 0;
		Map<Person, LeagueRow> rowMap = new HashMap<Person, LeagueRow>();
		
		for (Game game : games) {
			totalGames++;
			Person loser = game.getLoser();
			for (Person participant : game.getParticipants()) {
				if (!rowMap.containsKey(participant)) {
					LeagueRow newLeagueRow = new LeagueRow();
					newLeagueRow.setPerson(participant);
					rowMap.put(participant, newLeagueRow);
				}
				LeagueRow leagueRow = rowMap.get(participant);
				leagueRow.setGamesPlayed(leagueRow.getGamesPlayed() + 1);
				if (participant.equals(loser)) {
					leagueRow.setGamesLost(leagueRow.getGamesLost() + 1);
				}
				leagueRow.setRoundsPlayed(
						leagueRow.getRoundsPlayed() + countPlaysForPersonInGame(participant, game));
			}
		}
		
		// Now enter the total number of games ever played and initialise the deltas.
		for (LeagueRow leagueRow : rowMap.values()) {
			leagueRow.setTotalGamesPlayed(totalGames);
			leagueRow.setDelta(Delta.NONE);
		}
		
		// Populate the number of rounds played by each person
		
		SortedSet<LeagueRow> league = new TreeSet<LeagueRow>(getComparator());
		league.addAll(rowMap.values());
		return league;
	}
	
	private int countPlaysForPersonInGame(Person person, Game game) {
		int cnt = 0;
		for (Round round : game.getRounds()) {
			if (round.getParticipants().contains(person)) {
				cnt++;
			}
		}
		return cnt;
	}
	
	private static Comparator<LeagueRow> s_atomicCompareByLossesPerGame =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Double(o1.getLossesPerGame()).compareTo(o2.getLossesPerGame());
			}
		};
		
	private static Comparator<LeagueRow> s_atomicCompareByLossesPerRound =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Double(o1.getLossesPerRound()).compareTo(o2.getLossesPerRound());
			}
		};
					
	private static Comparator<LeagueRow> s_atomicCompareByGamesPlayed =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Integer(o2.getGamesPlayed()).compareTo(o1.getGamesPlayed());
			}
		};

	private static Comparator<LeagueRow> s_atomicCompareByRoundsPlayed =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return new Integer(o2.getRoundsPlayed()).compareTo(o1.getRoundsPlayed());
			}
		};

	private static Comparator<LeagueRow> s_atomicCompareByPerson =
		new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				return o1.getPerson().compareTo(o2.getPerson());
			}
		};
	
	public Comparator<LeagueRow> getCompareByLossesPerGame() {
		ComparatorChain<LeagueRow> chain = new ComparatorChain<LeagueRow>();
		
		chain.addComparator(s_atomicCompareByLossesPerGame);
		chain.addComparator(s_atomicCompareByLossesPerRound);
		chain.addComparator(s_atomicCompareByGamesPlayed);
		chain.addComparator(s_atomicCompareByRoundsPlayed);
		chain.addComparator(s_atomicCompareByPerson);
		return chain;
	}
	public Comparator<LeagueRow> getComparator() {
		return i_comparator;
	}
	public void setComparator(Comparator<LeagueRow> comparator) {
		i_comparator = comparator;
	}
	public Collection<Game> getCurrentGames() {
		return i_currentGames;
	}
	public void setCurrentGames(Collection<Game> currentGames) {
		i_currentGames = currentGames;
	}
	public Collection<Game> getPreviousGames() {
		return i_previousGames;
	}
	public void setPreviousGames(Collection<Game> previousGames) {
		i_previousGames = previousGames;
	}
}
