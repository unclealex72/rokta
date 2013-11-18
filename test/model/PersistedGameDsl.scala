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

/**
 * A DSL for creating persisted games in tests.
 * @author alex
 *
 */
trait PersistedGameDsl {

  implicit def gameBuilderToGame(gameBuilder: GameBuilder): PersistedGame = gameBuilder.finish

  // Players
  
  implicit class PlayerImplicits(val person: PersistedPlayer) {
    
    def instigatesAt(when: DateTime) = GameBuilder(person, when, Seq.empty)
    
    def plays(hand: Hand): Pair[PersistedPlayer, Hand] = person -> hand
    
  }
  
  case class GameBuilder(val instigator: PersistedPlayer, val when: DateTime, val allPlays: Seq[Map[PersistedPlayer, Hand]]) {
    
    def and(plays : Pair[PersistedPlayer, Hand]*): GameBuilder = {
      val playMap = plays.foldLeft(Map.empty[PersistedPlayer, Hand]){ case (plays, play) => plays + play }
      GameBuilder(instigator, when, allPlays :+ playMap)
    }
    
    def finish: PersistedGame = {
      val game = PersistedGame(instigator, when)
      allPlays.foldLeft(game) { case (game, plays) => game.addRound(plays)}
    }
  }  

}