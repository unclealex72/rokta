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

package dao

import scala.collection.SortedSet

import org.joda.time.DateTime

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

import filter.BetweenGameFilter
import filter.ContiguousGameFilter
import filter.DayGameFilter
import filter.MonthGameFilter
import filter.SinceGameFilter
import filter.UntilGameFilter
import filter.YearGameFilter
import model.Game
import model.PersistedGame
import model.PersistedPlayer

/**
 * A Data access object used to persist and retrieve [[Game]]s.
 * @author alex
 *
 */
trait GameDao {

  /**
   * Get all games matching an optional [[ContiguousGameFilter]] in date played order.
   * @param contiguousGameFilter A [[ContiguousGameFilter]] used to optionally filter games.
   * @return All played games matching the supplied filters in date played order.
   */
  def games(
    contiguousGameFilter: Option[ContiguousGameFilter]): SortedSet[Game]
  
  /**
   * Get the first game played.
   * @return the first game played or none if no games have been played.
   */
  def firstGamePlayed: Option[Game]

  /**
   * Create a new game to which rounds can be added.
   * @param instigator The instigator
   * @param datePlayed The date and time the game was played.
   * @return A new game.
   */
  def newGame(instigator: PersistedPlayer, datePlayed: DateTime): PersistedGame
}