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

import scala.collection.SortedSet
import scala.collection.SortedMap
import org.joda.time.DateTime
import model.Player
import filter.ContiguousGameFilter
import filter.ContiguousGameFilter
import model.Game

/**
 * An immutable collection of all current stats.
 * @author alex
 *
 */
case class Stats[G <: Game](
  /**
   * The serialised contiguous game filter used to source these statistics.
   */
  contiguousGameFilter: ContiguousGameFilter,
  /**
   * True if these stats are current as the filter used to produce them contains today, false otherwise.
   */
  current: Boolean,
  /**
   * The current results for today, if any.
   */
  currentResults: Map[String, CurrentResults],
  /**
   * All known players.
   */
  players: Set[Player],
  /**
   * The full league.
   */
  league: SortedSet[LeagueRow],
  /**
   * All the snapshots for each game and player.
   */
  snapshots: SortedMap[DateTime, Map[String, Snapshot]],
  /**
   * All winning and losing streaks.
   */
  streaks: Streaks,
  /**
   * The currently exempt player, on none if no games have been played today.
   */
  exemptPlayer: Option[String],
  /**
   * The last game that was played if these Stats are current, None otherwise.
   */
  lastGame: Option[G],
  /**
   * The number of games that have been played today.
   */
  numberOfGamesToday: Int  
)