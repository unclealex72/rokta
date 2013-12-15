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

import scala.collection.SortedMap
import org.joda.time.DateTime
import scala.collection.SortedSet
import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.BindingModule
import com.escalatesoft.subcut.inject.injected
import model.Player
import stats.LeagueRow._

/**
 * The default implementation of [[LeagueFactory]].
 *
 * @author alex
 *
 */
class LeagueFactoryImpl(_gapCalculator: Option[GapCalculator] = injected) extends LeagueFactory with AutoInjectable {

  val gapCalculator: GapCalculator = injectIfMissing(_gapCalculator)

  def apply(
    snapshots: SortedMap[DateTime, Map[Player, Snapshot]], 
    todaysPlayers: Option[Set[Player]], exemptPlayer: Option[Player]): SortedSet[LeagueRow] = {
    val leagues = snapshots.takeRight(2).toIndexedSeq.map(_._2).map(generateLeague)
    if (leagues.isEmpty) {
      SortedSet.empty
    } else {
      if (leagues.size == 1 || todaysPlayers.isEmpty) {
        decorateWithExemption(exemptPlayer)(leagues.last)
      } else {
        decorate(leagues(1), leagues(0), todaysPlayers.get, exemptPlayer)
      }
    }
  }

  def generateLeague: Map[Player, Snapshot] => SortedSet[LeagueRow] = { snapshots =>
    snapshots.toSeq.foldLeft(SortedSet.empty[LeagueRow]) { (leagueRows, playerSnapshot) =>
      val (player, snapshot) = playerSnapshot
      leagueRows + LeagueRow(player, snapshot.gamesWon, snapshot.gamesLost,
        snapshot.roundsDuringWinningGames, snapshot.roundsDuringLosingGames)
    }
  }

  def decorate(
    currentLeague: SortedSet[LeagueRow],
    previousLeague: SortedSet[LeagueRow],
    todaysPlayers: Set[Player], exemptPlayer: Option[Player]): SortedSet[LeagueRow] = {
    val decorators =
      Seq(decorateWithMovement(previousLeague), decorateWithCurrent(todaysPlayers),
        decorateWithExemption(exemptPlayer), decorateWithGap(todaysPlayers))
    decorators.foldLeft(currentLeague)((currentLeague, decorator) => decorator(currentLeague))
  }

  /**
   * Decorate each row with the player's movement since the previous league.
   * @param previousLeague The previous league containing the player's old positions.
   */
  def decorateWithMovement(previousLeague: SortedSet[LeagueRow]): SortedSet[LeagueRow] => SortedSet[LeagueRow] = {
    currentLeague =>
      val previousPositions = positionsForPlayers(previousLeague)
      val currentPositions = positionsForPlayers(currentLeague)
      currentLeague.map { leagueRow =>
        val Player = leagueRow.player
        previousPositions.get(Player) match {
          // Player didn't play previously
          case None => leagueRow
          // Take the difference of the current and previous positions.
          case Some(previousPosition) => leagueRow.withMovement(currentPositions(Player) - previousPosition)
        }
      }
  }

  /**
   * Calculate the position of each player in a league
   */
  def positionsForPlayers(league: SortedSet[LeagueRow]): Map[Player, Int] =
    league.zipWithIndex.foldLeft(Map.empty[Player, Int]) { (positions, leagueRowAndIndex) =>
      val (leagueRow, index) = leagueRowAndIndex
      positions + (leagueRow.player -> index)
    }

  /**
   * Decorate league rows for current players.
   */
  def decorateWithCurrent(todaysPlayers: Set[Player]): SortedSet[LeagueRow] => SortedSet[LeagueRow] =
    decorateBoolean(todaysPlayers, leagueRow => leagueRow.withCurrentlyPlaying)

  /**
   * Decorate league rows for exempt players.
   */
  def decorateWithExemption(exemptPlayer: Option[Player]): SortedSet[LeagueRow] => SortedSet[LeagueRow] =
    decorateBoolean(exemptPlayer, leagueRow => leagueRow.withExempt)

  /**
   * A convenience method for decorating league rows with a boolean value that indicates a player is in a known
   * list of players.
   */
  def decorateBoolean(players: Traversable[Player], decorator: LeagueRow => Boolean => LeagueRow): SortedSet[LeagueRow] => SortedSet[LeagueRow] = { currentLeague =>
    currentLeague.map { leagueRow =>
      decorator(leagueRow)(players.exists(_ == leagueRow.player))
    }
  }

  /**
   * Decorate all but the last row with gaps.
   */
  def decorateWithGap(todaysPlayers: Set[Player]): SortedSet[LeagueRow] => SortedSet[LeagueRow] = { league =>
    // Calculate the gaps by player name.
    val leagueRowPairs = league.toIndexedSeq.sliding(2)
    val gapsByPlayer = leagueRowPairs.foldLeft(Map.empty[Player, Option[Int]]) { (gapsByPlayer, leagueRows) =>
      val (topLeagueRow, bottomLeagueRow) = (leagueRows(0), leagueRows(1))
      val gap = gapCalculator.calculateGap(topLeagueRow, bottomLeagueRow)
      gapsByPlayer + (bottomLeagueRow.player -> gap)
    }
    // Now decorate the original league.
    league.map { leagueRow =>
      val Player = leagueRow.player
      gapsByPlayer.get(Player) match {
        case Some(Some(gap)) => leagueRow.withGap(gap)
        case _ => leagueRow
      }
    }
  }
}