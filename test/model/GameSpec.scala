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

import org.specs2.mutable.Specification
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter

import model.GamePlayingTestDsl.Player
import model.Hand.PAPER
import model.Hand.ROCK
import model.Hand.SCISSORS
import model.RoktaSchema.__thisDsl
import model.RoktaSchema.anyRef2ActiveTransaction

/**
 * Tests for game playing mechanics.
 * @author alex
 *
 */
class GameSpec extends Specification {

  /**
   * Wrap tests with database creation and transactions
   */
  def txn[B](block: Game => IndexedSeq[Round] => Person => Person => Person => Person => B) = {

    import model.RoktaSchema._

    Class forName "org.h2.Driver"
    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:mem:", "", ""),
        new H2Adapter))
    RoktaSchema.inTransaction {
      RoktaSchema.create
      val freddie  = Person(0, "Freddie", "freddie@queen.com", "BLACK")
      val brian = Person(0, "Brian", "brian@queen.com", "BLUE")
      val roger = Person(0, "Roger", "roger@queen.com", "RED")
      val john = Person(0, "John", "john@queen.com", "WHITE")

      Seq(freddie, roger, brian, john).foreach { person =>
        person.save
      }
      val game: Game = freddie instigatesAt ("05/09/1972 09:12") and
        (freddie plays SCISSORS, roger plays SCISSORS, brian plays SCISSORS) and
        (freddie plays ROCK, roger plays SCISSORS, brian plays PAPER) and
        (freddie plays ROCK, roger plays ROCK, brian plays PAPER) and
        (freddie plays ROCK, roger plays SCISSORS) countedBy (john)
      game.save
      block(game)(game.rounds.toIndexedSeq)(freddie)(roger)(brian)(john)
    }
  }

  "Creating a new game at a given date and time" should {
    "set the correct year played" in txn { game => rounds => freddie => roger => brian => john =>
        game.yearPlayed must be equalTo (1972)
    }
    "set the correct month played" in txn { game => rounds => freddie => roger => brian => john =>
      game.monthPlayed must be equalTo (8)
    }
    "set the correct week played" in txn { game => rounds => freddie => roger => brian => john =>
      game.weekPlayed must be equalTo (36)
    }
    "set the correct day played" in txn { game => rounds => freddie => roger => brian => john =>
      game.dayPlayed must be equalTo (5)
    }
  }

  "The first round" should {
    "be drawn" in txn { game => rounds => freddie => roger => brian => john => 
      val roundOne = rounds(0)
      roundOne.losers must containTheSameElementsAs(Seq(roger, brian, freddie))
    }
  }

  "The second round" should {
    "also be drawn" in txn { game => rounds => freddie => roger => brian => john =>
      val roundTwo = rounds(1)
      roundTwo.losers must containTheSameElementsAs(Seq(roger, brian, freddie))
    }
  }

  "The third round" should {
    "be won by Brian" in txn { game => rounds => freddie => roger => brian => john =>
      val roundThree = rounds(2)
      roundThree.losers must containTheSameElementsAs(Seq(roger, freddie))
    }
  }

  "The fourth round" should {
    "be won by Freddie" in txn { game => rounds => freddie => roger => brian => john =>
      val roundFour = rounds(3)
      roundFour.losers must containTheSameElementsAs(Seq(roger))
    }
  }

  "The whole game" should {
    "be lost by Roger" in txn { game => rounds => freddie => roger => brian => john =>
      game.loser must be equalTo (Some(roger))
    }
  }
}