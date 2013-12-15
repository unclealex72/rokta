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

import org.specs2.mutable.Specification
import json.JsonMatchers
import dates.DaysAndTimes
import play.api.libs.json.Json._
import scala.collection.SortedSet
import model.NonPersistedGameDsl
/**
 * Tests for deserialising [[Streaks]].
 * @author alex
 *
 */
case class Quickie(value: Boolean, values: Set[String])

class StreaksSpec extends Specification with JsonMatchers with DaysAndTimes with NonPersistedGameDsl {

  "Deserialising a streaks JSON object" should {
    "produce the correct streaks object" in {
      val winningStreaks = 
        SortedSet(
          Streak(freddie, January(1, 2013) at (5 oclock).pm) extendTo(January(2, 2013) at midday),
          Streak(brian, January(3, 2013) at (2 oclock).pm) extendTo(January(3, 2013) at (1 oclock).pm))
      val losingStreaks = 
        SortedSet(
          Streak(roger, January(3, 2013) at (5 oclock).pm) extendTo(January(5, 2013) at midday) makeCurrent,
          Streak(brian, January(1, 2013) at (2 oclock).pm) extendTo(January(2, 2013) at (1 oclock).pm))
      val streaks = Streaks(winningStreaks, losingStreaks)
      streaks must serialiseTo(
        obj(
          "winningStreaks" ->
            arr(
              obj(
                "player" -> brian.name,
                "dateTimes" -> arr("2013-01-03T13:00:00.000Z", "2013-01-03T14:00:00.000Z"),
                "current" -> false), 
              obj(
                "player" -> freddie.name,
                "dateTimes" -> arr("2013-01-01T17:00:00.000Z", "2013-01-02T12:00:00.000Z"),
                "current" -> false)),
          "losingStreaks" -> 
            arr(
              obj(
                "player" -> roger.name,
                "dateTimes" -> arr("2013-01-03T17:00:00.000Z", "2013-01-05T12:00:00.000Z"),
                "current" -> true), 
              obj(
                "player" -> brian.name,
                "dateTimes" -> arr("2013-01-01T14:00:00.000Z", "2013-01-02T13:00:00.000Z"),
                "current" -> false))))
    }
  }
}