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

package model

/**
 * A hand played in a game of rock, scissors paper.
 * @author alex
 *
 */
sealed abstract class Hand(
  /**
   * This hand's persistable token
   */
  val persistableToken: String,
  /**
   * A human readable description of this hand.
   */
  val description: String) extends Hand.Value {

  /**
   * Return true if this hand beats another hand, false otherwise.
   * @param otherHand The other hand.
   * @return true if this hand beats the other hand, false otherwise.
   */
  def beats(otherHand: Hand): Boolean
  
  override def toString = s"Hand($persistableToken)"
}

object Hand extends PersistableEnumeration[Hand] {

  /**
   * Rock.
   */
  case object ROCK extends Hand("ROCK", "Rock") {
    def beats(otherHand: Hand): Boolean = otherHand == SCISSORS
  }
  ROCK

  /**
   * Scissors.
   */
  case object SCISSORS extends Hand("SCISSORS", "Scissors") {
    def beats(otherHand: Hand): Boolean = otherHand == PAPER
  }
  SCISSORS

  /**
   * Paper.
   */
  case object PAPER extends Hand("PAPER", "Paper") {
    def beats(otherHand: Hand): Boolean = otherHand == ROCK    
  }
  PAPER
}