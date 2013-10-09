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

import java.util.{SortedSet => JSortedSet}
import java.util.{TreeSet => JTreeSet}
import javax.persistence.Entity
import java.util.Date
import scala.beans.BeanProperty
import javax.persistence.JoinColumn
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import org.hibernate.annotations.Sort
import javax.persistence.CascadeType
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.SortType
import org.hibernate.annotations.Fetch
import java.util.Calendar
import java.util.GregorianCalendar

/**
 * A game is a single game of Rokta.
 */
@Entity(name="Game")
case class Game (
  /**
   * The synthetic ID of this game.
   */
  @BeanProperty  @Id @GeneratedValue
  var id: Integer,
  /**
   * The person who instigated this game.
   */
  @BeanProperty @ManyToOne @Column(name="instigator")
  var instigator : Person,
  /**
   * The rounds played in this game.
   */
  @BeanProperty
  @OneToMany(cascade=Array(CascadeType.ALL))
  @JoinColumn(name="game_id")
  @Sort(`type` = SortType.COMPARATOR, comparator = classOf[RoundComparator])
  @Fetch(FetchMode.JOIN)
  var rounds: JSortedSet[Round],
  /**
   * The date and time that this game was played.
   */
  @BeanProperty @Column(name="datePlayed")
  /**
   * The date and time at which this game was played.
   */
  var datePlayed: Date,
  /**
   * The year that this game was played.
   */
  @BeanProperty @Column(name="yearPlayed")
  var yearPlayed: Integer,
  /**
   * The 0-based month that this game was played.
   */
  @BeanProperty @Column(name="monthPlayed")
  var monthPlayed: Integer,
  /**
   * The 1-based week of the year that this game was played.
   */
  @BeanProperty @Column(name="weekPlayed")
  var weekPlayed: Integer,
  /**
   * The 1-based day of the month that this game was played.
   */
  @BeanProperty @Column(name="dayPlayed")
  var dayPlayed: Integer) {

  /**
   * Get the person who lost this game.
   * @return The person who lost this game.
   */
  def loser: Person = {
    def lastRound = rounds.last()
    lastRound.losers.iterator.next
  }
  
}

object Game {
  
  import java.util.Calendar._
  import scala.collection.JavaConversions._
  
  /**
   * Create a new game.
   * @param instigator The [[Person]] who instigated the game.
   * @param datePlayed The date and time the game was played.
   * @param rounds The [[Round]]s of the game.
   * @return A new unpersisted [[Game]].
   */
  def apply(instigator: Person, datePlayed: Date, rounds: Round*) : Game = {
    val calendar = new GregorianCalendar
    calendar.setTime(datePlayed)
    val year = calendar get YEAR
    val month = calendar get MONTH
    val day = calendar get DAY_OF_MONTH
    val week = calendar get WEEK_OF_YEAR
    val sortedRounds = new JTreeSet(new RoundComparator)
    sortedRounds addAll rounds
    Game(null, instigator, sortedRounds, datePlayed, year, month, week, day)
  }
}