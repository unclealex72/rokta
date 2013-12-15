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

import org.joda.time.DateTime
import org.specs2.mutable.Specification
import dates.DaysAndTimes
import dates.UtcChronology
import json.Json._
import json.JsonMatchers
import filter.ContiguousGameFilter._
import dates.IsoChronology

/**
 * Test JSON serialisation of [[ContiguousGameFilter]]s.
 * @author alex
 *
 */
class ContiguousGameFilterSpec extends Specification with DaysAndTimes with IsoChronology {

  "A year filter" represents(YearGameFilter(2013), "d2013")
  "A month filter" represents(MonthGameFilter(2013, 5), "d201305")
  "A day filter" represents(DayGameFilter(2013, 5, 20), "d20130520")
  "A since filter" represents(SinceGameFilter(September(5, 2013)), "s20130905")
  "An until filter" represents(UntilGameFilter(September(5, 2013)), "u20130905")
  "A between filter" represents(BetweenGameFilter(November(5, 2012), February(20, 2013)), "b2012110520130220")
  
  implicit class TestNameImplicits(testName: String) {
    def represents(filter: ContiguousGameFilter, str: String) = {
      testName should {
        "serialise correctly" in {
          ContiguousGameFilter.unapply(str) must beSome(filter)
        }
        "deserialise correctly" in {
          ContiguousGameFilter(filter) must be equalTo(str)
        }
      }
    }
  }
}