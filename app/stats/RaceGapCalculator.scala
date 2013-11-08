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

/**
 * A [[GapCalculator]] that simulates game results to see how many games it will take for one person to catch up
 * with the other.
 * @author alex
 *
 */
class RaceGapCalculator extends GapCalculator {

  def calculateGap(topLeagueRow: LeagueRow, bottomLeagueRow: LeagueRow) = {
    val playingTop = topLeagueRow.currentlyPlaying
    val playingBottom = bottomLeagueRow.currentlyPlaying
    if (!playingTop && !playingBottom) {
      // Neither player playing is the same as both players playing.
      calculateGap(topLeagueRow.withCurrentlyPlaying(true), bottomLeagueRow.withCurrentlyPlaying(true))
    }
    else {
      val playedTop = topLeagueRow.gamesPlayed
      val lostTop = topLeagueRow.gamesLost
      val exemptTop = topLeagueRow.exempt
      
      val playedBottom = bottomLeagueRow.gamesPlayed
      val lostBottom = bottomLeagueRow.gamesLost
      val exemptBottom = bottomLeagueRow.exempt
      // You can't beat the top player if they're not playing and have never lost
      if (!playingTop && lostTop == 0) {
        None
      }
      // Likewise, a player at the bottom who is not playing and has never won can never catch up.
      else if (!playingBottom && playedBottom == lostBottom) {
        None
      }
      else {
        race(1, playingTop, playedTop, lostTop, exemptTop, playingBottom, playedBottom, lostBottom, exemptBottom)
      }
    }
  }

  def race(
    currentGap: Int,
    playingTop: Boolean, playedTop: Int, lostTop: Int, exemptTop: Boolean, 
    playingBottom: Boolean, playedBottom: Int, lostBottom: Int, exemptBottom: Boolean): Option[Int] = {
      // Make the top player lose if they are currently playing and not exempt.
      val nextPlayedTop = if (playingTop && !exemptTop) playedTop + 1 else playedTop
      val nextLostTop = if (playingTop && !exemptTop) lostTop + 1 else lostTop
      val nextExemptTop = if (playingTop) !exemptTop else false
      
      // Let the bottom player win if they are currently playing.
      val nextPlayedBottom = if (playingBottom && !exemptBottom) playedBottom + 1 else playedBottom
      
    if (lostBottom * nextPlayedTop < nextLostTop * nextPlayedBottom) {
      // If the second player has overtaken the first then we're done.
      Some(currentGap)
    }
    else {
      // Progress the race by one step.
      race(
        currentGap + 1, 
        playingTop, nextPlayedTop, nextLostTop, nextExemptTop, 
        playingBottom, nextPlayedBottom, lostBottom, false)
    }
  }
}