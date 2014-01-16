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
import model.Player
import model.NonPersistedGameDsl

/**
 * @author alex
 *
 */
class LeagueFactoryImplSpec extends Specification with Mockito with DaysAndTimes with IsoChronology with NonPersistedGameDsl {

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
            brian.wins(5).playingRounds(12).loses(1).playingRounds(12),
            roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
            freddie.wins(3).playingRounds(12).loses(3).playingRounds(12),
            john.wins(2).playingRounds(12).loses(2).playingRounds(12)) ++
            on(
              October(15, 2013) at (15 oclock),
              john.wins(5).playingRounds(12).loses(1).playingRounds(12),
              freddie.wins(4).playingRounds(12).loses(2).playingRounds(12),
              roger.wins(3).playingRounds(12).loses(3).playingRounds(12),
              brian.wins(2).playingRounds(12).loses(2).playingRounds(12))

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
            brian.wins(5).playingRounds(12).loses(1).playingRounds(12),
            roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
            freddie.wins(3).playingRounds(12).loses(3).playingRounds(12),
            john.wins(2).playingRounds(12).loses(2).playingRounds(12))

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
            brian.wins(5).playingRounds(12).loses(1).playingRounds(12),
            roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
            freddie.wins(3).playingRounds(12).loses(3).playingRounds(12),
            john.wins(2).playingRounds(12).loses(2).playingRounds(12))

          val decoratedLeague = SortedSet[LeagueRow](
            brian.wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true),
            roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
            freddie.wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true),
            john.wins(2).playingRounds(12).loses(2).playingRounds(12))

          leagueFactory.decorateWithCurrent(Set(brian, freddie))(nonDecoratedLeague) must be equalTo (decoratedLeague)
    }
  }

  "Decorating a league with who is currently exempt" should {
    "mark exempt players' results as exempt" in mocked {
      (leagueFactory: LeagueFactoryImpl) =>
        (gapCalculator: GapCalculator) =>
          val nonDecoratedLeague = SortedSet[LeagueRow](
            brian.wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true),
            roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
            freddie.wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true),
            john.wins(2).playingRounds(12).loses(2).playingRounds(12))

          val decoratedLeague = SortedSet[LeagueRow](
            brian.wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true).withExempt(true),
            roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
            freddie.wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true),
            john.wins(2).playingRounds(12).loses(2).playingRounds(12))

          leagueFactory.decorateWithExemption(Some(brian))(nonDecoratedLeague) must be equalTo (decoratedLeague)
    }
  }

  "Decorating a league with the gaps between Set" should {
    "compare the bottom three Set to the Set above them" in mocked {
      (leagueFactory: LeagueFactoryImpl) =>
        (gapCalculator: GapCalculator) =>
          val brianL: LeagueRow = brian.wins(5).playingRounds(12).loses(1).playingRounds(12).withCurrentlyPlaying(true)
          val rogerL: LeagueRow = roger.wins(4).playingRounds(12).loses(2).playingRounds(12).withCurrentlyPlaying(true)
          val freddieL: LeagueRow = freddie.wins(3).playingRounds(12).loses(3).playingRounds(12)
          val johnL: LeagueRow = john.wins(2).playingRounds(12).loses(2).playingRounds(12)
          gapCalculator.calculateGap(brianL, rogerL) returns (Some(1))
          gapCalculator.calculateGap(rogerL, freddieL) returns (Some(2))
          gapCalculator.calculateGap(freddieL, johnL) returns (Some(3))
          val decoratedLeague =
            leagueFactory.decorateWithGap(Set(brian, roger, freddie, john))(SortedSet(brianL, rogerL, freddieL, johnL))
          decoratedLeague must contain(brianL, rogerL.withGap(1), freddieL.withGap(2), johnL.withGap(3)).inOrder
    }
  }

  "Generating a full league" should {
    "correctly decorate each row" in mocked {
      (leagueFactory: LeagueFactoryImpl) =>
        (gapCalculator: GapCalculator) =>
          def snapshots =
            on(
              October(15, 2013) at (9 oclock),
              brian.wins(5).playingRounds(12).loses(1).playingRounds(12),
              roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
              freddie.wins(3).playingRounds(12).loses(3).playingRounds(12),
              john.wins(1).playingRounds(12).loses(2).playingRounds(12)) ++
            on(
              October(15, 2013) at (11 oclock),
              brian.wins(5).playingRounds(12).loses(1).playingRounds(12),
              roger.wins(4).playingRounds(12).loses(2).playingRounds(12),
              freddie.wins(3).playingRounds(12).loses(3).playingRounds(12),
              john.wins(2).playingRounds(12).loses(2).playingRounds(12)) ++
              on(
                October(15, 2013) at (15 oclock),
                john.wins(5).playingRounds(12).loses(1).playingRounds(12),
                freddie.wins(4).playingRounds(12).loses(2).playingRounds(12),
                roger.wins(3).playingRounds(12).loses(3).playingRounds(12),
                brian.wins(2).playingRounds(12).loses(2).playingRounds(12))
          gapCalculator.calculateGap(any[LeagueRow], any[LeagueRow]) returns Some(2)
          def league = leagueFactory.apply(snapshots, Some(Set(brian, roger)), Some(freddie))
          league must contain(
            john.wins(5).playingRounds(12).loses(1).playingRounds(12).withMovement(-3),
            freddie.wins(4).playingRounds(12).loses(2).playingRounds(12).withMovement(-1).withExempt(true).withGap(2),
            roger.wins(3).playingRounds(12).loses(3).playingRounds(12).withCurrentlyPlaying(true).withMovement(1).withGap(2),
            brian.wins(2).playingRounds(12).loses(2).playingRounds(12).withCurrentlyPlaying(true).withMovement(3).withGap(2)).inOrder
    }
  }
}

object LeagueFactoryImplSpec {
  // DSL for snapshot and league row creation

  implicit class LeaguePlayerImplicits(player: Player) {
    def wins(gamesWon: Int) = Wins(player, gamesWon)
  }

  case class Wins(player: Player, gamesWon: Int) {
    def playingRounds(roundsPlayedInWinningGames: Int) =
      WinsRounds(player, gamesWon, roundsPlayedInWinningGames)
  }

  case class WinsRounds(player: Player, gamesWon: Int, roundsPlayedInWinningGames: Int) {
    def loses(gamesLost: Int) = Loses(player, gamesWon, roundsPlayedInWinningGames, gamesLost)
  }

  case class Loses(player: Player, gamesWon: Int, roundsPlayedInWinningGames: Int, gamesLost: Int) {
    def playingRounds(roundsPlayedInLosingGames: Int) =
      LeagueRowOrSnapshot(player, gamesWon, roundsPlayedInWinningGames, gamesLost, roundsPlayedInLosingGames)
  }

  case class LeagueRowOrSnapshot(
    player: Player, gamesWon: Int, roundsPlayedInWinningGames: Int, gamesLost: Int, roundsPlayedInLosingGames: Int) {

    def asLeagueRow = LeagueRow(player, gamesWon, gamesLost, roundsPlayedInWinningGames, roundsPlayedInLosingGames)
    def asSnapshot =
      player ->
        Snapshot(HandCount(), gamesWon, gamesLost, roundsPlayedInWinningGames, roundsPlayedInLosingGames)
  }

  implicit def LeagueRowOrSnapshotToLeagueRow(leagueRowOrSnapshot: LeagueRowOrSnapshot): LeagueRow =
    leagueRowOrSnapshot.asLeagueRow

  implicit def LeagueRowOrSnapshotToSnapshot(leagueRowOrSnapshot: LeagueRowOrSnapshot): Pair[Player, Snapshot] =
    leagueRowOrSnapshot.asSnapshot

  def on(dateTime: DateTime, snapshots: Pair[Player, Snapshot]*): SortedMap[DateTime, Map[Player, Snapshot]] =
    SortedMap(dateTime -> snapshots.foldLeft(Map.empty[Player, Snapshot])(_ + _))
}