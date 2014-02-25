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

import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification

import dates.UtcChronology
import play.api.libs.json.JsValue
import play.api.libs.json.{Json => PJson}
import argonaut._, Argonaut._, DecodeResult._
import model.Hand

/**
 * A trait that includes a `serialisesTo` matcher to allow serialisation testing.
 * @author alex
 *
 */
trait JsonMatchers extends UtcChronology { self: Specification =>
  
  /**
   * Match a JSON object to how it is serialised.
   */
  def serialiseTo[A](serialised: String)(implicit encoder: EncodeJson[A]): Matcher[A] =
    serialiseTo[A](PJson.parse(serialised))

  /**
   * Match a JSON object to how it is serialised.
   */
  def serialiseTo[A](serialised: => JsValue)(implicit encoder: EncodeJson[A]): Matcher[A] = {
    ((a: A) => PJson.parse(encoder.encode(a).toString)) ^^ beEqualTo(serialised)
  }

  /**
   * Allow a JSON object within a string be matched against a serialised object.
   */
  def deserialiseTo[A](deserialised: => A)(implicit d: DecodeJson[A]) : Matcher[String] =
    ((s: String) => Json.read[A](s)) ^^ beEqualTo(deserialised)

  /**
   * Test that a map of objects serialise and deserialise to the correct Json.
   */
  def testJson[A](cases: Map[A, String])(implicit d: DecodeJson[A], evEncode: EncodeJson[A]) {
    cases.foreach { case (obj, serialised) => testJson(obj, serialised) }
  }

  def testJson[A](obj: A, serialised: String)(implicit evDecode: DecodeJson[A], evEncode: EncodeJson[A]) {
    s"Serialising $obj to JSON" should {
      s"serialise it to $serialised" in {
        obj must serialiseTo(serialised)
      }
    }
    s"Deserialising $serialised from JSON" should {
      s"deserialise it to $obj" in {
        serialised.decodeOption[A] must beSome(obj)
      }
    }

  }
}