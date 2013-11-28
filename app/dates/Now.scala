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

import org.joda.time.DateTime
import filter.Day
import filter.DayLike

/**
 * A trait that can be used to return the current date and time or, in the case of testing, any
 * required date and time.
 * @author alex
 *
 */
trait Now {

  /**
   * Return the current date and time.
   */
  def apply(): DateTime

}

/**
 * Implicits for [[DateTime]]s that allow them to be checked against today.
 */
trait WhenImplicits {

  self: {

    val now: Now
  } =>

  implicit class DateTimeImplicits(when: DateTime) {

    def isThisYear: Boolean = when.getYear() == now().getYear()

    def isThisMonth: Boolean = {
      val today = now()
      when.getYear() == today.getYear() && when.getMonthOfYear() == today.getMonthOfYear()
    }

    def isToday: Boolean = {
      val today = now()
      when.getYear() == today.getYear() && when.getDayOfYear() == today.getDayOfYear()
    }
  }

  implicit class DayImplicits(when: DayLike) {

    def isThisYear: Boolean = when.year == now().getYear()

    def isThisMonth: Boolean = {
      val today = now()
      when.year == today.getYear() && when.month == today.getMonthOfYear()
    }

    def isToday: Boolean = {
      val today = now()
      when.year == today.getYear() && when.month == today.getMonthOfYear() && when.day == today.getDayOfMonth()
    }
  }
}

/**
 * A simple implementation of [[WhenImplicits]] that takes the required [[Now]] as a constructor argument.
 */
case class BasicWhenImplicits(val now: Now) extends WhenImplicits