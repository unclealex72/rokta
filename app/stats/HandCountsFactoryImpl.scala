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

import model.Game
import model.Hand

/**
 * The default implementation of [[HandCountsFactory]]
 * @author alex
 *
 */
class HandCountsFactoryImpl extends HandCountsFactory {

  def apply(games: Iterable[Game]): Map[String, HandCount] = {
    games.foldLeft(Map.empty[String, HandCount])(countHandsForGame)
  }
  
  def countHandsForGame = { (handCounts: Map[String, HandCount], game: Game) => 
    game.rounds.foldLeft(handCounts)(countHandsForRound)
  }
  
  def countHandsForRound = { (handCounts: Map[String, HandCount], round: Pair[Int, Map[String, Hand]]) => 
    val roundNumber = round._1
    round._2.foldLeft(handCounts)(countHandsForPlay(roundNumber))
  }
  
  def countHandsForPlay = { roundNumber: Int => (handCounts: Map[String, HandCount], play: Pair[String, Hand]) =>
    val playerName = play._1
    val hand = play._2
    val handCountForPlayer = handCounts.getOrElse(playerName, HandCount())
    handCounts + (playerName -> (handCountForPlayer + (roundNumber, hand)))
  }
}