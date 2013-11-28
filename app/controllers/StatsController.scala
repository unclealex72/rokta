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
import json.Json._
import stats.SnapshotsFactory
import filter.YearGameFilter
import scala.concurrent.Future
import stats.StatsFactory
import dates.Now
import filter.ContiguousGameFilter
import json.JsonResults
import dao.Transactional

/**
 * @author alex
 *
 */
class StatsController(
  _statsFactory: Option[StatsFactory] = injected,
  _now: Option[Now] = injected,
  _tx: Option[Transactional] = injected)
  extends Etag with JsonResults with AutoInjectable {

  val statsFactory = injectIfMissing(_statsFactory)
  val now = injectIfMissing(_now)
  val tx = injectIfMissing(_tx)

  def stats(filter: String) = filter match {
    case ContiguousGameFilter(gameFilter) => statsForGameFilter(gameFilter)
    case _ => Action { request => NotFound }
  }

  def defaultStats = statsForGameFilter(YearGameFilter(now().getYear))

  def statsForGameFilter(gameFilter: ContiguousGameFilter) = ETag {
    Action { request =>
      json(statsFactory(gameFilter))
    }
  }
  
  def gameLimits = ETag {
    Action { request =>
      val (first, last) = tx { playerDao => gameDao => 
        (gameDao.firstGamePlayed, gameDao.lastGamePlayed)
      }
      json(Map("first" -> first, "last" -> last))
    }
  }
}