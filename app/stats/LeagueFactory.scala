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

import scala.collection.SortedMap
import org.joda.time.DateTime
import model.Player
import scala.collection.SortedSet

/**
 * A trait for generating [[League]]s.
 * @author alex
 *
 */
trait LeagueFactory {

  /**
   * Generate a league for all [[Snapshot]]s.
   * @param snapshots The [[Snapshot]]s used to generate the league.
   * @param todaysPlayers A set of the players who played today or None if this league is not current.
   * @param exemptPlayer The player who is currently exempt or None if no-one is exempt.
   * @return The set of [[LeagueRow]]s from top to bottom.
   */
  def generateLeague(
    snapshots: SortedMap[DateTime, Map[Player, Snapshot]], 
    todaysPlayers: Option[Set[Player]], exemptPlayer: Option[Player]): SortedSet[LeagueRow]
}