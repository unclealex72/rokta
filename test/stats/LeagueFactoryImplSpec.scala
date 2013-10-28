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
import org.specs2.mock.Mockito
import com.escalatesoft.subcut.inject.BindingModule
import com.escalatesoft.subcut.inject.NewBindingModule
import model.Colour
import scala.collection.SortedSet
import stats.LeagueFactoryImplSpec._
import org.joda.time.DateTime
import scala.collection.SortedMap
import model.JodaDateTime._
import org.specs2.mock._
import dates.IsoChronology
import dates.DaysAndTimes

/**
 * @author alex
 *
 */
class LeagueFactoryImplSpec extends Specification with Mockito with DaysAndTimes with IsoChronology {

  /**
   * Give tests access to the concrete [[LeagueFactoryImpl]] and mocked [[GapCalculator]]
   */
  def mocked[B](block: LeagueFactoryImpl => GapCalculator => B) = {
    val gapCalculator: GapCalculator = mock[GapCalculator]
    implicit val module = new NewBindingModule({ module =>
      import module._
      bind[GapCalculator] toSingle gapCalculator
    })
    val leagueFactory = new LeagueFactoryImpl()
    block(leagueFactory)(gapCalculator)
  }

  "A non-current league" should {
    "not be decorated" in mocked { (leagueFactory: LeagueFactoryImpl) =>
      (gapCalculator: GapCalculator) =>
        def snapshots =
          on(
            October(15, 2013) at (11 oclock),
            "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12),
            "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
            "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12),
            "John".wins(2).playingRounds(12).loses(2).playingRounds(12)) ++
            on(
              October(15, 2013) at (15 oclock),
              "John".wins(5).playingRounds(12).loses(1).playingRounds(12),
              "Freddie".wins(4).playingRounds(12).loses(2).playingRounds(12),
              "Roger".wins(3).playingRounds(12).loses(3).playingRounds(12),
              "Brian".wins(2).playingRounds(12).loses(2).playingRounds(12))

        val league = leagueFactory.apply(snapshots, None, None)
        league must contain { (leagueRow: LeagueRow) =>
          (leagueRow.currentlyPlaying must beFalse) and
            (leagueRow.movement must beNone) and
            (leagueRow.exempt must beFalse) and
            (leagueRow.gap must beNone)
        }.foreach
    }
  }

  "A league generated from a single snapshot" should {
    "not be decorated" in mocked { (leagueFactory: LeagueFactoryImpl) =>
      (gapCalculator: GapCalculator) =>
        def snapshots =
          on(
            October(15, 2013) at (11 oclock),
            "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12),
            "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
            "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12),
            "John".wins(2).playingRounds(12).loses(2).playingRounds(12))

        val league = leagueFactory.apply(snapshots, None, None)
        league must contain { (leagueRow: LeagueRow) =>
          (leagueRow.currentlyPlaying must beFalse) and
            (leagueRow.movement must beNone) and
            (leagueRow.exempt must beFalse) and
            (leagueRow.gap must beNone)
        }.foreach
    }
  }

  "Decorating a league with who is currently playing and who is currently not" should {
    "mark playing players' results as current" in mocked {
      (leagueFactory: LeagueFactoryImpl) =>
        (gapCalculator: GapCalculator) =>
          val nonDecoratedLeague = SortedSet[LeagueRow](
            "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12),
            "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
            "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12),
            "John".wins(2).playingRounds(12).loses(2).playingRounds(12))

          val decoratedLeague = SortedSet[LeagueRow](
            "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true),
            "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
            "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true),
            "John".wins(2).playingRounds(12).loses(2).playingRounds(12))

          leagueFactory.decorateWithCurrent(Set("Brian", "Freddie"))(nonDecoratedLeague) must be equalTo (decoratedLeague)
    }
  }

  "Decorating a league with who is currently exempt" should {
    "mark exempt players' results as exempt" in mocked {
      (leagueFactory: LeagueFactoryImpl) =>
        (gapCalculator: GapCalculator) =>
          val nonDecoratedLeague = SortedSet[LeagueRow](
            "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true),
            "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
            "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true),
            "John".wins(2).playingRounds(12).loses(2).playingRounds(12))

          val decoratedLeague = SortedSet[LeagueRow](
            "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true).withExempt(true),
            "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
            "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true),
            "John".wins(2).playingRounds(12).loses(2).playingRounds(12))

          leagueFactory.decorateWithExemption(Some("Brian"))(nonDecoratedLeague) must be equalTo (decoratedLeague)
    }
  }

  "Decorating a league with the gaps between Set" should {
    "compare the bottom three Set to the Set above them" in mocked {
      (leagueFactory: LeagueFactoryImpl) =>
        (gapCalculator: GapCalculator) =>
          val brian: LeagueRow = "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true)
          val roger: LeagueRow = "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12).withCurrentlyPlaying(true)
          val freddie: LeagueRow = "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12)
          val john: LeagueRow = "John".wins(2).playingRounds(12).loses(2).playingRounds(12)
          gapCalculator.calculateGap(brian, roger) returns (Some(1))
          gapCalculator.calculateGap(roger, freddie) returns (Some(2))
          gapCalculator.calculateGap(freddie, john) returns (Some(3))
          val decoratedLeague =
            leagueFactory.decorateWithGap(Set("Brian", "Roger", "Freddie", "John"))(SortedSet(brian, roger, freddie, john))
          decoratedLeague must contain(brian, roger.withGap(1), freddie.withGap(2), john.withGap(3)).inOrder
    }
  }

  "Generating a full league" should {
    "correctly decorate each row" in mocked {
      (leagueFactory: LeagueFactoryImpl) =>
        (gapCalculator: GapCalculator) =>
          def snapshots =
            on(
              October(15, 2013) at (9 oclock),
              "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12),
              "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
              "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12),
              "John".wins(1).playingRounds(12).loses(2).playingRounds(12)) ++
            on(
              October(15, 2013) at (11 oclock),
              "Brian".wins(5).playingRounds(12).loses(1).playingRounds(12),
              "Roger".wins(4).playingRounds(12).loses(2).playingRounds(12),
              "Freddie".wins(3).playingRounds(12).loses(3).playingRounds(12),
              "John".wins(2).playingRounds(12).loses(2).playingRounds(12)) ++
              on(
                October(15, 2013) at (15 oclock),
                "John".wins(5).playingRounds(12).loses(1).playingRounds(12),
                "Freddie".wins(4).playingRounds(12).loses(2).playingRounds(12),
                "Roger".wins(3).playingRounds(12).loses(3).playingRounds(12),
                "Brian".wins(2).playingRounds(12).loses(2).playingRounds(12))
          gapCalculator.calculateGap(any[LeagueRow], any[LeagueRow]) returns Some(2)
          def league = leagueFactory.apply(snapshots, Some(Set("Brian", "Roger")), Some("Freddie"))
          league must contain(
            "John".wins(5).playingRounds(12).loses(1).playingRounds(12).withMovement(-3),
            "Freddie".wins(4).playingRounds(12).loses(2).playingRounds(12).withMovement(-1).withExempt(true).withGap(2),
            "Roger".wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true).withMovement(1).withGap(2),
            "Brian".wins(2).playingRounds(12).loses(2).playingRounds(12).withCurrentlyPlaying(true).withMovement(3).withGap(2)).inOrder
    }
  }
}

object LeagueFactoryImplSpec {
  // DSL for snapshot and league row creation

  implicit class StringNameImplicits(playerName: String) {
    def wins(gamesWon: Int) = Wins(playerName, gamesWon)
  }

  case class Wins(playerName: String, gamesWon: Int) {
    def playingRounds(roundsPlayedInWinningGames: Int) =
      WinsRounds(playerName, gamesWon, roundsPlayedInWinningGames)
  }

  case class WinsRounds(playerName: String, gamesWon: Int, roundsPlayedInWinningGames: Int) {
    def loses(gamesLost: Int) = Loses(playerName, gamesWon, roundsPlayedInWinningGames, gamesLost)
  }

  case class Loses(playerName: String, gamesWon: Int, roundsPlayedInWinningGames: Int, gamesLost: Int) {
    def playingRounds(roundsPlayedInLosingGames: Int) =
      LeagueRowOrSnapshot(playerName, gamesWon, roundsPlayedInWinningGames, gamesLost, roundsPlayedInLosingGames)
  }

  case class LeagueRowOrSnapshot(
    playerName: String, gamesWon: Int, roundsPlayedInWinningGames: Int, gamesLost: Int, roundsPlayedInLosingGames: Int) {

    def asLeagueRow = LeagueRow(playerName, gamesWon, gamesLost, roundsPlayedInWinningGames, roundsPlayedInLosingGames)
    def asSnapshot =
      playerName ->
        Snapshot(gamesWon, gamesLost, roundsPlayedInWinningGames, roundsPlayedInLosingGames)
  }

  implicit def LeagueRowOrSnapshotToLeagueRow(leagueRowOrSnapshot: LeagueRowOrSnapshot): LeagueRow =
    leagueRowOrSnapshot.asLeagueRow

  implicit def LeagueRowOrSnapshotToSnapshot(leagueRowOrSnapshot: LeagueRowOrSnapshot): Pair[String, Snapshot] =
    leagueRowOrSnapshot.asSnapshot

  def on(dateTime: DateTime, snapshots: Pair[String, Snapshot]*): SortedMap[DateTime, Map[String, Snapshot]] =
    SortedMap(dateTime -> snapshots.foldLeft(Map.empty[String, Snapshot])(_ + _))
}