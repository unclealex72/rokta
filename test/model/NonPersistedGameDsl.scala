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

import model.Colour._
import org.joda.time.DateTime
import scala.collection.SortedMap
/**
 * @author alex
 *
 */
trait NonPersistedGameDsl {

  /**
   * Some players
   */
  val freddie: Player = NonPersistedPlayer("Freddie", "freddie@queen.com", BLACK)
  val brian: Player = NonPersistedPlayer("Brian", "brian@queen.com", BLUE)
  val roger: Player = NonPersistedPlayer("Roger", "roger@queen.com", RED)
  val john: Player = NonPersistedPlayer("John", "john@queen.com", WHITE)

  def at(datePlayed: DateTime, play: Pair[Player, Hand], plays: Pair[Player, Hand]*) = 
    new GameBuilder(datePlayed, freddie, SortedMap.empty[Int, Map[Player, Hand]]).and(play, plays :_*)

  implicit class PlayerImplicits(player: Player) {
    
    def plays(hand: Hand): Pair[Player, Hand] = player -> hand
  }  
}

class GameBuilder(
  /**
   * The date and time this game was played.
   */
  override val datePlayed: DateTime,
  /**
   * The player who instigated this game.
   */
  override val instigator: Player,
  /**
   * The actual hands played during the game.
   */
  override val rounds: SortedMap[Int, Map[Player, Hand]]) 
  extends NonPersistedGame(datePlayed, instigator, rounds) with NonPersistedGameDsl {

  def and(play: Pair[Player, Hand], plays: Pair[Player, Hand]*) = {
    val nextRound = rounds.size + 1
    val round = (Seq(play) ++ plays).foldLeft(Map.empty[Player, Hand])((plays, play) => plays + (play._1 -> play._2))
    new GameBuilder(datePlayed, instigator, rounds + (nextRound -> round))
  }
}