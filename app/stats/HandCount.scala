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

import model.Hand

/**
 * A class to hold the counts of hands played for a player.
 * @author alex
 *
 */
case class HandCount(
  /**
   * A count of the hands played in the first rounds of games.
   */
  countsForFirstRounds: Map[Hand, Int],
  /**
   * A count of the hands played in all rounds of games.
   */
  countsForAllRounds: Map[Hand, Int]) {
  
  /**
   * Add an extra hand to a hand count
   */
  def +(round: Int, hand: Hand): HandCount = {
    def add(currentCount: Map[Hand, Int], accept: Int => Boolean): Map[Hand, Int] = {
      if (accept(round)) {
        val currentCountForHand = currentCount.getOrElse(hand, 0)
        currentCount + (hand -> (currentCountForHand + 1))
      }
      else {
        currentCount
      }
    }
    HandCount(add(countsForFirstRounds, _ == 1), add(countsForAllRounds, _ => true))
  }
}

object HandCount {

  /**
   * Create an empty [[HandCount]]
   */
  def apply() : HandCount = new HandCount(Map.empty[Hand, Int], Map.empty[Hand, Int])
}