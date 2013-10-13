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

import org.joda.time.DateTime
import model.JodaDateTime._

/**
 * A trait for [[PersistedGame]] that allows other components to be tested without having to set up a database.
 * @author alex
 *
 */
trait Game {

  /**
   * The date and time this game was played.
   */
  def datePlayed: DateTime
  
  /**
   * The person who lost the game if it is finished or None otherwise.
   */
  def loser: Option[Player]
  
  /**
   * The total number of rounds played.
   */
  def numberOfRounds: Int
  
  /**
   * The original participants.
   */
  def participants: Set[Player]
  
  /**
   * The number of rounds each player played.
   */
  def roundsPlayed: Map[Player, Int]
}

object Game {
  implicit def gameOrdering: Ordering[Game] = Ordering.by(_.datePlayed)
}