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

import org.specs2.mutable.Specification
import model.Colour._
import model.PlayerFullEncodeJson._
import json.JsonMatchers

/**
 * @author alex
 *
 */
class PlayerSpec extends Specification with JsonMatchers {

  "A player with no email" should {
    "serialise to an object with two fields." in {
      SimplePlayer("Freddie", None, BLACK) must serialiseTo(
          """{"name":"Freddie","email":null,"colour":"BLACK"}""")
    }
  }
  
  "A player with an email" should {
    "serialise to an object with three fields." in {
      SimplePlayer("Freddie", Some("freddie@queen.com"), BLACK) must serialiseTo(
          """{"name":"Freddie","email":"freddie@queen.com","colour":"BLACK"}""")
    }
  }
  
  case class SimplePlayer(name: String, email: Option[String], colour: Colour) extends Player
}