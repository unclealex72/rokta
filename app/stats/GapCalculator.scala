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

/**
 * A gap calculator is used to calculate the number of games until a player can catch up with the player
 * ahead of them in the league if everything goes their way.
 * @author alex
 *
 */
trait GapCalculator {

  /**
   * Calculate the gap between two rows.
   * @param topLeagueRow The row for the player who is in the lead.
   * @param bottomLeagueRow The row for the player who needs to catch up with the player in the lead.
   * @return The number of games needed for the player to catch up or None if the player cannot catch up.
   */
  def calculateGap(topLeagueRow: LeagueRow, bottomLeagueRow: LeagueRow): Option[Int]
}