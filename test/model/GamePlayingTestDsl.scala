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

import java.util.Date
import java.text.SimpleDateFormat

import model.RoktaSchema._

/**
 * An object containing a DSL for creating test games. For convenience, it is assumed that one person counts
 * all the rounds.
 * @author alex
 *
 */
object GamePlayingTestDsl {

  val DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy hh:mm")

  // Players
  
  implicit class Player(val person: Person) {
    
    def instigatesAt(when: String) = GameBuilder(person, DATE_FORMATTER parse when, Seq.empty)
    
    def plays(hand: Hand): Pair[Person, Hand] = person -> hand
    
  }
  
  case class GameBuilder(val instigator: Person, val when: Date, val allPlays: Seq[Map[Person, Hand]]) {
    
    def and(plays : Pair[Person, Hand]*): GameBuilder = {
      val playMap = plays.foldLeft(Map.empty[Person, Hand]){ case (plays, play) => plays + play }
      GameBuilder(instigator, when, allPlays :+ playMap)
    }
    def countedBy(counter: Person): Game = {
      val game = Game(instigator, when)
      allPlays.foldLeft(game) { case (game, plays) => game.addRound(counter, plays)}
    }
  }  
}