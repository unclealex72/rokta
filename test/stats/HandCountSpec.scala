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
class HandCountSpec extends Specification with NonPersistedGameDsl with DaysAndTimes with UtcChronology {

  "Adding a hand to an empty handcount" should {

    "register it for both initial- and all-hand counts if it is from the first round" in {
      val handCount = HandCount() + (1, ROCK)
      handCount.countsForFirstRounds must be equalTo(Map(ROCK -> 1))
      handCount.countsForAllRounds must be equalTo(Map(ROCK -> 1))
    }
    "register it for only all-hand counts if it is not from the first round" in {
      val handCount = HandCount() + (2, ROCK)
      handCount.countsForFirstRounds must beEmpty
      handCount.countsForAllRounds must be equalTo(Map(ROCK -> 1))
    }
  }
  
  "Counting multiple hands" should {
    "store the total number of each hands played for one game" in {
      val handCount = HandCount() ++ Seq(ROCK, ROCK, SCISSORS, SCISSORS, PAPER)
      handCount.countsForFirstRounds must be equalTo(Map(ROCK -> 1))
      handCount.countsForAllRounds must be equalTo(Map(ROCK -> 2, SCISSORS -> 2, PAPER -> 1))
    }
    "store the total number of each hands played for more than one game" in {
      val handCount = HandCount() ++ Seq(ROCK, SCISSORS) ++ Seq(SCISSORS, PAPER) ++ Seq(ROCK, ROCK, SCISSORS, SCISSORS, PAPER)
      handCount.countsForFirstRounds must be equalTo(Map(ROCK -> 2, SCISSORS ->1))
      handCount.countsForAllRounds must be equalTo(Map(ROCK -> 3, SCISSORS -> 4, PAPER -> 2))
    }
  }
}