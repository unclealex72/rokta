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

import argonaut.DecodeResult
import argonaut._, Argonaut._, DecodeResult._
import Hand._

/**
 * A game that can be uploaded from a browser and then persisted.
 * @author alex
 *
 */
case class UploadableGame(
  /**
   * The name of the person who instigiated the game.
   */
  instigator: Player,
  /**
   * The original participants of the game.
   */
  participants: List[Player],
  /**
   * A sequence of all the rounds played as a map of rounds keyed by player name.
   */
  rounds: List[Map[Player, Hand]])
  
object UploadableGame {
  
  def UploadableGameDecodeJson(playerFactory: String => Player) = {
    def create(instigator: String, participants: List[String], rounds: List[Map[String, Hand]]): UploadableGame = {
      val playerRounds = rounds map { round => round.foldLeft(Map.empty[Player, Hand]){ 
        (round, play) => round + (playerFactory(play._1) -> play._2) }
      }
      UploadableGame(playerFactory(instigator), participants.map(playerFactory), playerRounds)
    }
    jdecode3L(create)("instigator", "participants", "rounds")
  }
  
}