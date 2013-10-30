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

package filter

import org.specs2.mutable.Specification
import json.JsonSpec
import json.Json._
import com.fasterxml.jackson.databind.ObjectMapper
import dates.DaysAndTimes
import org.joda.time.DateTime
import org.joda.time.Chronology
import org.joda.time.chrono.ISOChronology
import dates.UtcChronology

/**
 * Test JSON serialisation of [[ContiguousGameFilter]]s.
 * @author alex
 *
 */
class ContiguousGameFilterSpec extends Specification with JsonSpec with DaysAndTimes {

  "Deserialising a contiguous game filter" should {
    "correctly deserialise a year filter" in {
      """{"type":"year","year":2013}""" must deserialiseTo(YearGameFilter(2013))
    }
    "correctly deserialise a month filter" in {
      """{"type":"month","year":2013, "month": 5}""" must deserialiseTo(MonthGameFilter(2013, 5))
    }
    "correctly deserialise a day filter" in {
      """{"type":"day","year":2013, "month": 12, "day": 5}""" must deserialiseTo(DayGameFilter(2013, 12, 5))
    }
    "correctly deserialise a between filter" in {
      """{
      "type":"between",
      "from":"2013-02-05T15:00:00.000Z",
      "to":"2013-02-09T12:00:00.000Z"}""" must deserialiseTo(
        BetweenGameFilter(February(5, 2013) at (3 oclock).pm, February(9, 2013) at midday))
    }
    "correctly deserialise a since filter" in {
      """{
      "type":"since",
      "since":"2013-02-09T12:00:00.000Z"}""" must deserialiseTo(
        SinceGameFilter(February(9, 2013) at midday))
    }
    "correctly deserialise an until filter" in {
      """{
      "type":"until",
      "until":"2013-02-09T12:00:00.000Z"}""" must deserialiseTo(
        UntilGameFilter(February(9, 2013) at midday))
    }
  }  
}