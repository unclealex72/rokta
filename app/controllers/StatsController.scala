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
import dao.PlayerDao
import play.api.mvc._
import dao.Transactional
import json.Json._
import stats.SnapshotsFactory
import filter.YearGameFilter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * @author alex
 *
 */
class StatsController(
  _tx: Option[Transactional] = injected,
  _snapshotsFactory: Option[SnapshotsFactory] = injected) 
  extends Controller with JsonResults with AutoInjectable {

  val tx = injectIfMissing(_tx)
  val snapshotsFactory = injectIfMissing(_snapshotsFactory)
  
  def index = Action { request =>
    tx { playerDao => gameDao =>
      json(playerDao.allPlayers)
    }
  }
  
  def snapshots = Action { request =>
    val snapshots = Future { tx { playerDao => gameDao => 
      snapshotsFactory(gameDao.games(Some(YearGameFilter(2013)))) } }
    Async {
      snapshots.map(obj => json(obj))
    }
  }
}