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
import model.NonPersistedGameDsl._
import dates.DaysAndTimes
import model.Game
import model.Player
import scala.collection.SortedMap
import org.joda.time.DateTime
import model.JodaDateTime._
import stats.Snapshot._
import scala.collection.SortedSet
import dates.IsoChronology

/**
 * @author alex
 *
 */
class SnapshotsFactoryImplSpec extends Specification with DaysAndTimes with IsoChronology {

  val statisticsService = new SnapshotsFactoryImpl

  "Adding a new player to a snapshot" should {
    val game: Game = freddie losesAt (September(12, 2013) at (11 oclock)) whilst (brian plays (3)) and (roger plays (2))
    val startingPoint = SnapshotCumulation()
    "record their wins if they win" in {
      val newCumulation = statisticsService.addToSnapshot(game)(startingPoint, roger)
      val newSnapshot = newCumulation.cumulativeSnapshot
      newSnapshot.toSeq must contain(exactly(roger.asInstanceOf[Player] -> win(2)))
      newCumulation.historicalSnapshots.toSeq must be empty
    }
    "record their losses if they lose" in {
      val newCumulation = statisticsService.addToSnapshot(game)(startingPoint, freddie)
      val newSnapshot = newCumulation.cumulativeSnapshot
      newSnapshot.toSeq must contain(exactly(freddie.asInstanceOf[Player] -> lose(3)))
      newCumulation.historicalSnapshots.toSeq must be empty
    }
  }

  "Updating a player in a snapshot" should {
    val game: Game = freddie losesAt (September(12, 2013) at (11 oclock)) whilst (brian plays 3) and (roger plays 2)
    val startingPoint = SnapshotCumulation(
      Map(freddie -> win(1), roger -> lose(3), brian -> win(4)), 
      SortedMap.empty[DateTime, Map[Player, Snapshot]])
    "update their wins if they win" in {
      val newCumulation = statisticsService.addToSnapshot(game)(startingPoint, roger)
      val newSnapshot = newCumulation.cumulativeSnapshot
      newSnapshot.toSeq must contain(exactly(
          freddie.asInstanceOf[Player] -> win(1),
          roger -> lose(3).win(2),
          brian -> win(4)))
      newCumulation.historicalSnapshots.toSeq must be empty
    }
    "record their losses if they lose" in {
      val newCumulation = statisticsService.addToSnapshot(game)(startingPoint, freddie)
      val newSnapshot = newCumulation.cumulativeSnapshot
      newSnapshot.toSeq must contain(exactly(
          freddie.asInstanceOf[Player] -> win(1).lose(3),
          roger -> lose(3),
          brian -> win(4)))
      newCumulation.historicalSnapshots.toSeq must be empty
    }
  }
  
  "Adding a game to a snapshot" should {
    val previousSnapshot: Map[Player, Snapshot] = 
      Map(freddie -> win(2), brian -> lose(3), roger -> lose(4))
    val startingPoint = SnapshotCumulation(
      previousSnapshot, 
      SortedMap((September(12, 2013) at (11 oclock)) -> previousSnapshot))
    val game: Game = freddie losesAt (September(12, 2013) at (12 oclock)) whilst (brian plays 3) and (john plays 2)
    val expectedSnapshot: Map[Player, Snapshot] =
      Map(freddie -> win(2).lose(3), brian -> lose(3).win(3), roger -> lose(4), john -> win(2))
    val newCumulation = statisticsService.accumulateSnapshots(startingPoint, game)
    "Add the game data to the latest cumulation" in {
      newCumulation.cumulativeSnapshot must be equalTo(expectedSnapshot)
    }
    "Record the latest cumulation in its history" in {
      newCumulation.historicalSnapshots.toSeq must contain(exactly(
        (September(12, 2013) at (11 oclock)) -> previousSnapshot, (September(12, 2013) at (12 oclock)) -> expectedSnapshot)).inOrder
    }
  }
  
  "Getting the snapshot history for three games" should {
    val timeA = September(12, 2013) at (11 oclock)
    val timeB = September(12, 2013) at (15 oclock)
    val timeC = September(13, 2013) at (9 oclock)
    val gameA: Game = freddie losesAt timeA whilst (brian plays 3) and (roger plays 4)
    val gameB: Game = brian losesAt timeB whilst (roger plays 2)
    val gameC: Game = freddie losesAt timeC whilst (brian plays 1) and (roger plays 2)
    val snapshotA: Map[Player, Snapshot] = Map(freddie -> lose(4), brian -> win(3), roger -> win(4))
    val snapshotAB: Map[Player, Snapshot] = Map(freddie -> lose(4), brian -> win(3).lose(2), roger -> win(4).win(2))
    val snapshotABC: Map[Player, Snapshot] = 
      Map(freddie -> lose(4).lose(2), brian -> win(3).lose(2).win(1), roger -> win(4).win(2).win(2))
    val snapshots: IndexedSeq[Pair[DateTime, Map[Player, Snapshot]]] = 
      statisticsService.snapshots(Seq(gameA, gameB, gameC)).toIndexedSeq
    "record three snapshots" in {
      snapshots must have size(3)
    }
    "record the first snapshot" in {
      snapshots(0) must be equalTo(timeA -> snapshotA)
    }
    "record the second snapshot" in {
      snapshots(1) must be equalTo(timeB -> snapshotAB)
    }
    "record the third snapshot" in {
      snapshots(2) must be equalTo(timeC -> snapshotABC)
    }
  }
}