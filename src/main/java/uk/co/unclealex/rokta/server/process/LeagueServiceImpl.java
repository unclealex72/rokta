package uk.co.unclealex.rokta.server.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.commons.collections15.comparators.ComparatorChain;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.rokta.server.dao.GameDao;
import uk.co.unclealex.rokta.server.model.Day;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.model.Play;
import uk.co.unclealex.rokta.server.model.Round;
import uk.co.unclealex.rokta.server.util.DateUtil;
import uk.co.unclealex.rokta.shared.model.Colour;
import uk.co.unclealex.rokta.shared.model.InfiniteInteger;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;
import uk.co.unclealex.rokta.shared.model.Leagues;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Transactional
public class LeagueServiceImpl implements LeagueService {

  private GameDao i_gameDao;
  private DateUtil i_dateUtil;
  private PersonService i_personService;
  private Comparator<LeagueRow> i_leagueRowComparator;

  @PostConstruct
  public void initialise() {
    setLeagueRowComparator(createLeagueRowComparator());
  }

  @Override
  public Leagues generateLeagues(final SortedSet<Game> games, final Date now) {
    final SortedMap<Game, League> leaguesForAllGames = Maps.newTreeMap();
    final SortedMap<String, Colour> coloursByUsername = Maps.newTreeMap();
    generateLeagues(games, leaguesForAllGames, coloursByUsername);
    decorateWithDeltas(leaguesForAllGames);
    decorateWithExemptionAndAbsence(leaguesForAllGames, now);
    return new Leagues(coloursByUsername, Sets.newTreeSet(leaguesForAllGames.values()));
  }

  protected SortedMap<Game, League> generateLeagues(
      final SortedSet<Game> games,
      final SortedMap<Game, League> leaguesByGame,
      final SortedMap<String, Colour> coloursByUsername) {
    int totalGames = 0;
    int totalPlayers = 0;
    final Map<Person, LeagueRow> rowMap = new HashMap<Person, LeagueRow>();
    for (final Game game : games) {
      totalGames++;
      final Person loser = game.getLoser();
      final SortedSet<Person> participants = game.getParticipants();
      final int participantCount = participants.size();
      totalPlayers += participantCount;
      for (final Person participant : participants) {
        coloursByUsername.put(participant.getName(), participant.getGraphingColour());
        if (!rowMap.containsKey(participant)) {
          final LeagueRow newLeagueRow = new LeagueRow();
          newLeagueRow.setPersonName(participant.getName());
          newLeagueRow.setDelta(0);
          rowMap.put(participant, newLeagueRow);
        }
        final LeagueRow leagueRow = rowMap.get(participant);
        leagueRow.setTotalParticipants(leagueRow.getTotalParticipants() + participantCount);
        final int playsForPersonInGame = countPlaysForPersonInGame(participant, game);
        if (participant.equals(loser)) {
          leagueRow.setGamesLost(leagueRow.getGamesLost() + 1);
          leagueRow.setRoundsPlayedInLostGames(leagueRow.getRoundsPlayedInLostGames() + playsForPersonInGame);
        }
        else {
          leagueRow.setRoundsPlayedInWonGames(leagueRow.getRoundsPlayedInWonGames() + playsForPersonInGame);
          leagueRow.setGamesWon(leagueRow.getGamesWon() + 1);
        }
      }
      final League league = createLeague(rowMap.values(), totalGames, totalPlayers, game);
      leaguesByGame.put(game, league);

    }
    return leaguesByGame;
  }

  protected int countPlaysForPersonInGame(final Person person, final Game game) {
    int cnt = 0;
    for (final Round round : game.getRounds()) {
      if (round.getParticipants().contains(person)) {
        cnt++;
      }
    }
    return cnt;
  }

  protected League createLeague(
      final Collection<LeagueRow> rows,
      final int totalGames,
      final int totalPlayers,
      final Game game) {
    final League league = new League();
    league.setLastGameDate(game.getDatePlayed());
    league.setCurrent(false);
    league.setTotalGames(totalGames);
    league.setTotalPlayers(totalPlayers);

    final SortedSet<LeagueRow> sortedRows = new TreeSet<LeagueRow>(getLeagueRowComparator());
    for (final LeagueRow leagueRow : rows) {
      final LeagueRow copy = leagueRow.copy();
      copy.setLeague(league);
      sortedRows.add(copy);
    }
    league.setRows(new ArrayList<LeagueRow>(sortedRows));
    return league;
  }

  protected void decorateWithDeltas(final SortedMap<Game, League> leaguesByGame) {
    // We only decorate the last league and only if there is something to
    // compare it against.
    if (leaguesByGame.size() < 2) {
      return;
    }

    final Game lastGame = leaguesByGame.lastKey();
    final Game penultimateGame = leaguesByGame.headMap(lastGame).lastKey();
    Map<String, Integer> oldPositions;
    Map<String, Integer> newPositions = new HashMap<String, Integer>();
    final League[] leagues = new League[] { leaguesByGame.get(penultimateGame), leaguesByGame.get(lastGame) };
    for (final League league : leagues) {
      oldPositions = newPositions;
      newPositions = new HashMap<String, Integer>();
      for (final IndexingIterator<LeagueRow> iter = new IndexingIterator<LeagueRow>(league.getRows().iterator()); iter
          .hasNext();) {
        final LeagueRow row = iter.next();
        newPositions.put(row.getPersonName(), iter.getIndex());
      }

      for (final LeagueRow row : league.getRows()) {
        final String personName = row.getPersonName();
        final Integer currentPosition = newPositions.get(personName);
        final Integer previousPosition = oldPositions.get(personName);
        row.setDelta(previousPosition == null ? 0 : previousPosition - currentPosition);
      }
    }
  }

  protected void decorateWithExemptionAndAbsence(final SortedMap<Game, League> leaguesByGame, final Date currentDate) {
    if (leaguesByGame.isEmpty()) {
      return;
    }
    final Game lastGamePlayed = getGameDao().getLastGame();
    lastGamePlayed.getParticipants();
    final Game lastLeagueGame = leaguesByGame.lastKey();
    // Exemptions only occur if the last game played was today.
    // If the last game was played today we can also see who hasn't played today
    if (lastLeagueGame.equals(lastGamePlayed)) {
      final Collection<String> currentPlayerNames = getCurrentPlayerNames(leaguesByGame.keySet(), currentDate);
      final League league = leaguesByGame.get(lastLeagueGame);
      league.setCurrent(true);
      if (getDateUtil().areSameDay(lastGamePlayed.getDatePlayed(), currentDate)) {
        final Person exempt = getPersonService().getExemptPlayer(currentDate);
        final String exemptName = exempt == null ? null : exempt.getName();
        for (final LeagueRow leagueRow : league.getRows()) {
          final String playerName = leagueRow.getPersonName();
          leagueRow.setExempt(playerName.equals(exemptName));
          leagueRow.setPlayingToday(currentPlayerNames.contains(playerName));
        }
      }
      LeagueRow previousRow = null;
      for (final LeagueRow currentRow : league.getRows()) {
        if (previousRow != null) {
          currentRow.setGap(calculateGap(previousRow, currentRow));
        }
        previousRow = currentRow;
      }
    }
  }

  protected Collection<String> getCurrentPlayerNames(final Set<Game> games, final Date currentDate) {
    final Day currentDay = new Day(currentDate);
    final int day = currentDay.getDay();
    final int month = currentDay.getMonth();
    final int year = currentDay.getYear();
    final Predicate<Game> currentGamePredicate = new Predicate<Game>() {
      @Override
      public boolean apply(final Game game) {
        return game.getDayPlayed().intValue() == day
            && game.getMonthPlayed().intValue() == month
            && game.getYearPlayed().intValue() == year;
      }
    };
    final Iterable<Game> currentDaysGames = Iterables.filter(games, currentGamePredicate);
    final Set<String> playerNames = Sets.newHashSet();
    for (final Game game : currentDaysGames) {
      for (final Round round : game.getRounds()) {
        for (final Play play : round.getPlays()) {
          playerNames.add(play.getPerson().getName());
        }
      }
    }
    return playerNames;
  }

  protected InfiniteInteger calculateGap(final LeagueRow higherRow, final LeagueRow lowerRow) {
    final int playedTop = higherRow.getGamesPlayed();
    final int lostTop = higherRow.getGamesLost();
    final boolean exemptTop = higherRow.isExempt();
    boolean playingTop = higherRow.isPlayingToday();
    final int playedBottom = lowerRow.getGamesPlayed();
    final int lostBottom = lowerRow.getGamesLost();
    final boolean exemptBottom = lowerRow.isExempt();
    boolean playingBottom = lowerRow.isPlayingToday();

    InfiniteInteger gap;
    if (!playingTop && !playingBottom) {
      playingTop = playingBottom = true;
    }
    if (!playingTop && lostTop == 0) {
      gap = InfiniteInteger.INFINITY;
    }
    else if (!playingBottom && playedBottom == lostBottom) {
      gap = InfiniteInteger.INFINITY;
    }
    else {
      gap =
          new InfiniteInteger(race(
              playingTop,
              playedTop,
              lostTop,
              exemptTop,
              playingBottom,
              playedBottom,
              lostBottom,
              exemptBottom));
    }
    return gap;
  }

  protected int race(
      final boolean playingTop,
      int playedTop,
      int lostTop,
      boolean exemptTop,
      final boolean playingBottom,
      int playedBottom,
      final int lostBottom,
      boolean exemptBottom) {
    int gap = 0;
    do {
      if (playingTop) {
        if (!exemptTop) {
          playedTop++;
          lostTop++;
        }
        exemptTop = !exemptTop;
      }
      if (playingBottom) {
        if (!exemptBottom) {
          playedBottom++;
        }
        exemptBottom = false;
      }
      gap++;
    } while (lostBottom * playedTop > lostTop * playedBottom);
    return gap;
  }

  // Comparators for leagues - a smaller value on the LHS will mean a higher
  // league placing.

  protected Comparator<LeagueRow> createLeagueRowComparator() {
    final Comparator<LeagueRow> atomicCompareByLossesPerGame = new Comparator<LeagueRow>() {
      @Override
      public int compare(final LeagueRow o1, final LeagueRow o2) {
        return new Double(o1.getLossesPerGame()).compareTo(o2.getLossesPerGame());
      }
    };

    final Comparator<LeagueRow> atomicCompareByRoundsPerWonGame = new Comparator<LeagueRow>() {
      @Override
      public int compare(final LeagueRow o1, final LeagueRow o2) {
        return new Double(o1.getRoundsPerWonGames()).compareTo(o2.getRoundsPerWonGames());
      }
    };

    final Comparator<LeagueRow> atomicCompareByRoundsPerLostGame = new Comparator<LeagueRow>() {
      @Override
      public int compare(final LeagueRow o1, final LeagueRow o2) {
        return new Double(o2.getRoundsPerLostGames()).compareTo(o2.getRoundsPerLostGames());
      }
    };

    final Comparator<LeagueRow> atomicCompareByGamesPlayed = new Comparator<LeagueRow>() {
      @Override
      public int compare(final LeagueRow o1, final LeagueRow o2) {
        return new Integer(o2.getGamesPlayed()).compareTo(o1.getGamesPlayed());
      }
    };

    final Comparator<LeagueRow> atomicCompareByRoundsPlayed = new Comparator<LeagueRow>() {
      @Override
      public int compare(final LeagueRow o1, final LeagueRow o2) {
        return new Integer(o2.getRoundsPlayed()).compareTo(o1.getRoundsPlayed());
      }
    };

    final Comparator<LeagueRow> atomicCompareByPerson = new Comparator<LeagueRow>() {
      @Override
      public int compare(final LeagueRow o1, final LeagueRow o2) {
        return o1.getPersonName().compareTo(o2.getPersonName());
      }
    };
    final ComparatorChain<LeagueRow> chain = new ComparatorChain<LeagueRow>();
    chain.addComparator(atomicCompareByLossesPerGame);
    chain.addComparator(atomicCompareByRoundsPerWonGame);
    chain.addComparator(atomicCompareByRoundsPerLostGame);
    chain.addComparator(atomicCompareByGamesPlayed);
    chain.addComparator(atomicCompareByRoundsPlayed);
    chain.addComparator(atomicCompareByPerson);
    return chain;
  }

  public DateUtil getDateUtil() {
    return i_dateUtil;
  }

  public void setDateUtil(final DateUtil dateUtil) {
    i_dateUtil = dateUtil;
  }

  public PersonService getPersonService() {
    return i_personService;
  }

  public void setPersonService(final PersonService personManager) {
    i_personService = personManager;
  }

  public GameDao getGameDao() {
    return i_gameDao;
  }

  public void setGameDao(final GameDao gameDao) {
    i_gameDao = gameDao;
  }

  public Comparator<LeagueRow> getLeagueRowComparator() {
    return i_leagueRowComparator;
  }

  public void setLeagueRowComparator(final Comparator<LeagueRow> leagueRowComparator) {
    i_leagueRowComparator = leagueRowComparator;
  }
}
