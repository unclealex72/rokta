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

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode._
import javax.persistence.Entity
import java.util.{Set => JSet}
import javax.persistence.GeneratedValue
import javax.persistence.Id
import scala.beans.BeanProperty
import javax.persistence.OneToMany
import javax.persistence.CascadeType._
import javax.persistence.JoinColumn
import javax.persistence.Column
import scala.collection.JavaConversions._
import java.util.Comparator

/**
 * A round of [[Play]]s in a [[Game]].
 */
@Entity(name="Round")
case class Round(
  /**
   * The synthetic ID for this round.
   */
  @BeanProperty @GeneratedValue @Id
  var id: Integer,
  /**
   * The [[Play]]s contained in this round
   */
  @BeanProperty
  @OneToMany(cascade=Array(ALL))
  @JoinColumn(name="round_id")
  @Column(nullable=false)
  @Fetch(JOIN)
	var plays: JSet[Play],
	/**
	 * The [[Person]] who counted in this round.
	 */
	var counter: Person,
	/**
	 * The number of this round within a [[Game]]
	 */
	var round: Integer) {
	
  /**
   * A convenience function to turn a set of [[Play]]s into [[Person players]]
   */
  def playersOf: JSet[Play] => Set[Person] = 
    plays => plays.foldLeft(Set.empty[Person]) { (ps: Set[Person], p: Play) => ps + p.person }

  /**
   * Get a set of all the participants in this round.
   * @return A set of all the participants in this round.
   */
	def participants: Set[Person] = playersOf (plays)
	  

	/**
	 * Get a list of all the people who lost this round. If exactly two types of hands are played then
	 * the players who played the losing hand are returned, otherwise, all the participants are.
	 * @return The people who lost this round.
	 */
	def losers: Set[Person] = {
	  val playedHands = plays map { _.hand }
	  playedHands.size match {
	    case 2 => {
	      val playedHandsIterator = playedHands.iterator
	      val firstHand = playedHandsIterator.next
	      val secondHand = playedHandsIterator.next
	      val losingHand = if (firstHand.beats(secondHand)) secondHand else firstHand
	      playersOf (plays filter { _.hand == losingHand})
	    }
	    case _ => participants
	  }
	}
}

object Round {
  
  /**
   * Create a new [[Round]]
   * @param round The number of the round within a game.
   * @param counter The [[Person]] who counted in the players.
   * @Param plays The [[Play]]s made by the players.
   */
  def apply(round: Integer, counter: Person, plays: Play*): Round =
    Round(null, plays.toSet, counter, round)
  
}
/**
 * A comparator that can be used to compare [[Round]]s by their index.
 */
class RoundComparator extends Comparator[Round] {
  
  override def compare(r1: Round, r2: Round) = r1.round - r2.round
}