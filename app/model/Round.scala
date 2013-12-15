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

import org.squeryl.dsl.OneToMany
import org.squeryl.dsl.ManyToOne
import org.squeryl.KeyedEntity
import org.squeryl.dsl.StatefulManyToOne
import org.squeryl.dsl.StatefulOneToMany
import dao.RoktaSchema
import org.squeryl.annotations.Column

/**
 * A round of [[Play]]s in a [[Game]].
 */
case class Round(
  /**
   * The synthetic ID for this round.
   */
  @Column("id")
  val id: Long,
	/**
	 * The ID of the [[Game]] to which this [[Round]] belongs
	 */
	@Column("game_id")
	val gameId: Long,
	/**
	 * The number of this round within a [[Game]]
	 */
	val round: Int) extends KeyedEntity[Long] {

  import Round._
  
  /**
   * The [[Play]]s contained in this round
   */
  lazy val _plays: StatefulOneToMany[Play] = RoktaSchema.roundToPlays.leftStateful(this)
  lazy val plays: Map[Player, Hand] = _plays.foldLeft(Map.empty[Player, Hand]){(plays, play) =>
    plays + (play.player -> play.hand)
  }

  /**
   * Add plays to this round
   */
  def addPlays(plays: Map[PersistedPlayer, Hand]): Round = {
    plays.foreach { case (person, hand) => this._plays.associate(Play(this, person, hand)) }
    this
  }
}

object Round {
  
  import dao.RoktaSchema._
  import dao.EntryPoint._
  
  implicit def ordering: Ordering[Round] = Ordering.fromLessThan(_.round < _.round)

  def apply(game: PersistedGame, round: Int): Round = {
    val rnd = Round(0, game.id, round)
    rnd.save
    rnd
  }  
}