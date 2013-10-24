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

import scala.collection.Iterable
import model.Game
import scala.collection.SortedMap
import org.joda.time.DateTime
import model.JodaDateTime._

/**
 * The default implementation of {@link StatisticsService}.
 * 
 * @author alex
 *
 */
class SnapshotsFactoryImpl extends SnapshotsFactory {

  def snapshots(games: Iterable[Game]): SortedMap[DateTime, Map[String, Snapshot]] = {
    games.foldLeft(SnapshotCumulation())(accumulateSnapshots).historicalSnapshots
  }

  /**
   * Add a [[Game]] to a [[SnapshotCumulation]]
   */
  def accumulateSnapshots: (SnapshotCumulation, Game) => SnapshotCumulation = { (previousSnapshotCumulation, game) =>
    val cumulativeSnapshot =
      game.participants.map(_.name).foldLeft(previousSnapshotCumulation)(addToSnapshot(game)).cumulativeSnapshot
    val historicalSnapshots: SortedMap[DateTime, Map[String, Snapshot]] =
      previousSnapshotCumulation.historicalSnapshots + (game.datePlayed -> cumulativeSnapshot)
    SnapshotCumulation(cumulativeSnapshot, historicalSnapshots)
  }

  /**
   * Add the result for one [[String]] in one [[Game]] to a [[SnapshotCumulation]]
   */
  def addToSnapshot(game: Game): (SnapshotCumulation, String) => SnapshotCumulation = { (snapshotCumulation, playerName) =>
    val previousSnapshotEntry = snapshotCumulation.cumulativeSnapshot.get(playerName).getOrElse(Snapshot(0, 0, 0, 0))
    game.loser match {
      case Some(loser) => {
        val rounds = game.roundsPlayed.find(_._1.name == playerName).map(_._2).getOrElse(0)
        val newSnapshotEntry = if (playerName == loser.name) {
          previousSnapshotEntry.lose(rounds)
        } else {
          previousSnapshotEntry.win(rounds)
        }
        SnapshotCumulation(
          snapshotCumulation.cumulativeSnapshot + (playerName -> newSnapshotEntry),
          snapshotCumulation.historicalSnapshots)
      }
      case None => snapshotCumulation
    }
  }
}

/**
 * A class to hold cumulative snapshots as well as a history so that it can be used in folds to build up
 * lists of snapshots.
 */
case class SnapshotCumulation(
  /**
   * The current snapshot information for each player.
   */
  val cumulativeSnapshot: Map[String, Snapshot],
  /**
   * The historical information of previous snapshots.
   */
  val historicalSnapshots: SortedMap[DateTime, Map[String, Snapshot]])

object SnapshotCumulation {
  def apply(): SnapshotCumulation =
    SnapshotCumulation(Map.empty[String, Snapshot], SortedMap.empty[DateTime, Map[String, Snapshot]])
}
