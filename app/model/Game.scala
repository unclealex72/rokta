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
import scala.collection.SortedMap

/**
 * A trait for [[PersistedGame]] that allows other components to be tested without having to set up a database.
 * @author alex
 *
 */
trait Game extends Ordered[Game] {

  /**
   * The date and time this game was played.
   */
  def datePlayed: DateTime
  
  /**
   * The person who lost the game if it is finished or None otherwise.
   */
  lazy val loser: Option[String] = rounds.lastOption.map(_._2).flatMap{ plays =>
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
   * The total number of rounds played.
   */
  lazy val numberOfRounds: Int = rounds.size
  
  /**
   * The player who instigated this game.
   */
  def instigator: String
  
  /**
   * The original participants.
   */
  lazy val participants: Set[String] = roundsPlayed.keySet
  
  /**
   * The number of rounds each player played.
   */
  lazy val roundsPlayed: Map[String, Int] = 
    rounds.values.foldLeft(Map.empty[String, Int]){ (roundsPlayed, plays) => 
      plays.keys.foldLeft(roundsPlayed){ (roundsPlayed, player) =>
        roundsPlayed + (player -> (1 + roundsPlayed.get(player).getOrElse(0)))
      }
    }
 
  /**
   * The actual hands played during the game.
   */
  def rounds: SortedMap[Int, Map[String, Hand]]
  
  /**
   * Games are ordered by the date and time they were played.
   */
  def compare(g: Game): Int = datePlayed.compareTo(g.datePlayed)
}