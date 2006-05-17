package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.LeagueRow;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.Round;

public class LeagueManager {

	public SortedSet<LeagueRow> generateLeague(Collection<Game> games, Comparator<LeagueRow> comparator) {
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
		
		// Now enter the total number of games ever played
		for (LeagueRow leagueRow : rowMap.values()) {
			leagueRow.setTotalGamesPlayed(totalGames);
		}
		
		// Populate the number of rounds played by each person
		
		SortedSet<LeagueRow> league = new TreeSet<LeagueRow>(comparator);
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
	/**
	 * Comparator for a league where the least lost games is better. Next, the most played games is better,
	 * then most played rounds. In the event of a tie, go alphabetically by person. 
	 * @return
	 */
	public Comparator<LeagueRow> getCompareByLostGames() {
		return new Comparator<LeagueRow>() {
			public int compare(LeagueRow o1, LeagueRow o2) {
				int cmpLostGames =  new Integer(o1.getGamesLost()).compareTo(o2.getGamesLost());
				if (cmpLostGames != 0) {
					return -cmpLostGames;
				}
				int cmpPlayedGames = new Integer(o1.getGamesPlayed()).compareTo(o2.getGamesPlayed());
				if (cmpPlayedGames != 0) {
					return cmpPlayedGames;
				}
				int cmpPlayedRounds = new Integer(o1.getRoundsPlayed()).compareTo(o2.getRoundsPlayed());
				return cmpPlayedRounds==0?o1.getPerson().compareTo(o2.getPerson()):cmpPlayedRounds;
			}
		};
	}
}
