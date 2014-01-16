/**
 * Copyright 2013 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with work for additional information
 * regarding copyright ownership.  The ASF licenses file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package stats

import org.joda.time.DateTime
import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.injected
import dao.GameDao
import dao.PlayerDao
import dao.Transactional
import dates.Now
import dates.WhenImplicits
import filter.BetweenGameFilter
import filter.ContiguousGameFilter
import filter.DayGameFilter
import filter.MonthGameFilter
import filter.SinceGameFilter
import filter.UntilGameFilter
import filter.YearGameFilter
import filter.DayLikeImplicits._
import model.Game
import model.Player
import com.typesafe.scalalogging.slf4j.Logging
import model.NonPersistedGame
import filter.Day

/**
 * @author alex
 *
 */
class StatsFactoryImpl(
  _now: Option[Now] = injected,
  _leagueFactory: Option[LeagueFactory] = injected,
  _currentResultsFactory: Option[CurrentResultsFactory] = injected,
  _snapshotsFactory: Option[SnapshotsFactory] = injected,
  _streaksFactory: Option[StreaksFactory] = injected,
  _headToHeadsFactory: Option[HeadToHeadsFactory] = injected,
  _exemptPlayerFactory: Option[ExemptPlayerFactory] = injected,
  _todaysGamesFactory: Option[TodaysGamesFactory] = injected,
  _transactional: Option[Transactional] = injected
  ) extends StatsFactory with WhenImplicits with AutoInjectable {

  val now = injectIfMissing(_now)
  val leagueFactory = injectIfMissing(_leagueFactory)
  val currentResultsFactory = injectIfMissing(_currentResultsFactory)
  val snapshotsFactory = injectIfMissing(_snapshotsFactory)
  val headToHeadsFactory = injectIfMissing(_headToHeadsFactory)
  val streaksFactory = injectIfMissing(_streaksFactory)
  val exemptPlayerFactory = injectIfMissing(_exemptPlayerFactory)
  val todaysGamesFactory = injectIfMissing(_todaysGamesFactory)
  val tx = injectIfMissing(_transactional)
  
  def apply(contiguousGameFilter: ContiguousGameFilter): Stats[Game] = {
    val games = tx { (playerDao: PlayerDao) => (gameDao: GameDao) => 
      gameDao.games(contiguousGameFilter) }
    val todaysGames = todaysGamesFactory()
    val current = filterIsCurrent(contiguousGameFilter)
    val currentResults = currentResultsFactory(todaysGames)
    val todaysPlayers = if (current) Some(findTodaysPlayers(todaysGames)) else None
    val exemptPlayer = exemptPlayerFactory(todaysGames)
    val snapshots = snapshotsFactory(games)
    val headToHeads = headToHeadsFactory(games)
    val league = leagueFactory(snapshots, todaysPlayers, exemptPlayer)
    val streaks = streaksFactory(games, current)
    val numberOfGamesToday = todaysGames.size
    val lastGame = if (current) games.lastOption else None
    Stats(
      contiguousGameFilter, current, currentResults, 
      league, snapshots, headToHeads, streaks, exemptPlayer, lastGame, numberOfGamesToday)
  }
 
  def filterIsCurrent(contiguousGameFilter: ContiguousGameFilter): Boolean = contiguousGameFilter match {
    case BetweenGameFilter(from, to) => from.isToday || to.isToday || (now().isAfter(from) && now().isBefore(to))
    case SinceGameFilter(year, month, day) => {
      val from = Day(year, month, day)
      from.isToday || now().isAfter(from)
    }
    case UntilGameFilter(year, month, day) => {
      val to = Day(year, month, day)
      to.isToday || now().isBefore(to)
    }
    case YearGameFilter(year) => now().getYear() == year
    case MonthGameFilter(year, month) => now().getYear == year && now().getMonthOfYear == month
    case DayGameFilter(year, month, day) => 
      now().getYear == year && now().getMonthOfYear == month && now().getDayOfMonth == day
  }
  
  def findTodaysPlayers(todaysGames: Iterable[Game]): Set[Player] = 
    todaysGames.flatMap(_.participants).toSet
  
}