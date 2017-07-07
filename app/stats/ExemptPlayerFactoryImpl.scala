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
import model.{Game, Player}
import com.escalatesoft.subcut.inject._
import play.api.Play
import play.api.Play.current

/**
 * The default implementation of `ExemptPlayerFactory`.
 * @author alex
 *
 */
class ExemptPlayerFactoryImpl(
  _todaysGamesFactory: Option[TodaysGamesFactory] = injected) extends ExemptPlayerFactory with AutoInjectable {

  val todaysGamesFactory = injectIfMissing(_todaysGamesFactory)

  val enableExemptions: Boolean = Play.configuration.getBoolean("enableExemptions").getOrElse(false)

  private def maybe(maybeExemptPlayer: => Option[Player]): Option[Player] = {
    if (enableExemptions) maybeExemptPlayer else None
  }

  def apply(todaysGames: Iterable[Game]) = maybe(todaysGames.lastOption.flatMap(_.loser))

  def apply() = maybe(apply(todaysGamesFactory.apply()))
}