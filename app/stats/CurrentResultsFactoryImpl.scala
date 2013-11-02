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

import scala.collection.Iterable
import scala.collection.immutable.Map
import model.Game
import dates.Now
import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.injected
import org.joda.time.DateTime
import dates.WhenImplicits
import model.Player

/**
 * @author alex
 *
 */
class CurrentResultsFactoryImpl(val _now: Option[Now] = injected) extends CurrentResultsFactory with AutoInjectable with WhenImplicits {

  val now = injectIfMissing(_now)
  
  def apply(games: Iterable[Game]): Map[String, CurrentResults] = {
    val todaysGames = games.filter(g => g.datePlayed.isToday)
    todaysGames.foldLeft(Map.empty[String, CurrentResults])(currentResultsPerGame)
  }
  
  /**
   * Work out how each game changes the current results.
   */
  def currentResultsPerGame(currentResults: Map[String, CurrentResults], game: Game): Map[String, CurrentResults] = {
    game.loser match {
      case Some(loser) => game.participants.foldLeft(currentResults) { (currentResults, player) => 
        val playersCurrentResults = currentResults.get(player).getOrElse(CurrentResults())
        currentResults + (player -> (
            if (player == loser) playersCurrentResults.withLoss else playersCurrentResults.withWin))
      }
      case None => currentResults
    }
  }
}