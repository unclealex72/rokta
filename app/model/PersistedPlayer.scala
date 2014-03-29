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
package model

import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column
import com.fasterxml.jackson.annotation.JsonIgnore
import org.squeryl.dsl.StatefulOneToMany
import dao.RoktaSchema._
import dao.EntryPoint._
import argonaut.EncodeJson
import scala.collection.SortedMap

/**
 * The representation of a Rokta player.
 */
case class PersistedPlayer(
  /**
   * The synthetic ID of this element.
   */
  @Column("id")
  val id: Long,
  /**
   * The player's name.
   */
  @Column("name")
	val name: String,
  /**
   * The player's avatar URL, if any.
   */
  @Column("avatarurl")
  val avatarUrl: Option[String],
  /**
   * The name of the colour used to represent the player's results in any graphs.
   */
  @Column("graphingcolour")
  @JsonIgnore
	val _colour:  String) extends KeyedEntity[Long] with Player {
  
  lazy val colour: Colour = _colour match {
    case Colour(colour) => colour
    case _ => throw new IllegalStateException(s"${_colour} is not a valid colour.")
  }
}

object PersistedPlayer {

  /**
   * Create a simple player for testing purposes.
   * @param name
   * @return
   */
  def apply(name: String): PersistedPlayer = PersistedPlayer(0, name, None, "BLACK")
}