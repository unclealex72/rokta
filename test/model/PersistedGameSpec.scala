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
import org.joda.time.format.DateTimeFormat
import org.specs2.mutable.Specification
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import dao.EntryPoint._
import model.GamePlayingTestDsl._
import model.Hand._
import dao.RoktaSchema
import dates.DaysAndTimes._

/**
 * Tests for game playing mechanics.
 * @author alex
 *
 */
class PersistedGameSpec extends Specification {

  /**
   * Wrap tests with database creation and transactions
   */
  def txn[B](block: PersistedGame => IndexedSeq[Round] => Player => Player => Player => Player => B) = {

    import dao.RoktaSchema._

    Class forName "org.h2.Driver"
    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:mem:", "", ""),
        new H2Adapter))
    inTransaction {
      RoktaSchema.create
      val freddie  = Player(0, "Freddie", "freddie@queen.com", "BLACK")
      val brian = Player(0, "Brian", "brian@queen.com", "BLUE")
      val roger = Player(0, "Roger", "roger@queen.com", "RED")
      val john = Player(0, "John", "john@queen.com", "WHITE")

      Seq(freddie, roger, brian, john).foreach { person =>
        person.save
      }
      val game: PersistedGame = freddie instigatesAt (September(5, 1972).at(9, 12)) and
        (freddie plays SCISSORS, roger plays SCISSORS, brian plays SCISSORS) and
        (freddie plays ROCK, roger plays SCISSORS, brian plays PAPER) and
        (freddie plays ROCK, roger plays ROCK, brian plays PAPER) and
        (freddie plays ROCK, roger plays SCISSORS) countedBy (john)
      game.save
      block(game)(game.rounds.toIndexedSeq)(freddie)(roger)(brian)(john)
    }
  }

  "A newly created game" should {
    "have Roger, Brian and Freddie as participants" in txn { game => rounds => freddie => roger => brian => john => 
      game.participants must contain(exactly(roger, brian, freddie))
    }
  }
  "The first round" should {
    "be drawn" in txn { game => rounds => freddie => roger => brian => john => 
      val roundOne = rounds(0)
      roundOne.losers must contain(exactly(roger, brian, freddie))
    }
  }

  "The second round" should {
    "also be drawn" in txn { game => rounds => freddie => roger => brian => john =>
      val roundTwo = rounds(1)
      roundTwo.losers must contain(exactly(roger, brian, freddie))
    }
  }

  "The third round" should {
    "be won by Brian" in txn { game => rounds => freddie => roger => brian => john =>
      val roundThree = rounds(2)
      roundThree.losers must contain(exactly(roger, freddie))
    }
  }

  "The fourth round" should {
    "be won by Freddie" in txn { game => rounds => freddie => roger => brian => john =>
      val roundFour = rounds(3)
      roundFour.losers must contain(exactly(roger))
    }
  }

  "The whole game" should {
    "be lost by Roger" in txn { game => rounds => freddie => roger => brian => john =>
      game.loser must be equalTo (Some(roger))
    }
  }
}
/**
 * An object containing a DSL for creating test games. For convenience, it is assumed that one person counts
 * all the rounds.
 * @author alex
 *
 */
object GamePlayingTestDsl {

  // Players
  
  implicit class PlayerImplicits(val person: Player) {
    
    def instigatesAt(when: DateTime) = GameBuilder(person, when, Seq.empty)
    
    def plays(hand: Hand): Pair[Player, Hand] = person -> hand
    
  }
  
  case class GameBuilder(val instigator: Player, val when: DateTime, val allPlays: Seq[Map[Player, Hand]]) {
    
    def and(plays : Pair[Player, Hand]*): GameBuilder = {
      val playMap = plays.foldLeft(Map.empty[Player, Hand]){ case (plays, play) => plays + play }
      GameBuilder(instigator, when, allPlays :+ playMap)
    }
    def countedBy(counter: Player): PersistedGame = {
      val game = PersistedGame(instigator, when)
      allPlays.foldLeft(game) { case (game, plays) => game.addRound(counter, plays)}
    }
  }  
}