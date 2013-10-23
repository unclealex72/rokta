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

import org.joda.time.DateTime

import model.JodaDateTime.dateTimeOrdering

/**
 * A streak is a contiguous set of games that a player has either won or lost.
 * @author alex
 *
 */
case class Streak(
  playerName: String,
  dateTimes: SortedSet[DateTime],
  current: Boolean) {
  
  def candidate: Boolean = dateTimes.size > 1
  
  def extendTo(dateTime: DateTime): Streak = Streak(playerName, dateTimes + dateTime, current)

  def makeCurrent: Streak = Streak(playerName, dateTimes, true)
}

object Streak {
  def apply(playerName: String, dateTime: DateTime): Streak = Streak(playerName, SortedSet(dateTime), false)
  def apply(playerName: String, dateTime: DateTime, dateTimes:DateTime*): Streak = 
    dateTimes.foldLeft(Streak(playerName, dateTime))((streak, dateTime) => streak.extendTo(dateTime))
  
  implicit def streakOrdering: Ordering[Streak] = 
    Ordering.by((s: Streak) => (s.dateTimes.size, s.dateTimes.head, s.playerName)).reverse
}
