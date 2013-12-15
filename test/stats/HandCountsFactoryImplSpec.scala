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

package stats

import org.specs2.mutable.Specification
import model.NonPersistedGameDsl
import dates.DaysAndTimes
import dates.UtcChronology
import model.Hand._
import model.Player
/**
 * @author alex
 *
 */
class HandCountsFactoryImplSpec extends Specification with NonPersistedGameDsl with DaysAndTimes with UtcChronology {

  val handCountsFactory = new HandCountsFactoryImpl
  
  "Adding a hand to an empty handcount" should {
    val empty = Map.empty[Player, HandCount]
    
    "register it for both initial- and all-hand counts if it is from the first round" in {
      val handCounts = handCountsFactory.countHandsForPlay(1)(empty, (freddie, ROCK))
      handCounts.keySet must contain(freddie)
      val handCount = handCounts(freddie)
      handCount.countsForFirstRounds must be equalTo(Map(ROCK -> 1))
      handCount.countsForAllRounds must be equalTo(Map(ROCK -> 1))
    }
    "register it for only all-hand counts if it is not from the first round" in {
      val handCounts = handCountsFactory.countHandsForPlay(2)(empty, (freddie, ROCK))
      handCounts.keySet must contain(freddie)
      val handCount = handCounts(freddie)
      handCount.countsForFirstRounds must beEmpty
      handCount.countsForAllRounds must be equalTo(Map(ROCK -> 1))
    }
  }
  
  "Counting multiple games" should {
    "count all rounds for all players" in {
      val gameOne = 
        at(September(5, 1972) at midday, freddie plays ROCK, roger plays SCISSORS, brian plays PAPER, john plays ROCK) and
        (freddie plays ROCK, roger plays ROCK, brian plays ROCK, john plays SCISSORS)
      val gameTwo = 
        at(September(5, 1972) at (1 oclock).pm, freddie plays SCISSORS, roger plays SCISSORS, brian plays ROCK, john plays ROCK) and
        (freddie plays SCISSORS, roger plays SCISSORS) and
        (freddie plays PAPER, roger plays SCISSORS)
      handCountsFactory(Seq(gameOne, gameTwo)) must be equalTo(Map(
        freddie -> HandCount(Map(ROCK -> 1, SCISSORS -> 1), Map(ROCK ->2, SCISSORS -> 2, PAPER -> 1)),
        roger -> HandCount(Map(SCISSORS -> 2), Map(ROCK -> 1, SCISSORS -> 4)),
        brian -> HandCount(Map(ROCK -> 1, PAPER -> 1), Map(ROCK -> 2, PAPER -> 1)),
        john -> HandCount(Map(ROCK -> 2), Map(ROCK -> 2, SCISSORS -> 1))
      ))
    }
  }
}