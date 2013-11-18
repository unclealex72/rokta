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

import org.joda.time.DateTime
import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column
import org.squeryl.dsl.StatefulManyToOne
import org.squeryl.dsl.StatefulOneToMany

import dao.EntryPoint.__thisDsl
import dao.RoktaSchema.anyRef2ActiveTransaction
import dao.RoktaSchema.gameToRounds
import dao.RoktaSchema.instigatorToGames
import model.JodaDateTime.dateTimeOrdering

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
  val _datePlayed: Timestamp) extends KeyedEntity[Long] {

  /**
   * The Joda Time version of the date and time the game was played.
   */
  lazy val datePlayed: DateTime = new DateTime(_datePlayed)

  /**
   * The persisted [[Round]] for this game.
   */
  lazy val rounds: StatefulOneToMany[Round] = gameToRounds.leftStateful(PersistedGame.this)

  /**
   * The persisted instigator for this game.
   */
  lazy val instigator: StatefulManyToOne[PersistedPlayer] = instigatorToGames.rightStateful(PersistedGame.this)

  /**
   * Add a new round to this game.
   * @param plays The [[Hand]]s played by each [[Person]].
   * @return this.
   */
  def addRound(plays: Map[PersistedPlayer, Hand]): PersistedGame = {
    rounds.associate((Round(PersistedGame.this, numberOfRounds)).addPlays(plays))
    PersistedGame.this
  }
  
  def numberOfRounds: Int = rounds.size + 1
}

object PersistedGame {

  import dao.RoktaSchema._
  import dao.EntryPoint._
  import model.JodaDateTime._
  
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