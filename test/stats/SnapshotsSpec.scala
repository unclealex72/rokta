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
import json.JsonSpec
import play.api.libs.json.Json._
import dates.DaysAndTimes
import scala.collection.SortedMap
import model.JodaDateTime._
import model.Player
import model.NonPersistedGameDsl
import stats.Snapshot._
import org.joda.time.DateTime
import play.api.libs.json.Json._

/**
 * @author alex
 *
 */
class SnapshotsSpec extends Specification with JsonSpec with DaysAndTimes with NonPersistedGameDsl {

  "Serialising snapshots" should {
    "create the correct JSON" in {
      val snapshots_one: Map[String, Snapshot] = Map(
        freddie -> win(4).lose(3),
        brian -> win(1).lose(2))
      val snapshots_two: Map[String, Snapshot] = Map(
        john -> win(5).lose(9),
        roger -> win(10).lose(1))
      val snapshots: SortedMap[DateTime, Map[String, Snapshot]] = SortedMap(
        (February(5, 2013) at (5 oclock).pm) -> snapshots_one,
        (February(6, 2013) at (11 oclock)) -> snapshots_two)
      snapshots must serialiseTo(
        obj(
          "2013-02-05T17:00:00.000Z" -> 
            obj(
              "Freddie" -> 
                obj("gamesWon" -> 1, "gamesLost" -> 1, "roundsDuringWinningGames" -> 4, "roundsDuringLosingGames" -> 3), 
              "Brian" -> 
                obj("gamesWon" -> 1, "gamesLost" -> 1, "roundsDuringWinningGames" -> 1, "roundsDuringLosingGames" -> 2)), 
          "2013-02-06T11:00:00.000Z" -> 
            obj(
              "John" -> 
                obj("gamesWon" -> 1, "gamesLost" -> 1, "roundsDuringWinningGames" -> 5, "roundsDuringLosingGames" -> 9), 
              "Roger" -> 
                obj("gamesWon" -> 1, "gamesLost" -> 1, "roundsDuringWinningGames" -> 10, "roundsDuringLosingGames" -> 1))))
    }
  }

  implicit def toNameAndSnapshot(playerAndSnapshot: Pair[Player, Snapshot]): Pair[String, Snapshot] =
    playerAndSnapshot._1.name -> playerAndSnapshot._2

}