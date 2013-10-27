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
import model.Player
import org.joda.time.DateTime
import scala.collection.SortedSet
import model.Game

/**
 * @author alex
 *
 */
class StreaksFactoryImpl extends StreaksFactory {

  def apply(games: SortedSet[Game], current: Boolean): Streaks = {
    val streaksStatus = games.filter(_.loser.isDefined).foldLeft(StreaksStatus())(updateStreaksStatusPerGame)
    addRemainingStreaks(current, streaksStatus)
  }

  /**
   * Add all the remaining streaks to the historical streaks.
   * @param current True if the overriding filter is current, false otherwise. If this is false then the non-finished
   * streaks will be added as non-current.
   * @param streaksStatus The current state of all streaks.
   * @return The historical and remaining streaks.
   * 
   */
  def addRemainingStreaks(current: Boolean, streaksStatus: StreaksStatus): Streaks = {
    val historicalStreaks = streaksStatus.streaks
    val makeCurrent: Streak => Streak = if (current) {streak => streak.makeCurrent} else {streak => streak }
    val currentStreakBuilders = Map(
      streaksStatus.winningStreaks -> addWinningStreak, streaksStatus.losingStreaks -> addLosingStreak)
    
    currentStreakBuilders.foldLeft(historicalStreaks){ (streaks, currentStreakBuilder) => 
      currentStreakBuilder match {
        case(currentStreaks, addStreak) =>
          currentStreaks.values.foldLeft(streaks) { (streaks, streak) =>
            if (streak.candidate) addStreak(streaks)(makeCurrent(streak)) else streaks
          }
      }
    }
  }
  
  /**
   * Add a remaining streak to the historical streaks.
   */
  def addCurrentStreak: (Streaks => Streak => Streaks) => (Streaks, Streak) => Streaks = {
    addStreak => (streaks, streak) => if (streak.candidate) addStreak(streaks)(streak.makeCurrent) else streaks
  }
  
  /**
   * Add a wining streak to a set of streaks.
   */
  def addWinningStreak: Streaks => Streak => Streaks = streaks => streak => streaks.withWinningStreak(streak)

  /**
   * Add a losing streak to a set of streaks.
   */
  def addLosingStreak: Streaks => Streak => Streaks = streaks => streak => streaks.withLosingStreak(streak)

  /**
   * Update the status of the streaks with the result of a game.
   */
  def updateStreaksStatusPerGame: (StreaksStatus, Game) => StreaksStatus = { (streaksStatus, game) =>
    game.participants.foldLeft(streaksStatus)(updateStreaksStatusPerParticipant(game))
  }

  /**
   * Update the status of the streaks with the results of a game for a player.
   */
  def updateStreaksStatusPerParticipant: Game => (StreaksStatus, Player) => StreaksStatus = {
    game =>
      (streaksStatus, player) =>
        val (sameStreaks, oppositeStreaks, addStreak, streaksOrdering) = if (Some(player) == game.loser) {
          val swap = (pair: Pair[Map[Player, Streak], Map[Player, Streak]]) => (pair._2, pair._1)
          (streaksStatus.losingStreaks, streaksStatus.winningStreaks, addWinningStreak, swap)
        }
        else {
          val dontswap = (pair: Pair[Map[Player, Streak], Map[Player, Streak]]) => pair
          (streaksStatus.winningStreaks, streaksStatus.losingStreaks, addLosingStreak, dontswap)
        }
        updateStreaksForWinnerOrLoser(
          streaksStatus, player, game, sameStreaks, oppositeStreaks, addStreak, streaksOrdering)
  }

  /**
   * Update the streaks for a single person, but at this point it is known whether they won the game or lost.
   * The required streaks and function to add a streak are supplied.
   */
  def updateStreaksForWinnerOrLoser(
    streaksStatus: StreaksStatus, player: Player, game: Game,
    sameStreaks: Map[Player, Streak], oppositeStreaks: Map[Player, Streak],
    addStreak: Streaks => Streak => Streaks,
    streaksOrdering: Pair[Map[Player, Streak], Map[Player, Streak]] => Pair[Map[Player, Streak], Map[Player, Streak]]) = {
    // If the winner/loser has a current winning/losing streak we add to that.
    // If the winner/loser has a current losing/winning streak we cancel that and, if it is a candidate to be a streak, 
    // add it to the streaks.
    val playersCurrentSameStreak = sameStreaks.get(player)
    val newSameStreaks = playersCurrentSameStreak match {
      case Some(sameStreak) =>
        sameStreaks + (player -> sameStreak.extendTo(game.datePlayed))
      case _ => sameStreaks + (player -> Streak(player.name, game.datePlayed))
    }
    val playersCurrentOppositeStreak = oppositeStreaks.get(player)
    val (newOppositeStreaks, newStreaks): (Map[Player, Streak], Streaks) = playersCurrentOppositeStreak match {
      case Some(oppositeStreak) => {
        val newOppositeStreaks = oppositeStreaks - player
        val newStreaks =
          if (oppositeStreak.candidate) addStreak(streaksStatus.streaks)(oppositeStreak) else streaksStatus.streaks
        (newOppositeStreaks, newStreaks)
      }
      case _ => (oppositeStreaks, streaksStatus.streaks)
    }
    val newHistoricalStreaks = streaksOrdering(newSameStreaks -> newOppositeStreaks)
    StreaksStatus(newHistoricalStreaks._1, newHistoricalStreaks._2, newStreaks)
  }
}

/**
 * A snapshot of historical winning and losing streaks along with any current streaks at a given time.
 */
case class StreaksStatus(
  winningStreaks: Map[Player, Streak],
  losingStreaks: Map[Player, Streak],
  streaks: Streaks)

object StreaksStatus {

  def apply(): StreaksStatus = StreaksStatus(Map.empty, Map.empty, Streaks())
}