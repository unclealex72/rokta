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
import json.JsonMatchers
import play.api.libs.json.Json._
import dates.DaysAndTimes
import scala.collection.SortedMap
import model.JodaDateTime._
import model.Player
import model.NonPersistedGameDsl
import stats.Snapshot._
import org.joda.time.DateTime
import play.api.libs.json.Json._
import stats.Snapshot._
import json.Json._
import dates.DateTimeJsonCodec._
import model.Hand.{PAPER, SCISSORS, ROCK}

/**
 * @author alex
 *
 */
class SnapshotSpec extends Specification with JsonMatchers with DaysAndTimes with NonPersistedGameDsl {

  "Serialising snapshots" should {
    "create the correct JSON" in {
      val snapshots_one: Map[String, Snapshot] = Map(
        freddie -> win(ROCK, ROCK, SCISSORS, PAPER).lose(SCISSORS, ROCK, PAPER),
        brian -> win(ROCK).lose(SCISSORS, ROCK))
      val snapshots_two: Map[String, Snapshot] = Map(
        john -> win(SCISSORS, PAPER, PAPER, ROCK, SCISSORS).lose(ROCK, PAPER, SCISSORS, ROCK, PAPER, SCISSORS, ROCK, PAPER, SCISSORS),
        roger -> win(ROCK, ROCK, ROCK, ROCK, PAPER, SCISSORS, SCISSORS, SCISSORS, PAPER, ROCK).lose(SCISSORS))
      val snapshots: SortedMap[DateTime, Map[String, Snapshot]] = SortedMap(
        (February(5, 2013) at (5 oclock).pm) -> snapshots_one,
        (February(6, 2013) at (11 oclock)) -> snapshots_two)
      snapshots.toList must serialiseTo(
        arr(
          arr(
            "2013-02-05T17:00:00.000Z",
            obj(
              "Freddie" ->
                obj(
                  "roundsDuringLosingGames" -> 3,
                  "roundsDuringWinningGames" -> 4,
                  "handCount" -> obj(
                    "countsForAllRounds" -> obj(
                      "ROCK" -> 3,
                      "SCISSORS" -> 2,
                      "PAPER" -> 2
                    ),
                    "countsForFirstRounds" -> obj(
                      "ROCK" -> 1,
                      "SCISSORS" ->1
                    )
                  ),
                  "gamesLost" -> 1,
                  "gamesWon" -> 1
                  ),
              "Brian" -> 
                obj(
                  "roundsDuringLosingGames" -> 2,
                  "roundsDuringWinningGames" -> 1,
                  "handCount" -> obj(
                    "countsForAllRounds" -> obj(
                      "ROCK" -> 2,
                      "SCISSORS" -> 1
                    ),
                    "countsForFirstRounds" -> obj(
                      "ROCK" -> 1,
                      "SCISSORS" -> 1
                    )
                  ),
                  "gamesLost" -> 1,
                  "gamesWon" -> 1
                  ))),
          arr(
            "2013-02-06T11:00:00.000Z",
            obj(
              "John" -> 
                obj(
                  "roundsDuringLosingGames" -> 9,
                  "roundsDuringWinningGames" -> 5,
                  "handCount" -> obj(
                    "countsForAllRounds" -> obj(
                      "SCISSORS" -> 5,
                      "PAPER" -> 5,
                      "ROCK" -> 4
                    ),
                    "countsForFirstRounds" -> obj(
                      "ROCK" -> 1,
                      "SCISSORS" -> 1
                    )
                  ),
                  "gamesLost" -> 1,
                  "gamesWon" -> 1
                  ),
              "Roger" -> 
                obj(
                  "roundsDuringLosingGames" -> 1,
                  "roundsDuringWinningGames" -> 10,
                  "handCount" -> obj(
                    "countsForAllRounds" -> obj(
                      "ROCK" -> 5,
                      "PAPER" -> 2,
                      "SCISSORS" -> 4
                    ),
                    "countsForFirstRounds" -> obj(
                      "SCISSORS" -> 1,
                      "ROCK" -> 1
                    )
                  ),
                  "gamesLost" -> 1,
                  "gamesWon" -> 1)))))
    }
  }

  implicit def toNameAndSnapshot(playerAndSnapshot: Pair[Player, Snapshot]): Pair[String, Snapshot] =
    playerAndSnapshot._1.name -> playerAndSnapshot._2

}