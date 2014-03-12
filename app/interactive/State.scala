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

import argonaut._, Argonaut._
import com.typesafe.scalalogging.slf4j.Logging
import model.{Winners, Hand}

/**
 * Case classes to encapsulate the current state of an interactive game. Each state can consume a [[Message]]
 * which then tells the actor the new state and any [[Action]]s that need to be performed. Any messages
 * that do not make sense for the current state are logged and ignored.
 * Created by alex on 21/02/14.
 */
sealed abstract class State(val jsonType: String) extends Logging {

  implicit val changeState: State => Either[State, Action] = Left(_)
  implicit val performAction: Action => Either[State, Action] = Right(_)

  def noChange(warning: String): State = {
    logger.warn(warning)
    this
  }

  def noChange(incomingMessage: IncomingMessage): State =
    noChange(s"Message $incomingMessage does not make sense for state $jsonType")

  val cancel: Action = Quit
  val undo: Action = Undo
  val echo: Action = Echo
  def consume: IncomingMessage => Either[State, Action]
}

/**
 * The state for when no game has been started.
 */
case object NotStarted extends State("notStarted") {
  def consume = {
    case Instigator(instigator) => Start(instigator)
    case SendCurrentState => echo
    case m => noChange(m)
  }
}

/**
 * The state for a game that has been instigated and players are joining.
 * @param instigator
 * @param players
 */
case class WaitingForPlayers(exemptPlayer: Option[String], instigator: String, players: Set[String] = Set.empty[String]) extends State("waitingForPlayers") {
  def consume = {
    case NewPlayer(player) => {
      if (players.contains(player)) {
        noChange(s"Player $player has already joined.")
      }
      else if (Some(player) == exemptPlayer) {
        noChange(s"Player $player is exempt.")
      }
      else {
        val newPlayers = players + player
        WaitingForPlayers(exemptPlayer, instigator, newPlayers)
      }
    }
    case StartGame => {
      if (players.size > 1) {
        GameInProgress(instigator, players, players)
      }
      else {
        noChange("There are not enough players to start a game.")
      }
    }
    case Back => undo
    case Cancel => cancel
    case SendCurrentState => echo
    case m => noChange(m)
  }

}

/**
 * The state for when a game is currently being played.
 * @param instigator
 * @param originalPlayers
 * @param currentPlayers
 * @param currentRound
 * @param previousRounds
 */
case class GameInProgress(
  instigator: String,
  originalPlayers: Set[String],
  currentPlayers: Set[String],
  currentRound: Map[String, Hand] = Map.empty,
  previousRounds: Vector[Map[String, Hand]] = Vector.empty) extends State("gameInProgress") {

  def consume = {
    case HandPlayed(player, hand) => {
      currentRound.get(player) match {
        case Some(_) => noChange(s"$player has already played.")
        case None => {
          val newCurrentRound =
            if (currentPlayers.contains(player)) currentRound + (player -> hand) else currentRound
          if (newCurrentRound.keySet == currentPlayers) {
            val playedHands = newCurrentRound.values.toSet
            // No losers - go to next round.
            if (playedHands.size != 2) {
              GameInProgress(instigator, originalPlayers, currentPlayers, Map.empty, previousRounds :+ newCurrentRound)
            }
            else {
              val winners = Winners(newCurrentRound)
              val losers = currentPlayers -- winners
              if (losers.size == 1) {
                //Game over!
                GameOver(instigator, originalPlayers, previousRounds :+ newCurrentRound, losers.head)
              }
              else {
                GameInProgress(instigator, originalPlayers, losers, Map.empty, previousRounds :+ newCurrentRound)
              }
            }
          }
          else {
            GameInProgress(instigator, originalPlayers, currentPlayers, newCurrentRound, previousRounds)
          }
        }
      }
    }
    case Back => undo
    case Cancel => cancel
    case SendCurrentState => echo
    case m => noChange(m)
  }
}

/**
 * The state when the game has finished but has yet to be committed to the database.
 * @param instigator
 * @param loser
 * @param rounds
 */
case class GameOver(
  instigator: String, players: Set[String], rounds: Vector[Map[String, Hand]], loser: String) extends State("gameOver") {

  def consume = {
    case AcceptGame => UploadGame(instigator, players, rounds)
    case Back => undo
    case Cancel => cancel
    case SendCurrentState => echo
    case m => noChange(m)
  }
}

/**
 * JSON codecs
 */
object State {

  implicit val stateEncodeJson: EncodeJson[State] = EncodeJson { (state: State) =>
    val jTypeObject  = ("type" := state.jsonType) ->: jEmptyObject
    state match {
      case NotStarted => jTypeObject
      case WaitingForPlayers(exemptPlayer, instigator, players) =>
        ("exemptPlayer" := exemptPlayer) ->: ("instigator" := instigator) ->: ("players" := players) ->: jTypeObject
      case GameInProgress(instigator, originalPlayers, currentPlayers, currentRound, previousRounds) =>
        ("instigator" := instigator) ->: ("originalPlayers" := originalPlayers) ->: ("currentPlayers" := currentPlayers) ->: ("currentRound" := currentRound) ->:
          ("previousRounds" := previousRounds) ->: jTypeObject
      case GameOver(instigator, players, rounds, loser) =>
        ("instigator" := instigator) ->: ("players" := players) ->: ("rounds" := rounds) ->: ("loser" := loser) ->: jTypeObject
    }
  }

  implicit val stateDecodeJson: DecodeJson[State] = DecodeJson { cursor =>
    (cursor --\ "type").as[String].flatMap { jsonType => jsonType match {
      case "notStarted" => DecodeResult.ok(NotStarted)
      case "waitingForPlayers" =>
        jdecode3L(WaitingForPlayers.apply)("exemptPlayer", "instigator", "players").decode(cursor)
      case "gameInProgress" =>
        jdecode5L(
          GameInProgress.apply)(
            "instigator", "originalPlayers", "currentPlayers", "currentRound", "previousRounds").decode(
            cursor)
      case "gameOver" =>
        jdecode4L(GameOver.apply)("instigator", "players", "rounds", "loser").decode(cursor)
      case ty => DecodeResult.fail(s"${ty} is not a valid state type", cursor.history)
    }
    }
  }
}
