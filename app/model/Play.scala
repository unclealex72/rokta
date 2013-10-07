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

import scala.beans.BeanProperty

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode._

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

/**
 * A [[Hand]] played by a [[Person]] during a [[Round]] of a [[Game]].
 */
@Entity(name="Play")
case class Play (
  /**
   * The synthetic ID for this play.
   */
  @BeanProperty @Id @GeneratedValue  
  var id: Integer, 
  /**
   * The person who played this hand.
   */
  @BeanProperty
  @ManyToOne
  @Fetch(JOIN)
  var person: Person, 
  /**
   * The hand the person played.
   */
  @BeanProperty
  @Column(name="hand")
  var handPersisted: String) {

  /**
   * Get the hand played in this play.
   */
  def hand = Hand(handPersisted).getOrElse({
    throw new IllegalStateException(s"$handPersisted is not a valid hand.")})
  
  /**
   * Indicate whether this hand beats another.
   * @param play The other play to check against.
   * @return true if this hand beats the other, false otherwise.
   */
  def beats(play: Play): Boolean = hand beats(play.hand)
}

object Play {
  
  /**
   * Create a new [[Play]].
   * @param person The [[Person]] who played the hand.
   * @param hand The [[Hand]] played by the person.
   * @return A new unpersisted Play.
   */
  def apply(person: Person, hand: Hand): Play = Play(null, person, hand.persistableToken)
}