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

import model.GamePlayingTestDsl._
import model.Hand._
import org.specs2.mutable.Specification
import scala.collection.JavaConversions._

/**
 * Tests for game playing mechanics.
 * @author alex
 *
 */
class GameSpec extends Specification {

  val game: Game = Freddie instigatesAt("05/09/1972 09:12") and
    (Freddie plays SCISSORS, Roger plays SCISSORS, Brian plays SCISSORS) and
    (Freddie plays ROCK, Roger plays SCISSORS, Brian plays PAPER) and
    (Freddie plays ROCK, Roger plays ROCK, Brian plays PAPER) and
    (Freddie plays ROCK, Roger plays SCISSORS) countedBy (John)
    
  val rounds = game.rounds.toArray(Array.empty[Round])
  
  "Creating a new game at a given date and time" should {
    "set the correct year played" in {
      game.yearPlayed must be equalTo(1972)
    }
    "set the correct month played" in {
      game.monthPlayed must be equalTo(8)
    }
    "set the correct week played" in {
      game.weekPlayed must be equalTo(36)
    }
    "set the correct day played" in {
      game.dayPlayed must be equalTo(5)
    }
  }
  
  "The first round" should {
    "be drawn" in {
      val roundOne = rounds(0)
      roundOne.losers must containTheSameElementsAs(Seq(Roger, Brian, Freddie))
    }
  }
  
  "The second round" should {
    "also be drawn" in {
      val roundTwo = rounds(1)
      roundTwo.losers must containTheSameElementsAs(Seq(Roger, Brian, Freddie))
    }
  }

  "The third round" should {
    "be won by Brian" in {
      val roundThree = rounds(2)
      roundThree.losers must containTheSameElementsAs(Seq(Roger, Freddie))
    }
  }

  "The fourth round" should {
    "be won by Freddie" in {
      val roundFour = rounds(3)
      roundFour.losers must containTheSameElementsAs(Seq(Roger))
    }
  }
  
  "The whole game" should {
    "be lost by Roger" in {
      game.loser must be equalTo(Roger)
    }
  }
}