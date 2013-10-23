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

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * A row in a Rokta league.
 */
case class LeagueRow(
  /**
   * The name of the player for whom this row is for.
   */
  playerName: String,
  /**
   * The number of games the player has won.
   */
  gamesWon: Int,
  /**
   * The number of games the player has lost.
   */
  gamesLost: Int,
  /**
   * The number of rounds the player has played in games they've won.
   */
  roundsDuringWinningGames: Int,
  /**
   * The number of rounds the player has played in games they've won.
   */
  roundsDuringLosingGames: Int,
  /**
   * The number of places the player has dropped since the last league 
   * (e.g. 2 means the player has dropped from 1st to 3rd). This will be <code>None</code> if the player did not
   * partake in the previous league.
   */
  movement: Option[Int], 
  /**
   * True if the player is currently playing today, false otherwise.
   */
  currentlyPlaying: Boolean,
  /**
   * True if the player is currently exempt, false otherwise.
   */
  exempt: Boolean,
  /**
   * The number of games needed to catch up with the player above.
   */
  gap: Option[Int]) {

  /**
   * Create a new league row based on this one but with a new [[#movement]] property
   */
  def withMovement(newMovement: Int) = 
    LeagueRow(
      playerName, gamesWon, gamesLost, roundsDuringWinningGames, 
      roundsDuringLosingGames, Some(newMovement), currentlyPlaying, exempt, gap)

  /**
   * Create a new league row based on this one but with a new [[#currentlyPlaying]] property
   */
  def withCurrentlyPlaying(newCurrentlyPlaying: Boolean) = 
    LeagueRow(
      playerName, gamesWon, gamesLost, roundsDuringWinningGames, 
      roundsDuringLosingGames, movement, newCurrentlyPlaying, exempt, gap)

  /**
   * Create a new league row based on this one but with a new [[#exempt]] property
   */
  def withExempt(newExempt: Boolean) = 
    LeagueRow(
      playerName, gamesWon, gamesLost, roundsDuringWinningGames, 
      roundsDuringLosingGames, movement, currentlyPlaying, newExempt, gap)

      /**
   * Create a new league row based on this one but with a new [[#gap]] property
   */
  def withGap(newGap: Int) = 
    LeagueRow(
      playerName, gamesWon, gamesLost, roundsDuringWinningGames, 
      roundsDuringLosingGames, movement, currentlyPlaying, exempt, Some(newGap))
      
  /**
   * The total number of games played.
   */
  @JsonIgnore
  lazy val gamesPlayed: Int = gamesWon + gamesLost
}
  
object LeagueRow {
  
  /**
   * Create a new [[LeagueRow]] without no gap information and with every player not moving.
   */
  def apply(
  playerName: String,
  gamesWon: Int,
  gamesLost: Int,
  roundsDuringWinningGames: Int,
  roundsDuringLosingGames: Int): LeagueRow = LeagueRow(
      playerName, gamesWon, gamesLost, roundsDuringWinningGames, roundsDuringLosingGames, None, false, false, None)

  /**
   * A class that represents fractions.
   */
  case class Fraction(val numerator: Int, val denominator: Int)
  /**
   * A comparator to compare fractions using multiplication instead of division.
   */
  implicit object FractionOrdering extends Ordering[Fraction] {
    def compare(f1: Fraction, f2: Fraction): Int = {
      f1.numerator * f2.denominator - f2.numerator * f1.denominator
    }
  }
  
  implicit class IntImplicit(numerator: Int) {
    def over(denominator: Int) = Fraction(numerator, denominator)
  }
  

  /**
   * Define the ordering on [[LeagueRow]]s. The ordering is defined using the following steps:
   * <ol>
   *  <li>rows with lower loss rates are less than games with higher loss rates,</li>
   *  <li>rows where the player tends to win quickly are less than rows where the player tends to win slowly,</li>
   *  <li>rows where the player tends to lose slowly are less than rows where the player tends to lose quickly,</li>
   *  <li>rows with more games played are less than rows with less games played,</li>
   *  <li>rows are then compared by player name.
   * </ol>
   * 
   * Note that <em>less than</em> means earlier in the league and thus being less than is better.
   */
  implicit val leagueRowOrdering = {
    val lossOrdering = (lr: LeagueRow) => lr.gamesLost over lr.gamesPlayed
    val winningRoundOrdering = (lr: LeagueRow) => lr.roundsDuringWinningGames over lr.gamesWon
    val losingRoundOrdering = (lr: LeagueRow) => -lr.roundsDuringLosingGames over lr.gamesLost
    val gamesPlayedOrdering = (lr: LeagueRow) => -lr.gamesPlayed
    val playerNameOrdering = (lr: LeagueRow) => lr.playerName
    Ordering.by{ (lr: LeagueRow) => 
      (lossOrdering(lr), winningRoundOrdering(lr), losingRoundOrdering(lr), gamesPlayedOrdering(lr), playerNameOrdering(lr))
    }
  }
}