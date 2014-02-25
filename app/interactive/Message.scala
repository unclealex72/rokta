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

import argonaut._
import argonaut.Argonaut._
import model.Hand
import play.api.mvc.WebSocket.FrameFormatter

/**
 * Messages that can be sent to and from a browser client to progress the state of an interactive game.
 * Created by alex on 21/02/14.
 */
sealed trait Message {
  val jsonType: String
}

/**
 * The parent class of all allowable messages from the browser to the server.
 * @param jsonType
 */
sealed abstract class IncomingMessage(val jsonType: String) extends Message

/**
 * A message that allows the server to send the current state of an interactive game to all clients.
 * @param state
 */
case class CurrentState(state: State) extends Message { val jsonType = "state" }

/**
 * A message used to undo the last state change.
 */
case object Back extends IncomingMessage("back")

/**
 * A message used to totally cancel the current game.
 */
case object Cancel extends IncomingMessage("cancel")

/**
 * A message used to tell the server that someone has instigated.
 * @param instigator
 */
case class Instigator(instigator: String) extends IncomingMessage("instigator")

/**
 * A message used to tell the server that a player has joined the game.
 * @param player
 */
case class NewPlayer(player: String) extends IncomingMessage("newPlayer")

/**
 * A message used to tell the server that no more players will register and all players are ready to go.
 */
case object StartGame extends IncomingMessage("startGame")

/**
 * A message used to tell the server that a player has played a hand.
 * @param player
 * @param hand
 */
case class HandPlayed(player: String, hand: Hand) extends IncomingMessage("handPlayed")
case object AcceptGame extends IncomingMessage("acceptGame")

/**
 * JSON Codecs and FrameFormatter.
 */
object Message {

  implicit val messageEncodeJson: EncodeJson[Message] = EncodeJson { (message: Message) =>
    val jTypeObject  = ("type" := message.jsonType) ->: jEmptyObject
    message match {
      case Back => jTypeObject
      case Cancel => jTypeObject
      case Instigator(instigator) => ("instigator" := instigator) ->: jTypeObject
      case NewPlayer(player) => ("player" := player) ->: jTypeObject
      case StartGame => jTypeObject
      case HandPlayed(player, hand) => ("hand" := hand) ->: ("player" := player) ->: jTypeObject
      case CurrentState(state) => ("state" := state) ->: jTypeObject
      case AcceptGame => jTypeObject
    }
  }

  implicit val messageDecodeJson: DecodeJson[Message] = DecodeJson { cursor =>
    (cursor --\ "type").as[String].flatMap { jsonType => jsonType match {
      case "back" => DecodeResult.ok(Back)
      case "cancel" => DecodeResult.ok(Cancel)
      case "instigator" => jdecode1L(Instigator.apply)("instigator").decode(cursor)
      case "newPlayer" => jdecode1L(NewPlayer.apply)("player").decode(cursor)
      case "startGame" => DecodeResult.ok(StartGame)
      case "handPlayed" => jdecode2L(HandPlayed.apply)("player", "hand").decode(cursor)
      case "acceptGame" => DecodeResult.ok(AcceptGame)
      case "state" => jdecode1L(CurrentState.apply)("state").decode(cursor)
      case ty => DecodeResult.fail(s"$ty is not a valid message type", cursor.history)
    }
    }
  }

  implicit val messageFrameFormatter: FrameFormatter[Message] =
    FrameFormatter.stringFrame.transform(
      message => message.asJson.nospaces,
      str => str.decodeOption[Message].getOrElse(
        throw new IllegalArgumentException(s"$str is not a valid message")
      ))

}
