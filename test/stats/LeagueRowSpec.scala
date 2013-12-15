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
import stats.LeagueRow._
import scala.collection.SortedSet
import json.JsonMatchers
import play.api.libs.json.Json._
import play.api.libs.json.JsNull
import model.NonPersistedGameDsl
import stats.LeagueRow._
import json.Json._

/**
 * @author alex
 *
 */
class LeagueRowSpec extends Specification with JsonMatchers with NonPersistedGameDsl {

  "League rows with lower loss rates" should {
    "beat those with higher loss rates" in {
      LeagueRow(freddie, 3, 1, 1, 1) must be lessThan (LeagueRow(freddie, 1, 3, 1, 1))
    }
  }

  "League rows with equal loss rates but with lower rounds per won game rates" should {
    "beat those with equal loss rates but with higher rounds per won game rates" in {
      LeagueRow(freddie, 1, 1, 1, 1) must be lessThan (LeagueRow(freddie, 1, 1, 2, 1))
    }
  }

  "League rows with equal loss rates and rounds per won game rates but with higher rounds per lost game rates" should {
    "beat those with equal loss rates and rounds per won game rates but with lower rounds per won game rates" in {
      LeagueRow(freddie, 1, 1, 1, 2) must be lessThan (LeagueRow(freddie, 1, 1, 1, 1))
    }
  }

  "League rows with equal loss rates and rounds per won game rates but with higher rounds per lost game rates" should {
    "beat those with equal loss rates and rounds per won game rates but with lower rounds per won game rates" in {
      LeagueRow(freddie, 1, 1, 1, 2) must be lessThan (LeagueRow(freddie, 1, 1, 1, 1))
    }
  }

  "League rows with equal loss rates and round ratios but with more games played" should {
    "beat those with equal loss rates and round ratios but with less games played" in {
      LeagueRow(freddie, 2, 2, 2, 2) must be lessThan (LeagueRow(freddie, 1, 1, 1, 1))
    }
  }

  "League rows that are equal except for player name" should {
    "be ordered by the name of the player" in {
      LeagueRow(freddie, 2, 2, 2, 2) must be lessThan (LeagueRow(roger, 2, 2, 2, 2))

    }
  }

  "A league" should {
    "correctly serialise" in {
      val leagueRows = SortedSet(
        LeagueRow(freddie, 5, 1, 10, 20).withCurrentlyPlaying(true).withGap(2).withExempt(true),
        LeagueRow(brian, 4, 2, 11, 21).withCurrentlyPlaying(true).withGap(1).withMovement(1),
        LeagueRow(john, 3, 3, 12, 22).withMovement(-1),
        LeagueRow(roger, 2, 4, 13, 23).withMovement(-1))
      leagueRows must serialiseTo(
        arr(
          obj(
            "player" -> freddie.name,
            "gamesWon" -> 5,
            "gamesLost" -> 1,
            "roundsDuringWinningGames" -> 10,
            "roundsDuringLosingGames" -> 20,
            "currentlyPlaying" -> true,
            "gap" -> 2,
            "movement" -> JsNull,
            "exempt" -> true),
          obj(
            "player" -> brian.name,
            "gamesWon" -> 4,
            "gamesLost" -> 2,
            "roundsDuringWinningGames" -> 11,
            "roundsDuringLosingGames" -> 21,
            "currentlyPlaying" -> true,
            "gap" -> 1,
            "movement" -> 1,
            "exempt" -> false),
          obj(
            "player" -> john.name,
            "gamesWon" -> 3,
            "gamesLost" -> 3,
            "roundsDuringWinningGames" -> 12,
            "roundsDuringLosingGames" -> 22,
            "currentlyPlaying" -> false,
            "gap" -> JsNull,
            "movement" -> -1,
            "exempt" -> false),
          obj(
            "player" -> roger.name,
            "gamesWon" -> 2,
            "gamesLost" -> 4,
            "roundsDuringWinningGames" -> 13,
            "roundsDuringLosingGames" -> 23,
            "currentlyPlaying" -> false,
            "gap" -> JsNull,
            "movement" -> -1,
            "exempt" -> false)))
    }
  }
}