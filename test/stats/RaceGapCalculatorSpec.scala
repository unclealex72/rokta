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

/**
 * @author alex
 *
 */
class RaceGapCalculatorSpec extends Specification with NonPersistedGameDsl {

  val gapCalculator = new RaceGapCalculator

  // Tests taken to represent the league from 15/10/2013
  
  "A player who has won all their games and isn't currently playing" should {
    "be uncatchable" in {
      gapCalculator.calculateGap(
        played(1).lost(0).notPlaying, played(2).lost(1)) must beNone
    }
  }
  
  "A player who has never won a game and isn't currently playing" should {
    "not be able to catch anybody" in {
      gapCalculator.calculateGap(
        played(2).lost(1), played(1).lost(1).notPlaying) must beNone
    }
  }
  
  "The gap between a player with 313 games and 82 losses and a player with 13 games and 4 losses" should {
    "be 3" in {
      gapCalculator.calculateGap(
        played(313).lost(82).playing.notExempt,
        played(13).lost(4).playing.notExempt) must beSome(4)
    }
  }

  "The gap between a player with 13 games and 4 losses and a player with 336 games and 106 losses who is currently not playing" should {
    "be 1" in {
      gapCalculator.calculateGap(
          played(13).lost(4), played(336).lost(106).notPlaying) must beSome(2)
    }
  }

  "The gap between a player with 336 games and 106 losses who is currently not playing and a player with 367 games and 118 losses" should {
    "be 8" in {
      gapCalculator.calculateGap(
          played(336).lost(106).notPlaying, played(367).lost(118)) must beSome(9)
    }
  }

  "The gap between a player with 367 games and 118 losses and a player with 63 games and 21 losses who is currently not playing" should {
    "be 13" in {
      gapCalculator.calculateGap(
        played(367).lost(118), played(63).lost(21).notPlaying) must beSome(14)
    }
  }
  
  "The gap between a player with 63 games and 21 losses who is currently not playing and a player with 267 games and 90 losses" should {
    "be 4" in {
      gapCalculator.calculateGap(played(63).lost(21).notPlaying, played(267).lost(90)) must beSome(5)
    }
  }

  "The gap between a player with 267 games and 90 losses and a player with 238 games and 83 losses who is exempt" should {
    "be 5" in {
      gapCalculator.calculateGap(played(267).lost(90), played(238).lost(83).playing.exempt) must beSome(6)
    }
  }

  /**
   * A DSL for creating League Rows.
   */
  implicit def playingNotExempt(lost: Lost): LeagueRow = lost.playing.notExempt
  
  def played(gamesPlayed: Int) = Played(gamesPlayed)
  
  case class Played(gamesPlayed: Int) {
    def lost(gamesLost: Int) = Lost(gamesPlayed, gamesLost)
  }
  
  case class Lost(gamesPlayed: Int, gamesLost: Int) {
    def playing = Playing(gamesPlayed, gamesLost, true)
    def notPlaying = Playing(gamesPlayed, gamesLost, false).notExempt
  }
  
  case class Playing(gamesPlayed: Int, gamesLost: Int, playing: Boolean) {
    def exempt = createLeagueRow(true)
    def notExempt = createLeagueRow(false)
    
    def createLeagueRow(exempt: Boolean) = 
      LeagueRow(freddie, gamesPlayed - gamesLost, gamesLost, 0, 0, None, playing, true, None)
  }
}