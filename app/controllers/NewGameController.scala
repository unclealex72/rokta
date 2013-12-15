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

package controllers

import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.injected
import dao.Transactional
import dates.Now
import json.JsonBodyParser
import model.{PersistedGame, UploadableGame, Game, Player}
import model.UploadableGame._
import model.Game._
import model.PersistedGameImplicits._
import json.JsonResults
import stats.TodaysGamesFactory
import stats.ExemptPlayerFactory
import scalaz.{Failure, Success}

/**
 * The controller for creating and persisting new games.
 * @author alex
 *
 */
class NewGameController(
  _tx: Option[Transactional] = injected,
  _todaysGamesFactory: Option[TodaysGamesFactory] = injected,
  _exemptPlayerFactory: Option[ExemptPlayerFactory] = injected,
  _now: Option[Now] = injected) extends SecureController with JsonResults with AutoInjectable {

  val now = injectIfMissing(_now)
  val exemptPlayerFactory = injectIfMissing(_exemptPlayerFactory)
  val todaysGamesFactory = injectIfMissing(_todaysGamesFactory)
  
  def availablePlayers = AuthorisedAjaxAction { implicit request =>
    val players = (tx { playerDao => gameDao => playerDao.allPlayers }).map(_.name)
    val exemptPlayer: Option[Player] = exemptPlayerFactory(todaysGamesFactory())
    json(Map(
      "availablePlayers" -> (exemptPlayer match {
        case Some(exemptPlayer) => players - exemptPlayer.name
        case None => players
      }),
      "instigators" -> players))
  }
  
  def uploadGame = {
    val allPlayersByName = tx { playerDao => gameDao => playerDao.allPlayers.groupBy(_.name) }
    // Allow unknown players to fail horribly.
    val playerFactory = (name: String) => allPlayersByName.get(name).flatMap(_.headOption).get
    implicit val uploadableGameDecodeJson = UploadableGameDecodeJson(playerFactory)
    AuthorisedAjaxAction(JsonBodyParser[UploadableGame]) { implicit request =>
      request.body match {
        case Success(uploadableGame) => tx { playerDao => gameDao =>
          val persistedGame = gameDao.uploadGame(now(), uploadableGame)
          json(persistedGame)
        }
        case Failure(message) => BadRequest(message)
      }
    }
  }
}