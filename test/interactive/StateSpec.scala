/*
 * Copyright 2014 Alex Jones
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
 * specific language governing permissions and limitation
 * under the License.
 */

package interactive

import org.specs2.mutable.Specification
import json.JsonMatchers
import model.Hand._

/**
 * Created by alex on 21/02/14.
 */
class StateSpec extends Specification with JsonMatchers {

  testJson[State](
    NotStarted,
    """
      |{"type": "notStarted"}
    """.stripMargin)

  testJson[State](
    WaitingForPlayers(Some("john"), "freddie", Set("brian", "roger")),
    """
      |{
      |  "type": "waitingForPlayers",
      |  "exemptPlayer": "john",
      |  "instigator": "freddie",
      |  "players": ["brian", "roger"]
      |}
    """.stripMargin)

  testJson[State](
    GameInProgress(
      "freddie",
      Set("brian", "roger", "john"),
      Set("brian", "roger"),
      Map("brian" -> ROCK, "roger" -> SCISSORS),
      Vector(Map("brian" -> PAPER, "roger" -> PAPER))),
    """
      |{
      |  "type": "gameInProgress",
      |  "instigator": "freddie",
      |  "originalPlayers": ["brian", "roger", "john"],
      |  "currentPlayers": ["brian", "roger"],
      |  "currentRound": {"brian": "ROCK", "roger": "SCISSORS"},
      |  "previousRounds": [{"brian": "PAPER", "roger": "PAPER"}]
      |}
    """.stripMargin)

  testJson[State](
    GameOver(
      "freddie",
      Set("brian", "roger"),
      Vector(Map("brian" -> PAPER, "roger" -> PAPER), Map("brian" -> ROCK, "roger" -> SCISSORS)),
      "roger"),
    """
      |{
      |  "type": "gameOver",
      |  "instigator": "freddie",
      |  "players": ["brian", "roger"],
      |  "rounds": [{"brian": "PAPER", "roger": "PAPER"}, {"brian": "ROCK", "roger": "SCISSORS"}],
      |  "loser": "roger"
      |}
    """.stripMargin)

  "Trying to add an exempt player to the list of waiting players" should {
    "not change the current state" in {
      WaitingForPlayers(Some("freddie"), "brian").consume(NewPlayer("freddie")) must be equalTo(Left(WaitingForPlayers(Some("freddie"), "brian")))
    }
  }

  "Trying to add a player to the list of waiting players" should {
    "add the player if they have not already joined" in {
      WaitingForPlayers(None, "brian").consume(NewPlayer("freddie")) must be equalTo(Left(WaitingForPlayers(None, "brian", Set("freddie"))))
    }
    "be idempotent" in {
      WaitingForPlayers(None, "brian", Set("freddie")).consume(NewPlayer("freddie")) must be equalTo(Left(WaitingForPlayers(None, "brian", Set("freddie"))))
    }
  }

  "Finalising the list of players" should {
    "ignore any requests when there are less than two players" in {
      WaitingForPlayers(None, "brian").consume(StartGame) must be equalTo(
        Left(WaitingForPlayers(None, "brian")))
      WaitingForPlayers(None, "brian", Set("freddie")).consume(StartGame) must be equalTo(
        Left(WaitingForPlayers(None, "brian", Set("freddie"))))
    }
    "start the game proper when there are two or more players" in {
      WaitingForPlayers(None, "brian", Set("brian", "freddie")).consume(StartGame) must be equalTo(
        Left(GameInProgress("brian", Set("brian", "freddie"), Set("brian", "freddie"))))
      WaitingForPlayers(None, "brian", Set("brian", "freddie", "roger")).consume(StartGame) must be equalTo(
        Left(GameInProgress("brian", Set("brian", "freddie", "roger"), Set("brian", "freddie", "roger"))))
    }
  }

  "Playing a new hand" should {
    "only count if the player has yet to play" in {
      GameInProgress(
        "freddie",
        Set("freddie", "brian", "roger", "john"),
        Set("freddie", "brian", "john"),
        Map("brian" -> ROCK),
        Vector(
          Map(
            "brian" -> SCISSORS,
            "roger" -> ROCK,
            "freddie" -> SCISSORS,
            "john" -> SCISSORS))).consume(HandPlayed("freddie", SCISSORS)) must be equalTo(
        Left(GameInProgress(
          "freddie",
          Set("freddie", "brian", "roger", "john"),
          Set("freddie", "brian", "john"),
          Map("brian" -> ROCK, "freddie" -> SCISSORS),
          Vector(
            Map(
              "brian" -> SCISSORS,
              "roger" -> ROCK,
              "freddie" -> SCISSORS,
              "john" -> SCISSORS)))
        ))
      GameInProgress(
        "freddie",
        Set("freddie", "brian", "roger", "john"),
        Set("freddie", "brian", "john"),
        Map("brian" -> ROCK),
        Vector(
          Map(
            "brian" -> SCISSORS,
            "roger" -> ROCK,
            "freddie" -> SCISSORS,
            "john" -> SCISSORS))).consume(HandPlayed("brian", SCISSORS)) must be equalTo(
        Left(GameInProgress(
          "freddie",
          Set("freddie", "brian", "roger", "john"),
          Set("freddie", "brian", "john"),
          Map("brian" -> ROCK),
          Vector(
            Map(
              "brian" -> SCISSORS,
              "roger" -> ROCK,
              "freddie" -> SCISSORS,
              "john" -> SCISSORS)))
        ))
    }
    "progress to the next round with all players if there are no winners" in {
      GameInProgress(
        "freddie",
        Set("freddie", "brian", "roger", "john"),
        Set("freddie", "brian", "john"),
        Map("brian" -> ROCK, "john" -> ROCK),
        Vector(
          Map(
            "brian" -> SCISSORS,
            "roger" -> ROCK,
            "freddie" -> SCISSORS,
            "john" -> SCISSORS))).consume(HandPlayed("freddie", ROCK)) must be equalTo(
        Left(GameInProgress(
          "freddie",
          Set("freddie", "brian", "roger", "john"),
          Set("freddie", "brian", "john"),
          Map(),
          Vector(
            Map("brian" -> SCISSORS, "roger" -> ROCK, "freddie" -> SCISSORS, "john" -> SCISSORS),
            Map("brian" -> ROCK, "john" -> ROCK, "freddie" -> ROCK)))
        ))
    }
    "progress to the next round with all losers if there are winners" in {
      GameInProgress(
        "freddie",
        Set("freddie", "brian", "roger", "john"),
        Set("freddie", "brian", "john"),
        Map("brian" -> ROCK, "john" -> ROCK),
        Vector(
          Map(
            "brian" -> SCISSORS,
            "roger" -> ROCK,
            "freddie" -> SCISSORS,
            "john" -> SCISSORS))).consume(HandPlayed("freddie", PAPER)) must be equalTo(
        Left(GameInProgress(
          "freddie",
          Set("freddie", "brian", "roger", "john"),
          Set("brian", "john"),
          Map(),
          Vector(
            Map("brian" -> SCISSORS, "roger" -> ROCK, "freddie" -> SCISSORS, "john" -> SCISSORS),
            Map("brian" -> ROCK, "john" -> ROCK, "freddie" -> PAPER)))
        ))
    }
  }
  "end the game if there is exactly one loser" in {
    GameInProgress(
      "freddie",
      Set("freddie", "brian", "roger", "john"),
      Set("freddie", "brian", "john"),
      Map("brian" -> ROCK, "john" -> ROCK),
      Vector(
        Map(
          "brian" -> SCISSORS,
          "roger" -> ROCK,
          "freddie" -> SCISSORS,
          "john" -> SCISSORS))).consume(HandPlayed("freddie", SCISSORS)) must be equalTo(
      Left(
        GameOver(
          "freddie",
          Set("freddie", "brian", "roger", "john"),
        Vector(
          Map("brian" -> SCISSORS, "roger" -> ROCK, "freddie" -> SCISSORS, "john" -> SCISSORS),
          Map("brian" -> ROCK, "john" -> ROCK, "freddie" -> SCISSORS)), "freddie")
      ))
  }
}
