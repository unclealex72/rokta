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

package stats

/**
 * An object used to keep track of the number of games a player has won and lost (and thus played) during one day.
 * @author alex
 *
 */
case class CurrentResults(
  /**
   * The number of games the player has won.
   */  
  val gamesWon: Int = 0, 
  /**
   * The number of games the player has lost.
   */
  val gamesLost: Int = 0) {
  
  /**
   * Create a new [[CurrentResults]] with an extra win.
   */
  def withWin: CurrentResults = CurrentResults(gamesWon + 1, gamesLost)

  /**
   * Create a new [[CurrentResults]] with an extra loss.
   */
  def withLoss: CurrentResults = CurrentResults(gamesWon, gamesLost + 1)

}