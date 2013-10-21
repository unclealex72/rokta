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
import org.specs2.matcher.Matcher
import dates.UtcChronology
/**
 * A trait that includes a `serialisesTo` matcher to allow serialisation testing.
 * @author alex
 *
 */
trait JsonSpec extends UtcChronology { self: Specification =>
  
  /**
   * Allow a JSON object within a string be matched against a serialised object.
   */
  def deserialiseTo[A: Manifest](deserialised: => A): Matcher[String] = 
    ((s: String) => objectMapper.readValue(s, manifest[A].runtimeClass)) ^^ beEqualTo(deserialised)
  
}