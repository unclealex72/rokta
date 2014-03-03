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

import argonaut._, Argonaut._, DecodeResult._

/**
 * A database independent trait for Rokta players.
 * @author alex
 *
 */
trait Player {

  /**
   * The player's name.
   */
  def name: String
  /**
   * The name of the colour used to represent the player's results in any graphs.
   */
  def colour:  Colour

}

object PlayerFullEncodeJson {

  /**
   * JSON serialisation.
   */
  implicit val playerEncodeJson: EncodeJson[Player] =
    jencode2L((p: Player) => (p.name, p.colour.persistableToken))("name", "colour")
}

object PlayerNameEncodeJson {

  /**
   * JSON serialisation.
   */
  implicit val playerEncodeJson: EncodeJson[Player] = EncodeJson((p: Player) => jString(p.name))
  
  /**
   * JSON serialisation for maps that have players as keys.
   * 
   */
  implicit def playerMapEncodeJson[A](implicit ev: EncodeJson[A]): EncodeJson[Map[Player, A]] = 
    EncodeJson { (m: Map[Player, A]) =>
      m.foldLeft(jEmptyObject) { (json, kv) =>
        (kv._1.name := kv._2) ->: json
      }
  }
}