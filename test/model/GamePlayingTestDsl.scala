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

/**
 * An object containing a DSL for creating test games. For convenience, it is assumed that one person counts
 * all the rounds.
 * @author alex
 *
 */
object GamePlayingTestDsl {

  val DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy hh:mm")

  // Players
  object Freddie extends Person(null, "Freddie", "freddie@queen.com", "BLACK")
  object Brian extends Person(null, "Brian", "brian@queen.com", "BLUE")
  object Roger extends Person(null, "Roger", "roger@queen.com", "RED")
  object John extends Person(null, "John", "john@queen.com", "WHITE")
  
  implicit class Player(val person: Person) {
    
    def instigatesAt(when: String) = GameBuilder(person, when)
    
    def plays(hand: Hand) = Play(person, hand)
    
  }
  
  case class GameBuilder(val instigator: Person, val when: Date, val allPlays: Seq[Seq[Play]]) {
    
    def and(plays : Play*): GameBuilder = GameBuilder(instigator, when, allPlays :+ plays)
    def countedBy(counter: Person): Game = {
      val rounds : Seq[Round] = allPlays.zipWithIndex.map({ case (plays: Seq[Play], round: Int) => 
        Round(round, counter, plays :_*)
      })
      Game(instigator, when, rounds :_*)
    }
  }
  
  object GameBuilder {
    def apply(instigator: Person, when: String): GameBuilder = 
      GameBuilder(instigator, DATE_FORMATTER.parse(when), List.empty[Seq[Play]])
  }
}

object Example {
  
  import model.GamePlayingTestDsl._
  import model.Hand._
  
  val game: Game = Freddie instigatesAt("05/09/1972 09:12") and 
    (Freddie plays ROCK, Roger plays ROCK, Brian plays PAPER) and
    (Freddie plays ROCK, Roger plays SCISSORS) countedBy (Brian)
}