/*
 * Copyright 2014 Alex Jones
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
 * specific language governing permissions and limitation
 * under the License.
 */
package model

import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column
import dao.RoktaSchema._
import dao.EntryPoint._
import org.squeryl.dsl.{StatefulManyToOne, StatefulOneToMany}

/**
 * The representation of a Rokta player.
 */
case class PersistedEmail(
  /**
   * The synthetic ID of this element.
   */
  @Column("id")
  val id: Long,
  /**
   * The ID of the [[PersistedPlayer]] to which this [[PersistedEmail]] belongs
   */
  @Column("player_id")
  val playerId: Long,
  /**
   * An email address for a player.
   */
  @Column("email")
	val email: String) extends KeyedEntity[Long] {

  /**
   * The persisted [[Player]]s for this email.
   */
  lazy val _player: StatefulManyToOne[PersistedPlayer] = playerToEmails.rightStateful(PersistedEmail.this)

}

object PersistedEmail {

  def apply(player: PersistedPlayer, email: String): PersistedEmail = {
    val eml = PersistedEmail(0, player.id, email)
    eml.save
    eml
  }

}