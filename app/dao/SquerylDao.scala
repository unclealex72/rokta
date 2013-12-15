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
import org.joda.time.DateTime
import org.squeryl.dsl.NonNumericalExpression
import org.squeryl.dsl.UnaryAgregateLengthNeutralOp
import org.squeryl.dsl.ast.LogicalBoolean
import dao.EntryPoint.compute
import dao.EntryPoint.createOutMapperIntType
import dao.EntryPoint.dayOfMonth
import dao.EntryPoint.from
import dao.EntryPoint.inTransaction
import dao.EntryPoint.int2ScalarInt
import dao.EntryPoint.long2ScalarLong
import dao.EntryPoint.max
import dao.EntryPoint.min
import dao.EntryPoint.month
import dao.EntryPoint.select
import dao.EntryPoint.timestamp2ScalarTimestamp
import dao.EntryPoint.unaryOpConv17
import dao.EntryPoint.where
import dao.EntryPoint.year
import dao.RoktaSchema.{games => tgames}
import dao.RoktaSchema.{players => tplayers}
import dao.RoktaSchema.{plays => tplays}
import dao.RoktaSchema.{rounds => trounds}
import filter.BetweenGameFilter
import filter.ContiguousGameFilter
import filter.DayGameFilter
import filter.MonthGameFilter
import filter.SinceGameFilter
import filter.UntilGameFilter
import filter.YearGameFilter
import filter.DayLikeImplicits._
import model.NonPersistedGame
import model.Game
import model.Hand
import model.PersistedGame
import model.PersistedPlayer
import model.Player
import model.UploadableGame
import org.squeryl.dsl.Measures
import filter.Day
import model.NonPersistedGame

/**
 * The Squeryl implementation of [[GameDao]], [[PersonDao]] and [[Transactional]].
 * @author alex
 *
 */
class SquerylDao extends GameDao with PlayerDao with Transactional {

  def tx[B](block: PlayerDao => GameDao => B) = inTransaction(block(this)(this))

  def filteredGames(filter: PersistedGame => LogicalBoolean): SortedSet[Game] = {
    val gamesInstigatorsRoundsPlayersPlays = 
    from(tgames, tplayers, trounds, tplays, tplayers)((g, i, r, p, pr) => 
      where(
        r.gameId === g.id and p.roundId === r.id and p.playerId === pr.id and g.instigatorId === i.id and 
        filter(g)) 
      select (g, i, r.round, pr, p._hand) 
      orderBy(g.id, r.round)) map {case (g, i, round, p, h) => (g, i, round, p, Hand(h))}
    SortedSet(gamesInstigatorsRoundsPlayersPlays.groupBy(kv => (kv._1, kv._2)).map(NonPersistedGame(_)).toSeq: _*)
  }

  def games(contiguousGameFilter: ContiguousGameFilter): SortedSet[Game] = 
    filteredGames(contiguous(contiguousGameFilter))

  implicit def toTimestamp(dt: DateTime): NonNumericalExpression[Timestamp] = new Timestamp(dt.getMillis())

  def contiguous(filter: ContiguousGameFilter): PersistedGame => LogicalBoolean = { g: PersistedGame =>
    filter match {
      case YearGameFilter(yearPlayed) => year(g._datePlayed) === yearPlayed
      case MonthGameFilter(yearPlayed, monthPlayed) => 
        year(g._datePlayed) === yearPlayed and month(g._datePlayed) === monthPlayed
      case DayGameFilter(yearPlayed, monthPlayed, dayPlayed) => 
        year(g._datePlayed) === yearPlayed and 
        month(g._datePlayed) === monthPlayed and dayOfMonth(g._datePlayed) === dayPlayed
      case SinceGameFilter(year, month, day) => g.datePlayed >= Day(year, month, day).withTimeAtStartOfDay
      case UntilGameFilter(year, month, day) => g.datePlayed < Day(year, month, day).withTimeAtStartOfDay.plusDays(1)
      case BetweenGameFilter(from, to) => 
        g.datePlayed between (from.withTimeAtStartOfDay, to.withTimeAtStartOfDay.plusDays(1).minusMillis(1))
    }
  }

  def allPersistedPlayers: Iterable[PersistedPlayer] = from(tplayers)(p => select(p))
  
  def allPlayers: Set[Player] = allPersistedPlayers.toSet
  
  def limitGamePlayed(f: NonNumericalExpression[Timestamp] => UnaryAgregateLengthNeutralOp[Timestamp]): Option[DateTime] = 
    from(tgames)(g => compute(f(g._datePlayed))).headOption.map(_.measures.get).map(new DateTime(_))

  def firstGamePlayed: Option[DateTime] = limitGamePlayed(min[Timestamp]);

  def lastGamePlayed: Option[DateTime] = limitGamePlayed(max[Timestamp]);

    def newGame(instigator: PersistedPlayer, datePlayed: DateTime): PersistedGame = PersistedGame(instigator, datePlayed)

  def uploadGame(datePlayed: DateTime, uploadableGame: UploadableGame) = {
    val players = allPersistedPlayers
    val persistedPlayer: Player => PersistedPlayer = p => p match {
      case p: PersistedPlayer => p
      case p: Player => players.find(_.name == p.name).getOrElse(throw new IllegalArgumentException("Cannot find player " + p.name))
    }
    val game = newGame(persistedPlayer(uploadableGame.instigator), datePlayed)
    uploadableGame.rounds.foldLeft(game) { (game, plays) => 
      val persistablePlays = plays.foldLeft(Map.empty[PersistedPlayer, Hand]){ (persistablePlays, playedHand) =>
        persistablePlays + (persistedPlayer(playedHand._1) -> playedHand._2)
      }
      game.addRound(persistablePlays)
      game
    }
  }
}