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

package dates

import org.specs2.mutable.Specification
import org.joda.time.DateTime

/**
 * @author alex
 *
 */
class WhenImplicitsSpec extends Specification with DaysAndTimes with IsoChronology with WhenImplicits {

  val now: Now = StaticNow(September(5, 1972) at (10 oclock))
  
  "Two times on the same day" should {
    val when = September(5, 1972) at (3 oclock).pm
    "be recognised as being on the same day" in {
      when.isToday must beTrue
    }
    "be recognised as being in the same month" in {
      when.isThisMonth must beTrue
    }
    "be recognised as being in the same year" in {
      when.isThisYear must beTrue
    }
  }
  
  "Two different days in the same month" should {
    val when = September(6, 1972) at (3 oclock).pm
    "not be recognised as being on the same day" in {
      when.isToday must beFalse
    }
    "be recognised as being in the same month" in {
      when.isThisMonth must beTrue
    }
    "be recognised as being in the same year" in {
      when.isThisYear must beTrue
    }
  }
  
  "Two different days in different months in the same year" should {
    val when = August(5, 1972) at (3 oclock).pm
    "not be recognised as being on the same day" in {
      when.isToday must beFalse
    }
    "not be recognised as being in the same month" in {
      when.isThisMonth must beFalse
    }
    "be recognised as being in the same year" in {
      when.isThisYear must beTrue
    }
  }
  
  "The same day in two different years" should {
    val when = September(5, 1973) at (10 oclock)
    "not be recognised as being on the same day" in {
      when.isToday must beFalse
    }
    "not be recognised as being in the same month" in {
      when.isThisMonth must beFalse
    }
    "not be recognised as being in the same year" in {
      when.isThisYear must beFalse
    }
  }
}