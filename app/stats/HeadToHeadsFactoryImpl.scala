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

import model.Game
import model.Player

/**
 * The default implementation of [[HeadToHeadsFactory]].
 * @author alex
 *
 */
class HeadToHeadsFactoryImpl extends HeadToHeadsFactory {

  def apply(games: Iterable[Game]): Map[Player, Map[Player, Int]] = {
    games.filter(_.loser.isDefined).foldLeft(Map.empty[Player, Map[Player, Int]])(cumulateResults)
  }
  
  /**
   * Cumulate any head to heads in a single game.
   * @param headToHeads The current state of head to heads.
   * @param game The game with the current results.
   * @return A new map with any head to heads contained in the current game.
   */
  def cumulateResults(headToHeads: Map[Player, Map[Player, Int]], game: Game): Map[Player, Map[Player, Int]] = {
    val playersLeftInLastRound = game.roundsPlayed.filter{ case (_, rounds) => rounds == game.numberOfRounds }
    val loser = game.loser.get
    if (playersLeftInLastRound.size == 2) {
      val winner = playersLeftInLastRound.find{ case (player, _) => player != loser }.get._1
      increment(headToHeads, winner, loser)
    }
    else {
      headToHeads
    }
  }
  
  def increment(
    headToHeads: Map[Player, Map[Player, Int]], winner: Player, loser: Player): Map[Player, Map[Player, Int]] = {
    val winnersCurrentResults = headToHeads.get(winner).getOrElse(Map.empty[Player, Int])
    val winnersNewResults = winnersCurrentResults + (loser -> (winnersCurrentResults.get(loser).getOrElse(0) + 1))
    headToHeads + (winner -> winnersNewResults)
  }
}