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

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import org.squeryl.KeyedEntity
import org.squeryl.dsl.ManyToOne
import org.squeryl.dsl.OneToMany
import scala.collection.immutable.SortedSet

/**
 * A game is a single game of Rokta.
 */
case class Game(
  /**
   * The synthetic ID of this game.
   */
  val id: Long,
  /**
   * The ID of the person who instigated this game.
   */
  val instigatorId : Long,
  /**
   * The date and time that this game was played.
   */
  val datePlayed: Date,
  /**
   * The year that this game was played.
   */
  val yearPlayed: Integer,
  /**
   * The 0-based month that this game was played.
   */
  val monthPlayed: Integer,
  /**
   * The 1-based week of the year that this game was played.
   */
  val weekPlayed: Integer,
  /**
   * The 1-based day of the month that this game was played.
   */
  val dayPlayed: Integer) extends KeyedEntity[Long] {

  /**
   * The persisted [[Round]] for this game.
   */
  lazy val _rounds: OneToMany[Round] = RoktaSchema.gameToRounds.left(this)

  /**
   * The sorted [[Round]]s for this game.
   */
  lazy val rounds = _rounds.foldLeft(SortedSet.empty[Round]) { case (rs, r) => rs + r }

  /**
   * The persisted instigator for this game.
   */
  lazy val _instigator: ManyToOne[Person] = RoktaSchema.instigatorToGames.right(this)
  
  /**
   * The instigator for this game.
   */
  lazy val instigator: Person = _instigator.single
  
  /**
   * Get the person who lost this game.
   * @return The person who lost this game.
   */
  def loser: Option[Person] = rounds.lastOption flatMap { round =>
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
  def addRound(counter: Person, plays: Map[Person, Hand]): Game = {
    val index = _rounds.size
    _rounds.assign(Round(counter, this, index).addPlays(plays))
    this
  }
}

object Game {
  
  import java.util.Calendar._
  import model.RoktaSchema._
  /**
   * Create a new game.
   * @param instigator The [[Person]] who instigated the game.
   * @param datePlayed The date and time the game was played.
   * @param rounds The [[Round]]s of the game.
   * @return A new unpersisted [[Game]].
   */
  def apply(instigator: Person, datePlayed: Date) : Game = {
    val calendar = new GregorianCalendar
    calendar.setTime(datePlayed)
    val year = calendar get YEAR
    val month = calendar get MONTH
    val day = calendar get DAY_OF_MONTH
    val week = calendar get WEEK_OF_YEAR
    val game = Game(0, instigator.id, datePlayed, year, month, week, day)
    game.save
    game
  }
}