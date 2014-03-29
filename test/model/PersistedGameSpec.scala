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
import org.specs2.mutable.Specification
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import dao.SquerylDao
import dates.DaysAndTimes
import dates.IsoChronology
import model.Hand._
import dao.RoktaSchema

/**
 * Tests for game playing mechanics.
 * @author alex
 *
 */
class PersistedGameSpec extends Specification with DaysAndTimes with IsoChronology with PersistedGameDsl {

  val now = September(5, 1972) at (9:> 12)
  type Freddie = Player
  type Brian = Player
  type Roger = Player
  type John = Player
  
  /**
   * Wrap tests with database creation and transactions
   */
  def txn[B](block: Game => Freddie => Brian => Roger => John => B)(implicit roundCount: Int) = {

    import dao.RoktaSchema._
    import dao.EntryPoint._

    Class forName "org.h2.Driver"
    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:mem:", "", ""),
        new H2Adapter))
    inTransaction {
      create
      val freddie  = PersistedPlayer("Freddie")
      val brian = PersistedPlayer("Brian")
      val roger = PersistedPlayer("Roger")
      val john = PersistedPlayer("John")

      Seq(freddie, roger, brian, john).foreach { person =>
        person.save
      }
      val rounds: Seq[Seq[Pair[PersistedPlayer, Hand]]] = Seq(
        Seq(freddie plays SCISSORS, roger plays SCISSORS, brian plays SCISSORS),
        Seq(freddie plays ROCK, roger plays SCISSORS, brian plays PAPER),
        Seq(freddie plays ROCK, roger plays ROCK, brian plays PAPER),
        Seq(freddie plays ROCK, roger plays SCISSORS)) take (roundCount)
        
      val persistedGame: PersistedGame = 
        rounds.foldLeft(freddie instigatesAt now) { (game, plays) =>
          game and (plays: _*)
        }
      val game = new SquerylDao().filteredGames(g => g.id === persistedGame.id)
      block(game.head)(freddie)(brian)(roger)(john)
    }
  }

  "After one round the game" should {
    implicit val rounds = 1
    "start at the correct time" in txn { game => freddie => brian => roger => john =>
      game.datePlayed must be equalTo(now)
    }
    "have all three participants" in txn { game => freddie => brian => roger => john =>
      game.participants must contain(brian, roger, freddie).exactly
    }
    "be instigated by freddie" in txn { game => freddie => brian => roger => john =>
      game.instigator must be equalTo(freddie)
    }
    "have one round" in txn { game => freddie => brian => roger => john => 
      game.numberOfRounds must be equalTo(1)
      game.roundsPlayed must contain(freddie -> 1, brian -> 1, roger -> 1).exactly
    }
    "have no losers" in txn { game => freddie => brian => roger => john =>
      game.loser must beNone
    }
  }

  "After two rounds the game" should {
    implicit val rounds = 2
    "start at the correct time" in txn { game => freddie => brian => roger => john =>
      game.datePlayed must be equalTo(now)
    }
    "have all three participants" in txn { game => freddie => brian => roger => john =>
      game.participants must contain(brian, roger, freddie).exactly
    }
    "be instigated by freddie" in txn { game => freddie => brian => roger => john =>
      game.instigator must be equalTo(freddie)
    }
    "have two rounds" in txn { game => freddie => brian => roger => john => 
      game.numberOfRounds must be equalTo(2)
      game.roundsPlayed must contain(freddie -> 2, brian -> 2, roger -> 2).exactly
    }
    "have no losers" in txn { game => freddie => brian => roger => john =>
      game.loser must beNone
    }
  }

  "After three rounds the game" should {
    implicit val rounds = 3
    "start at the correct time" in txn { game => freddie => brian => roger => john =>
      game.datePlayed must be equalTo(now)
    }
    "have all three participants" in txn { game => freddie => brian => roger => john =>
      game.participants must contain(brian, roger, freddie).exactly
    }
    "be instigated by freddie" in txn { game => freddie => brian => roger => john =>
      game.instigator must be equalTo(freddie)
    }
    "have three rounds" in txn { game => freddie => brian => roger => john => 
      game.numberOfRounds must be equalTo(3)
      game.roundsPlayed must contain(freddie -> 3, brian -> 3, roger -> 3).exactly
    }
    "have no losers" in txn { game => freddie => brian => roger => john =>
      game.loser must beNone
    }
  }

  "After four rounds the game" should {
    implicit val rounds = 4
    "start at the correct time" in txn { game => freddie => brian => roger => john =>
      game.datePlayed must be equalTo(now)
    }
    "have all three participants" in txn { game => freddie => brian => roger => john =>
      game.participants must contain(brian, roger, freddie).exactly
    }
    "be instigated by freddie" in txn { game => freddie => brian => roger => john =>
      game.instigator must be equalTo(freddie)
    }
    "have four rounds" in txn { game => freddie => brian => roger => john => 
      game.numberOfRounds must be equalTo(4)
      game.roundsPlayed must contain(freddie -> 4, brian -> 3, roger -> 4).exactly
    }
    "be lost by roger" in txn { game => freddie => brian => roger => john =>
      game.loser must beSome(roger)
    }
  }
}