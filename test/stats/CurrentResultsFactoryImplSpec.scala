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

package stats

import org.specs2.mutable.Specification
import com.escalatesoft.subcut.inject.BindingModule
import dates.DaysAndTimes
import dates.IsoChronology
import dates.StaticNow
import module.EmptyBindingModule
import model.NonPersistedGameDsl
import model.Hand._
/**
 * @author alex
 *
 */
class CurrentResultsFactoryImplSpec extends Specification 
  with DaysAndTimes with IsoChronology with EmptyBindingModule with NonPersistedGameDsl {

  val currentResultsFactory = 
    new CurrentResultsFactoryImpl(Some(StaticNow(September(5, 2013) at (10 oclock))))
  
  "No games being played today" should {
    "mean that no results are collated" in {
      val game = at(September(4, 2013) at midday, freddie plays ROCK, brian plays SCISSORS)
      currentResultsFactory(Seq(game)) must beEmpty
    }
  }
  
  "2 games being played today and 1 yesterday" should {
    "mean that only the 2 games from today are collated" in {
      val yesterday = at(September(4, 2013) at midday, freddie plays SCISSORS, brian plays ROCK)
      val thisMorning = //brian losesAt (September(5, 2013) at (10 oclock)) and (roger plays 1) and (john plays 3)
        at(September(5, 2013) at (10 oclock), brian plays SCISSORS, roger plays ROCK, john plays SCISSORS) and
        (brian plays PAPER, roger plays SCISSORS)
      val thisAfternoon = //john losesAt (September(5, 2013) at (3 oclock).pm) and (roger plays 3)
        at(September(5, 2013) at (3 oclock).pm, john plays SCISSORS, roger plays ROCK)
      currentResultsFactory(Seq(yesterday, thisMorning, thisAfternoon)) must contain(
        roger -> CurrentResults(2, 0),
        brian -> CurrentResults(0, 1),
        john -> CurrentResults(1, 1)
      ).exactly
    }
  } 
}