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

/**
 * A trait for [[PersistedGame]] that allows other components to be tested without having to set up a database.
 * @author alex
 *
 */
case class CalculatedGame(
  /**
   * The date and time this game was played.
   */
  datePlayed: DateTime,
  /**
   * The person who lost the game if it is finished or None otherwise.
   */
  loser: Option[String],
  /**
   * The total number of rounds played.
   */
  numberOfRounds: Int,
  /**
   * The player who instigated this game.
   */
  instigator: String,
  /**
   * The original participants.
   */
  participants: Set[String],
  /**
   * The number of rounds each player played.
   */
  roundsPlayed: Map[String, Int]) extends Game

object CalculatedGame {

  /**
   * Create a new game from a Squeryl grouped tuple.
   */
  def apply(info: ((PersistedGame, String), Iterable[(PersistedGame, String, Int, String, String)])): CalculatedGame = {
    val (key, value) = info
    val (persistedGame, instigator) = key
    val rounds = value.groupBy(kv => (kv._1, kv._2, kv._3))
    calculateGame(persistedGame, instigator, rounds)
  }

  def calculateGame(
    persistedGame: PersistedGame, instigator: String,
    gamesInstigatorsRoundsPlayersPlays: 
      Map[(PersistedGame, String, Int), Iterable[(PersistedGame, String, Int, String, String)]]): CalculatedGame = {
    val rounds = gamesInstigatorsRoundsPlayersPlays.foldLeft(SortedMap.empty[Int, Map[String, Hand]]) { (rounds, round) =>
      val roundNumber = round._1._3
      val plays =
        round._2
          .groupBy(kv => (kv._1, kv._2, kv._3, kv._4))
          .foldLeft(Map.empty[String, Hand]) { (plays, play) =>
            val player = play._1._4
            val hand = play._2.headOption.map(_._5).getOrElse(
              throw new IllegalStateException(s"Could not read rounds from game ID ${persistedGame.id}"))
            plays + (player -> Hand(hand).getOrElse(
              throw new IllegalStateException(s"${hand} is not a valid hand.")))
          }
      rounds + (roundNumber -> plays)
    }
    val datePlayed = persistedGame.datePlayed
    val numberOfRounds = rounds.size
    val loser = rounds.lastOption.map(_._2).flatMap(calculateLoserOfRound(_))
    val roundsPlayed = countRoundsPlayed(rounds)
    val participants = roundsPlayed.keys.toSet
    CalculatedGame(datePlayed, loser, numberOfRounds, instigator, participants, roundsPlayed)
  }

  /**
   * Calculate the loser of a round, if any.
   */
  def calculateLoserOfRound(plays: Map[String, Hand]): Option[String] = {
    val hands = plays.values.toSet
    hands.size match {
      case 2 =>
        val firstHand = hands.head
        val secondHand = hands.last
        val losingHand = if (firstHand.beats(secondHand)) secondHand else firstHand
        val losers = plays.filter(_._2 == losingHand).map(_._1)
        if (losers.size == 1) losers.headOption else None
      case _ => None
    }
  }
  
  /**
   * Count the number of times each player has played.
   */
  def countRoundsPlayed(rounds: SortedMap[Int, Map[String, Hand]]): Map[String, Int] = {
    rounds.values.foldLeft(Map.empty[String, Int]){ (roundsPlayed, plays) => 
      plays.keys.foldLeft(roundsPlayed){ (roundsPlayed, player) =>
        roundsPlayed + (player -> (1 + roundsPlayed.get(player).getOrElse(0)))
      }
    }
  }
}