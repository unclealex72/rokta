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

import argonaut._, Argonaut._, DecodeResult._
import model.Hand

/**
 * A snapshot of the state of how many games a player has won or lost at a given time.
 * @author alex
 *
 */
case class Snapshot(
  /**
   * The current hand count for the player.
   */
  val handCount: HandCount,
  /**
   * The number of games a player has won.
   */
  val gamesWon: Int,
  /**
   * The number of games a player has lost.
   */
  val gamesLost: Int,
  /**
   * The number of rounds played in games the player has won.
   */
  val roundsDuringWinningGames: Int,
  /**
   * The number of rounds played in games the player has lost.
   */
  val roundsDuringLosingGames: Int) {
  
  def win(hands: Hand*): Snapshot =
    Snapshot(handCount ++ hands, gamesWon + 1, gamesLost, roundsDuringWinningGames + hands.size, roundsDuringLosingGames)

  def lose(hands: Hand*): Snapshot =
    Snapshot(handCount ++ hands, gamesWon, gamesLost + 1, roundsDuringWinningGames, roundsDuringLosingGames + hands.size)
}

object Snapshot {
  
  /**
   * An empty snapshot
   */
  val empty: Snapshot = Snapshot(HandCount(), 0, 0, 0, 0)
  
  /**
   * Convenience methods for bootstrapping snapshots.
   */
  def win(hands: Hand*) = empty.win(hands :_*)
  def lose(hands: Hand*) = empty.lose(hands :_*)
  
  /**
   * JSON Serialisation
   */
  implicit def snapshotJsonEncode: EncodeJson[Snapshot] = 
    jencode5L((s: Snapshot) => (s.handCount, s.gamesWon, s.gamesLost, s.roundsDuringWinningGames, s.roundsDuringLosingGames))(
        "handCount", "gamesWon", "gamesLost", "roundsDuringWinningGames", "roundsDuringLosingGames")
}