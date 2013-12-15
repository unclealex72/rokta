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
package json

import org.specs2.mutable.Specification
import json.Json._
import scala.collection.SortedMap
import argonaut._, Argonaut._

/**
 * Created by alex on 15/12/13.
 */
class JsonSpec extends Specification with JsonMatchers {

  "A sorted map" should {
    "encode all its key value pairs in order" in {
      SortedMap("Four" -> 4, "Three" -> 3, "Two" -> 2, "One" -> 1).toList must serialiseTo("""[["Four",4],["One",1],["Three",3],["Two",2]]""")
    }
  }
}
