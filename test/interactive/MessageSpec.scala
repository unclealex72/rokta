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

package interactive

import org.specs2.mutable.Specification
import json.JsonMatchers
import interactive.Message._
import interactive.State._
import model.Hand.ROCK

/**
 * Created by alex on 21/02/14.
 */
class MessageSpec extends Specification with JsonMatchers {

  testJson[Message](
    CurrentState(NotStarted),
    """
      |{
      |  "type": "state",
      |  "state": {
      |    "type": "notStarted"
      |  }
      |}
    """.stripMargin)

  testJson[Message](
    Back,
    """
      |{"type": "back"}
    """.stripMargin)

  testJson[Message](
    Cancel,
    """
      |{"type": "cancel"}
    """.stripMargin)

  testJson[Message](
    Instigator("freddie"),
    """
      |{"type": "instigator", "instigator": "freddie"}
    """.stripMargin)

  testJson[Message](
    NewPlayer("freddie"),
    """
      |{"type": "newPlayer", "player": "freddie"}
    """.stripMargin)

  testJson[Message](
    StartGame,
    """
      |{"type": "startGame"}
    """.stripMargin)

  testJson[Message](
    HandPlayed("freddie", ROCK),
    """
      |{"type":"handPlayed","player":"freddie","hand":"ROCK"}
    """.stripMargin)

  testJson[Message](
    AcceptGame,
    """
      |{"type": "acceptGame"}
    """.stripMargin)

}
