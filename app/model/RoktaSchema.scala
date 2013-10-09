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

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode

/**
 * The Rokta persistence schema.
 * @author alex
 *
 */
object RoktaSchema extends Schema with PrimitiveTypeMode {

  val games = table[Game]
  on(games)(g => declare(
    g.id is (autoIncremented)))

  val rounds = table[Round]
  on(rounds)(r => declare(
    r.id is (autoIncremented)))

  val plays = table[Play]
  on(plays)(p => declare(
    p.id is (autoIncremented)))

  val players = table[Person]
  on(players)(p => declare(
    p.id is (autoIncremented)))

  val instigatorToGames = oneToManyRelation(players, games).via((p, g) => p.id === g.instigatorId)
  val gameToRounds = oneToManyRelation(games, rounds).via((g, r) => g.id === r.gameId)
  val roundToPlays = oneToManyRelation(rounds, plays).via((r, p) => r.id === p.roundId)
  val counterToRounds = oneToManyRelation(players, rounds).via((p, r) => p.id === r.counterId)
  val playerToPlays = oneToManyRelation(players, plays).via((player, play) => player.id === play.playerId)

}