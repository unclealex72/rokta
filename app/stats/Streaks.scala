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
import argonaut._, Argonaut._
import json.Json._

/**
 * A container to hold winning and losing streaks.
 * @author alex
 *
 */
case class Streaks(
  /**
   * A set of winning streaks.
   */
  winningStreaks: SortedSet[Streak], 
  /**
   * A set of losing streaks.
   */
  losingStreaks: SortedSet[Streak]) {

  def withWinningStreak(winningStreak: Streak): Streaks = Streaks(winningStreaks + winningStreak, losingStreaks)

  def withLosingStreak(losingStreak: Streak): Streaks = Streaks(winningStreaks, losingStreaks + losingStreak)
}

object Streaks {
  
  def apply(): Streaks = Streaks(SortedSet.empty[Streak], SortedSet.empty[Streak])
  
  /**
   * JSON serialisation
   */
  implicit val streaksEncodeJson: EncodeJson[Streaks] =
    jencode2L((s: Streaks) => (s.winningStreaks, s.losingStreaks))("winningStreaks", "losingStreaks")
}