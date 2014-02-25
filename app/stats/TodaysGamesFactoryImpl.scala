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

import com.escalatesoft.subcut.inject._
import dates.Now
import model.Game
import scala.collection.SortedSet
import dao.Transactional
import dao.GameDao
import dao.PlayerDao
import org.joda.time.DateTime
import filter.DayGameFilter

/**
 * The default implementation of `TodaysGamesFactory`
 * @author alex
 *
 */
class TodaysGamesFactoryImpl(
  /**
   * The service used to get today.
   */
  _now: Option[Now] = injected,
  /**
   * The service used to retrieve games.
   */
  _tx: Option[Transactional] = injected) extends TodaysGamesFactory with AutoInjectable {

  val now = injectIfMissing(_now)
  val tx = injectIfMissing(_tx)

  def apply(): SortedSet[Game] = {
    val (year, month, day) = ((dt: DateTime) => (dt.getYear, dt.getMonthOfYear(), dt.getDayOfMonth()))(now())
    tx {
      (playerDao: PlayerDao) =>
        (gameDao: GameDao) =>
          gameDao.games(DayGameFilter(year, month, day))
    }
  }
}