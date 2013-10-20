/* Copyright 2013 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
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
 * @author unclealex72
 *
 */
package model

import java.sql.Timestamp

import scala.collection.immutable.SortedSet
import org.squeryl.annotations.Column

import org.joda.time.DateTime
import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne
import org.squeryl.dsl.OneToMany
import org.squeryl.dsl.StatefulManyToOne
import org.squeryl.dsl.StatefulOneToMany
import dao.RoktaSchema._

/**
 * A game is a single game of Rokta.
 */
case class PersistedGame(
  /**
   * The synthetic ID of this game.
   */
  @Column("id")
  val id: Long, 
  /**
   * The ID of the person who instigated this game.
   */
  @Column("instigator_id")
  val instigatorId: Long, 
  /**
   * The date and time that this game was played.
   */
  @Column("dateplayed")
  val _datePlayed: Timestamp) extends KeyedEntity[Long] with Game {

  /**
   * The Joda Time version of the date and time the game was played.
   */
  lazy val datePlayed: DateTime = new DateTime(_datePlayed)

  /**
   * The persisted [[Round]] for this game.
   */
  lazy val _rounds: StatefulOneToMany[Round] = gameToRounds.leftStateful(PersistedGame.this)

  /**
   * The sorted [[Round]]s for this game.
   */
  lazy val rounds = _rounds.foldLeft(SortedSet.empty[Round]) { case (rs, r) => rs + r }

  /**
   * The persisted instigator for this game.
   */
  lazy val _instigator: StatefulManyToOne[PersistedPlayer] = instigatorToGames.rightStateful(PersistedGame.this)

  /**
   * The instigator for this game.
   */
  lazy val instigator: PersistedPlayer = _instigator.one.get

  lazy val participants: Set[Player] = rounds.headOption.map(_.participants).getOrElse(Set.empty[Player])
  
  /**
   * Get the person who lost this game.
   * @return The person who lost this game.
   */
  def loser: Option[Player] = rounds.lastOption flatMap { round =>
    round.losers.size match {
      case 1 => Some(round.losers.iterator.next)
      case _ => None
    }
  }

  /**
   * Add a new round to this game.
   * @param counter The [[Person]] who counted in the round.
   * @param plays The [[Hand]]s played by each [[Person]].
   * @return this.
   */
  def addRound(counter: PersistedPlayer, plays: Map[PersistedPlayer, Hand]): PersistedGame = {
    _rounds.associate((Round(counter, PersistedGame.this, numberOfRounds)).addPlays(plays))
    PersistedGame.this
  }
  
  def numberOfRounds: Int = _rounds.size
  
  lazy val roundsPlayed: Map[Player, Int] = 
    participants.foldLeft(Map.empty[Player, Int]){ (map, player) => 
      val roundsPlayed = _rounds.filter(_.participants.contains(player)).size
      map + (player -> roundsPlayed)
    }.withDefaultValue(0)
}

object PersistedGame {

  import dao.RoktaSchema._
  import dao.EntryPoint._
  import model.JodaDateTime._
  
  /**
   * Ordering
   */
  implicit val gameOrdering: Ordering[PersistedGame] = Ordering.by(_.datePlayed)

  /**
   * Create a new game.
   * @param instigator The [[Person]] who instigated the game.
   * @param datePlayed The date and time the game was played.
   * @param rounds The [[Round]]s of the game.
   * @return A new unpersisted [[Game]].
   */
  def apply(instigator: PersistedPlayer, datePlayed: DateTime): PersistedGame = {
    val game = PersistedGame(0, instigator.id, new Timestamp(datePlayed.toDate().getTime()))
    game.save
    game
  }
}