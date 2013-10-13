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

import java.sql.Timestamp

import scala.collection.immutable.SortedSet
import scala.math.Ordering

import org.joda.time.DateTime
import org.squeryl.Table
import org.squeryl.dsl.NonNumericalExpression
import org.squeryl.dsl.ast.LogicalBoolean
import org.squeryl.dsl.fsm.Conditioned
import org.squeryl.dsl.fsm.WhereState

import dao.EntryPoint._
import filter.BetweenGameFilter
import filter.ContiguousGameFilter
import filter.DayGameFilter
import filter.MonthGameFilter
import filter.SinceGameFilter
import filter.UntilGameFilter
import filter.YearGameFilter
import model.PersistedGame
import model.PersistedPlayer

/**
 * The Squeryl implementation of [[GameDao]], [[PersonDao]] and [[Transactional]].
 * @author alex
 *
 */
class SquerylDao extends GameDao with PlayerDao with Transactional {

  def tx[B](block: PlayerDao => GameDao => B) = inTransaction(block(this)(this))

  def games(contiguousGameFilter: Option[ContiguousGameFilter]): SortedSet[PersistedGame] = {
    implicit val ordering: Ordering[PersistedGame] = Ordering.by(_.datePlayed.getMillis())
    from(RoktaSchema.games)(g => contiguousGameFilter match {
      case None => select(g)
      case Some(cgf) => where(contiguous(cgf)(g)) select (g)
    }).foldLeft(SortedSet.empty[PersistedGame]) { case (gs, g) => gs + g }
  }

  implicit def toTimestamp(dt: DateTime): NonNumericalExpression[Timestamp] = new Timestamp(dt.getMillis())

  def contiguous(contiguousGameFilter: ContiguousGameFilter): PersistedGame => LogicalBoolean = { g: PersistedGame =>
    contiguousGameFilter match {
      case YearGameFilter(yearPlayed) => year(g._datePlayed) === yearPlayed
      case MonthGameFilter(yearPlayed, monthPlayed) => 
        year(g._datePlayed) === yearPlayed and month(g._datePlayed) === monthPlayed
      case DayGameFilter(yearPlayed, monthPlayed, dayPlayed) => 
        year(g._datePlayed) === yearPlayed and month(g._datePlayed) === monthPlayed and dayOfMonth(g._datePlayed) === dayPlayed
      case SinceGameFilter(from) => g.datePlayed >= from
      case UntilGameFilter(to) => g.datePlayed <= to
      case BetweenGameFilter(from, to) => g.datePlayed between (from, to)
    }
  }

  def newGame(instigator: PersistedPlayer, datePlayed: DateTime): PersistedGame = PersistedGame(instigator, datePlayed)

}