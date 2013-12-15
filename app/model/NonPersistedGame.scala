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

package model

import org.joda.time.DateTime
import model.JodaDateTime._
import scala.collection.immutable.Map
import scala.collection.SortedMap
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import model.Hand._

/**
 * A class for [[PersistedGame]] that allows other components to be tested without having to set up a database.
 * @author alex
 *
 */
case class NonPersistedGame(
  /**
   * The date and time this game was played.
   */
  datePlayed: DateTime,
  /**
   * The player who instigated this game.
   */
  instigator: Player,
  /**
   * The actual hands played during the game.
   */
  rounds: SortedMap[Int, Map[Player, Hand]]) extends Game

object NonPersistedGame {

  /**
   * Create a new game from a Squeryl grouped tuple.
   */
  def apply(info: ((PersistedGame, Player), Iterable[(PersistedGame, Player, Int, Player, Hand)])): NonPersistedGame = {
    val (key, value) = info
    val (persistedGame, instigator) = key
    val rounds = value.groupBy(kv => (kv._1, kv._2, kv._3))
    calculateGame(persistedGame, instigator, rounds)
  }

  def calculateGame(
    persistedGame: PersistedGame, instigator: Player, 
    gamesInstigatorsRoundsPlayersPlays: 
      Map[(PersistedGame, Player, Int), Iterable[(PersistedGame, Player, Int, Player, Hand)]]): NonPersistedGame = {
    val rounds = gamesInstigatorsRoundsPlayersPlays.foldLeft(SortedMap.empty[Int, Map[Player, Hand]]) { (rounds, round) =>
      val roundNumber = round._1._3
      val plays =
        round._2
          .groupBy(kv => (kv._1, kv._2, kv._3, kv._4))
          .foldLeft(Map.empty[Player, Hand]) { (plays, play) =>
            val player = play._1._4
            val hand = play._2.headOption.map(_._5).getOrElse(
              throw new IllegalStateException(s"Could not read rounds from game ID ${persistedGame.id}"))
            plays + (player -> hand)
          }
      rounds + (roundNumber -> plays)
    }
    val datePlayed = persistedGame.datePlayed
    NonPersistedGame(datePlayed, instigator, rounds)
  }
}