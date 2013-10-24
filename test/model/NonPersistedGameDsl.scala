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
/**
 * @author alex
 *
 */
object NonPersistedGameDsl {

  /**
   * Some players
   */
  val freddie: Player = NonPersistedPlayer("Freddie", "freddie@queen.com", BLACK)
  val brian: Player = NonPersistedPlayer("Brian", "brian@queen.com", BLUE)
  val roger: Player = NonPersistedPlayer("Roger", "roger@queen.com", RED)
  val john: Player = NonPersistedPlayer("John", "john@queen.com", WHITE)

  implicit class PlayerImplicits(player: Player) {
    
    def losesAt(dateTime: DateTime): GameBuilder = GameBuilder(player, dateTime, Map.empty[Player, Int])
    
    def plays(rounds: Int): Pair[Player, Int] = player -> rounds
  }  
}

case class GameBuilder(val _loser: Player, val datePlayed: DateTime, otherRoundsPlayed: Map[Player, Int]) extends Game {
  
  def and: Pair[Player, Int] => GameBuilder = play => GameBuilder(_loser, datePlayed, otherRoundsPlayed + play)
  
  def whilst = and
  
  def loser: Option[Player] = Some(_loser)
  
  def participants: Set[Player] = otherRoundsPlayed.keySet + _loser
  
  def numberOfRounds: Int = otherRoundsPlayed.values.max
  
  def roundsPlayed: Map[Player, Int] = otherRoundsPlayed + (_loser -> numberOfRounds)
}