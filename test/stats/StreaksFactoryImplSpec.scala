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
import org.joda.time.DateTime
import model.Game
import model.Colour
import stats.StreaksFactoryImplSpec._
import scala.collection.SortedSet
import dates.IsoChronology
import dates.DaysAndTimes
import model.Hand
import scala.collection.SortedMap
import model.Hand._
/**
 * @author alex
 *
 */
class StreaksFactoryImplSpec extends Specification with DaysAndTimes with IsoChronology {

  val streaksFactory: StreaksFactoryImpl = new StreaksFactoryImpl
  val freddie = "Freddie"
  val roger = "Roger"
  val brian = "Brian"
  val john = "John"
    
  "A losing player" should {
    "end their winning streak" in {
      val game = September(5, 2013) at (11 oclock) lostBy(freddie) wonBy(john, roger, brian)
      val winningStreak = 
        Streak(freddie, September(5, 2013) at (9 oclock)) extendTo(September(5, 2013) at (10 oclock))
      val winningStreaks: Map[String, Streak] = Map(freddie -> winningStreak)
      val streaksStatus = StreaksStatus(winningStreaks, Map.empty, Streaks())
      val newStreaksStatus = streaksFactory.updateStreaksStatusPerParticipant(game)(streaksStatus, freddie)
      newStreaksStatus.winningStreaks must beEmpty
      newStreaksStatus.losingStreaks must contain(
          exactly(freddie -> Streak(freddie, September(5, 2013) at (11 oclock))))
      newStreaksStatus.streaks.winningStreaks must contain(exactly(winningStreak))
    }
    "extend their losing streak" in {
      val game = September(5, 2013) at (11 oclock) lostBy(freddie) wonBy(john, roger, brian)
      val losingStreak = 
        Streak(freddie, September(5, 2013) at (9 oclock)) extendTo(September(5, 2013) at (10 oclock))
      val losingStreaks: Map[String, Streak] = Map(freddie -> losingStreak)
      val streaksStatus = StreaksStatus(Map.empty, losingStreaks, Streaks())
      val newStreaksStatus = streaksFactory.updateStreaksStatusPerParticipant(game)(streaksStatus, freddie)
      newStreaksStatus.losingStreaks must contain(
          exactly(freddie -> losingStreak.extendTo(September(5, 2013) at (11 oclock))))
      newStreaksStatus.streaks.winningStreaks must beEmpty
    }
    "nullify a single won game" in {
      val game = September(5, 2013) at (11 oclock) lostBy(freddie) wonBy(john, roger, brian)
      val winningStreak = 
        Streak(freddie, September(5, 2013) at (9 oclock))
      val winningStreaks: Map[String, Streak] = Map(freddie -> winningStreak)
      val streaksStatus = StreaksStatus(winningStreaks, Map.empty, Streaks())
      val newStreaksStatus = streaksFactory.updateStreaksStatusPerParticipant(game)(streaksStatus, freddie)
      newStreaksStatus.winningStreaks must beEmpty
      newStreaksStatus.losingStreaks must contain(
          exactly(freddie -> Streak(freddie, September(5, 2013) at (11 oclock))))
      newStreaksStatus.streaks.winningStreaks must beEmpty
    }
  }
    
  "A winning player" should {
    "end their losing streak" in {
      val game = September(5, 2013) at (11 oclock) lostBy(freddie) wonBy(john, roger, brian)
      val losingStreak = 
        Streak(roger, September(5, 2013) at (9 oclock)) extendTo(September(5, 2013) at (10 oclock))
      val losingStreaks: Map[String, Streak] = Map(roger -> losingStreak)
      val streaksStatus = StreaksStatus(Map.empty, losingStreaks, Streaks())
      val newStreaksStatus = streaksFactory.updateStreaksStatusPerParticipant(game)(streaksStatus, roger)
      newStreaksStatus.losingStreaks must beEmpty
      newStreaksStatus.winningStreaks must contain(
          exactly(roger -> Streak(roger, September(5, 2013) at (11 oclock))))
      newStreaksStatus.streaks.losingStreaks must contain(exactly(losingStreak))
    }
    "extend their winning streak" in {
      val game = September(5, 2013) at (11 oclock) lostBy(freddie) wonBy(john, roger, brian)
      val winningStreak = 
        Streak(roger, September(5, 2013) at (9 oclock)) extendTo(September(5, 2013) at (10 oclock))
      val winningStreaks: Map[String, Streak] = Map(roger -> winningStreak)
      val streaksStatus = StreaksStatus(winningStreaks, Map.empty, Streaks())
      val newStreaksStatus = streaksFactory.updateStreaksStatusPerParticipant(game)(streaksStatus, roger)
      newStreaksStatus.winningStreaks must contain(
          exactly(roger -> winningStreak.extendTo(September(5, 2013) at (11 oclock))))
      newStreaksStatus.streaks.winningStreaks must beEmpty
    }
    "nullify a single lost game" in {
      val game = September(5, 2013) at (11 oclock) lostBy(freddie) wonBy(john, roger, brian)
      val losingStreak = 
        Streak(roger, September(5, 2013) at (9 oclock))
      val losingStreaks: Map[String, Streak] = Map(roger -> losingStreak)
      val streaksStatus = StreaksStatus(Map.empty, losingStreaks, Streaks())
      val newStreaksStatus = streaksFactory.updateStreaksStatusPerParticipant(game)(streaksStatus, roger)
      newStreaksStatus.losingStreaks must beEmpty
      newStreaksStatus.winningStreaks must contain(
          exactly(roger -> Streak(roger, September(5, 2013) at (11 oclock))))
      newStreaksStatus.streaks.losingStreaks must beEmpty
    }
  }
  
  "Adding the streaks that have yet to be terminated by the final game" should {
    val incompleteWinningStreaks = 
      (freddie streak(September(5, 2013) at (10 oclock), September(5, 2013) at (11 oclock))) ++
      (roger streak(September(5, 2013) at (11 oclock)))
    val incompleteLosingStreaks =
      (brian streak(September(5, 2013) at (9 oclock), September(5, 2013) at (10 oclock))) ++
      (john streak(September(5, 2013) at (10 oclock)))
    val completedWinningStreaks = SortedSet(
      Streak(freddie, September(4, 2013) at (10 oclock)).extendTo(September(4, 2013) at (11 oclock)))
    val completedLosingStreaks = SortedSet(
      Streak(brian, September(4, 2013) at (11 oclock)).extendTo(September(4, 2013) at (12 oclock)))
    val streaksStatus = StreaksStatus(
        incompleteWinningStreaks, incompleteLosingStreaks, Streaks(completedWinningStreaks, completedLosingStreaks))
    "return all streaks making the incomplete streaks current if requested" in {
      val streaks = streaksFactory.addRemainingStreaks(true, streaksStatus)
      streaks.winningStreaks must contain(exactly(
        Streak(freddie, September(5, 2013) at (10 oclock)).extendTo(September(5, 2013) at (11 oclock)).makeCurrent,
        Streak(freddie, September(4, 2013) at (10 oclock)).extendTo(September(4, 2013) at (11 oclock))
      )).inOrder
      streaks.losingStreaks must contain(exactly(
        Streak(brian, September(5, 2013) at (9 oclock)).extendTo(September(5, 2013) at (10 oclock)).makeCurrent,
        Streak(brian, September(4, 2013) at (11 oclock)).extendTo(September(4, 2013) at (12 oclock))
      )).inOrder
    }
    "return all streaks making the incomplete streaks non-current if requested" in {
      val streaks = streaksFactory.addRemainingStreaks(false, streaksStatus)
      streaks.winningStreaks must contain(exactly(
        Streak(freddie, September(5, 2013) at (10 oclock)).extendTo(September(5, 2013) at (11 oclock)),
        Streak(freddie, September(4, 2013) at (10 oclock)).extendTo(September(4, 2013) at (11 oclock))
      )).inOrder
      streaks.losingStreaks must contain(exactly(
        Streak(brian, September(5, 2013) at (9 oclock)).extendTo(September(5, 2013) at (10 oclock)),
        Streak(brian, September(4, 2013) at (11 oclock)).extendTo(September(4, 2013) at (12 oclock))
      )).inOrder
    }
  }
  
  "Calculating streaks for a set of games" should {
    val tenoclock = September(5, 2013) at (10 oclock)
    val elevenoclock = September(5, 2013) at (11 oclock)
    val twelveoclock = September(5, 2013) at midday
    val oneoclock = September(5, 2013) at (1 oclock).pm
    val twooclock = September(5, 2013) at (2 oclock).pm
    val threeoclock = September(5, 2013) at (3 oclock).pm
    val games: SortedSet[Game] = 
      (tenoclock lostBy(freddie) wonBy(john, roger, brian)) +
      (elevenoclock lostBy(roger) wonBy(john)) +
      (twelveoclock lostBy(freddie) wonBy(john, brian)) +
      (oneoclock lostBy(brian) wonBy(john, roger)) +
      (twooclock lostBy(roger) wonBy(john, freddie)) +
      (threeoclock lostBy(brian) wonBy(john, freddie, roger))
      val expectedWinningStreaks: SortedSet[Streak] = 
        Streak(john, tenoclock, elevenoclock, twelveoclock, oneoclock, twooclock, threeoclock) +
        Streak(freddie, twooclock, threeoclock).makeCurrent +
        Streak(brian, tenoclock, twelveoclock)
      val expectedLosingStreaks: SortedSet[Streak] =
        Streak(brian, oneoclock, threeoclock).makeCurrent +
        Streak(freddie, tenoclock, twelveoclock)
      val streaks = streaksFactory.apply(games, true)
    "amalgamate all the winning streak information into one object" in {
      streaks.winningStreaks must be equalTo(expectedWinningStreaks)
    }
    "amalgamate all the losing streak information into one object" in {
      streaks.losingStreaks must be equalTo(expectedLosingStreaks)
    }
  }
}

object StreaksFactoryImplSpec {
  
  /**
   * A DSL to create games that are lost by a player.
   */
  implicit class DateTimeImplicits(datePlayed: DateTime) {
    def lostBy(loser: String) = GameBuilder(datePlayed, loser)
  }

  implicit class StringImplicits(player: String) {
    def streak(dateTime: DateTime, dateTimes: DateTime*): Map[String, Streak] = {
      Map(player -> Streak(player, dateTime, dateTimes: _*))
    }
  }

  implicit class AnyImplicits[A](a: A) {
    def +(next: A)(implicit ord: Ordering[A]) = SortedSet(a, next)
  }
    
  case class GameBuilder(dateGamePlayed: DateTime, _loser: String) {
    def wonBy(winners: String*): Game = new Game() {
      val instigator = "Freddie"
      val datePlayed = dateGamePlayed
      val round: Map[String, Hand] = 
        winners.foldLeft(Map(_loser -> ROCK.asInstanceOf[Hand]))((plays, player) => plays + (player -> PAPER) )
      val rounds = SortedMap(1 -> round)
    }
  }
}