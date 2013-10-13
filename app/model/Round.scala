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

/**
 * A round of [[Play]]s in a [[Game]].
 */
case class Round(
  /**
   * The synthetic ID for this round.
   */
  val id: Long,
	/**
	 * The [[Person]] who counted in this round.
	 */
	val counterId: Long,
	/**
	 * The ID of the [[Game]] to which this [[Round]] belongs
	 */
	val gameId: Long,
	/**
	 * The number of this round within a [[Game]]
	 */
	val round: Int) extends KeyedEntity[Long] {

  import Round._
  
  /**
   * The persisted [[Person]] who is counting for this round.
   */
  lazy val _counter: StatefulManyToOne[PersistedPlayer] = RoktaSchema.counterToRounds.rightStateful(this)

  /**
   * The [[Person]] who counted this round.
   */
  lazy val counter: PersistedPlayer = _counter.one.get
  
  /**
   * The [[Play]]s contained in this round
   */
  lazy val plays: StatefulOneToMany[Play] = RoktaSchema.roundToPlays.leftStateful(this)

  /**
   * The participants of this game.
   */
  lazy val participants: Set[Player] = playersOf(plays)

  /**
   * Add plays to this round
   */
  def addPlays(plays: Map[PersistedPlayer, Hand]): Round = {
    plays.foreach { case (person, hand) => this.plays.associate(Play(this, person, hand)) }
    this
  }
	/**
	 * Get a list of all the people who lost this round. If exactly two types of hands are played then
	 * the players who played the losing hand are returned, otherwise, all the participants are.
	 * @return The people who lost this round.
	 */
	lazy val losers: Set[Player] = {
	  val playedHands = plays.foldLeft(Set.empty[Hand]) { case (hands, play) => hands + play.hand }
	  playedHands.size match {
	    case 2 => {
	      val playedHandSeq = playedHands toIndexedSeq
	      val firstHand = playedHandSeq(0)
	      val secondHand = playedHandSeq(1)
	      val losingHand = if (firstHand.beats(secondHand)) secondHand else firstHand
	      playersOf (plays filter { _.hand == losingHand})
	    }
	    case _ => participants
	  }
	}
}

object Round {
  
  import dao.RoktaSchema._
  import dao.EntryPoint._
  
  implicit def ordering: Ordering[Round] = Ordering.fromLessThan(_.round < _.round)

  def apply(counter: PersistedPlayer, game: PersistedGame, round: Int): Round = {
    val rnd = Round(0, counter.id, game.id, round)
    rnd.save
    rnd
  }
  /**
   * A convenience function to map [[Play]]s to their players.
   */
  def playersOf: Iterable[Play] => Set[Player] = plays =>
    plays.foldLeft(Set.empty[Player]) { (ps, p) => ps + p.player }


}