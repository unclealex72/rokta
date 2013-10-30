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
import scala.collection.SortedSet
import scala.math.Ordering
import org.joda.time.DateTime
import org.squeryl.dsl.NonNumericalExpression
import org.squeryl.dsl.ast.LogicalBoolean
import dao.EntryPoint.createOutMapperIntType
import dao.EntryPoint.dayOfMonth
import dao.EntryPoint.from
import dao.EntryPoint.inTransaction
import dao.EntryPoint.int2ScalarInt
import dao.EntryPoint.month
import dao.EntryPoint.orderByArg2OrderByExpression
import dao.EntryPoint.select
import dao.EntryPoint.timestamp2ScalarTimestamp
import dao.EntryPoint.typedExpression2OrderByArg
import dao.EntryPoint.where
import dao.EntryPoint.year
import dao.EntryPoint.weekOfYear
import dao.RoktaSchema.{games => tgames}
import dao.RoktaSchema.{players => tplayers}
import filter.BetweenGameFilter
import filter.ContiguousGameFilter
import filter.DayGameFilter
import filter.MonthGameFilter
import filter.SinceGameFilter
import filter.UntilGameFilter
import filter.YearGameFilter
import model.PersistedGame
import model.PersistedPlayer
import model.Player
import model.Game

/**
 * The Squeryl implementation of [[GameDao]], [[PersonDao]] and [[Transactional]].
 * @author alex
 *
 */
class SquerylDao extends GameDao with PlayerDao with Transactional {

  def tx[B](block: PlayerDao => GameDao => B) = inTransaction(block(this)(this))

  def games(contiguousGameFilter: ContiguousGameFilter): SortedSet[Game] = {
    implicit val ordering: Ordering[PersistedGame] = Ordering.by(_.datePlayed.getMillis())
    from(tgames)(g => where(contiguous(contiguousGameFilter)(g)) select (g)).
      foldLeft(SortedSet.empty[Game]) { case (gs, g) => gs + g }
  }

  implicit def toTimestamp(dt: DateTime): NonNumericalExpression[Timestamp] = new Timestamp(dt.getMillis())

  def contiguous(contiguousGameFilter: ContiguousGameFilter): PersistedGame => LogicalBoolean = { g: PersistedGame =>
    contiguousGameFilter match {
      case YearGameFilter(yearPlayed) => year(g._datePlayed) === yearPlayed
      case MonthGameFilter(yearPlayed, monthPlayed) => 
        year(g._datePlayed) === yearPlayed and month(g._datePlayed) === monthPlayed
      case DayGameFilter(yearPlayed, monthPlayed, dayPlayed) => 
        year(g._datePlayed) === yearPlayed and 
        month(g._datePlayed) === monthPlayed and dayOfMonth(g._datePlayed) === dayPlayed
      case SinceGameFilter(from) => g.datePlayed >= from.withTimeAtStartOfDay
      case UntilGameFilter(to) => g.datePlayed < to.withTimeAtStartOfDay.plusDays(1)
      case BetweenGameFilter(from, to) => 
        g.datePlayed between (from.withTimeAtStartOfDay, to.withTimeAtStartOfDay.plusDays(1).minusMillis(1))
    }
  }

  def allPlayers: Set[Player] = from(tplayers)(p => select(p)).toSet
  
  def firstGamePlayed: Option[PersistedGame] = 
    from(tgames)(g => select(g) orderBy(g._datePlayed).asc).page(0, 1).headOption

  def newGame(instigator: PersistedPlayer, datePlayed: DateTime): PersistedGame = PersistedGame(instigator, datePlayed)

}