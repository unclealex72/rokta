/**
 * Copyright 2013 Alex Jones
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
package model;

import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne
import org.squeryl.dsl.StatefulManyToOne
import dao.RoktaSchema

/**
 * A [[Hand]] played by a [[Person]] during a [[Round]] of a [[Game]].
 */
case class Play (
  /**
   * The synthetic ID for this play.
   */
  val id: Long, 
  /**
   * The ID of the [[Round]] to which this [[Play]] belongs
   */
  val roundId: Long,
  /**
   * The person who played this hand.
   */
  val playerId: Long, 
  /**
   * The hand the person played.
   */
  val _hand: String) extends KeyedEntity[Long] {

  /**
   * Get the hand played in this play.
   */
  lazy val hand: Hand = Hand(_hand).getOrElse({
    throw new IllegalStateException(s"${_hand} is not a valid hand.")})
  
  /**
   * The persisted player of this hand. 
   */
  lazy val _player: StatefulManyToOne[Player] = RoktaSchema.playerToPlays.rightStateful(this)

  /**
   * The player who played this hand.
   */
  lazy val player: Player = _player.one.get

  /**
   * Indicate whether this hand beats another.
   * @param play The other play to check against.
   * @return true if this hand beats the other, false otherwise.
   */
  def beats(play: Play): Boolean = hand beats(play.hand)
}

object Play {
  
  import dao.RoktaSchema._
  import dao.EntryPoint._
  
  def apply(round: Round, player: Player, hand: Hand): Play = { 
    val play = Play(0, round.id, player.id, hand.persistableToken)
    play.save
    play
  }
}