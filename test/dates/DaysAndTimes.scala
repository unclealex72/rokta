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

/**
 * A small DSL for creating easily readable dates and times.
 * @author alex
 *
 */
object DaysAndTimes {

  case class Time(val hour: Int, val minute: Int)
  val midnight = Time(0, 0)
  val midday = Time(12, 0)
  
  implicit class TimeImplicits(hour: Int) {
    def oclock = :>(0)
    def :>(minutes: Int) = Time(hour, minutes)
  }
  
  case class Year(val year: Int) {
    
    def isLeapYear: Boolean = year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)

    def months: Seq[Month] = 
      Seq(January(this), February(this), March(this), April(this), May(this), June(this), 
          July(this), August(this), September(this), October(this), November(this), December(this))
          
    def days: Seq[Day] = months flatMap { m => m.days }
  }

  case class Day(val month: Month, val day: Int) {
    def at(time: Time): DateTime = new DateTime(month.year.year, month.month, day, time.hour, time.minute)
  }

  sealed class Month(val year: Year, val month: Int, val daysInMonth: Int, val extraDayDuringLeapYear: Boolean) {

    def daysInMonthForYear(year: Year): Int = {
      if (extraDayDuringLeapYear && year.isLeapYear) {
        daysInMonth + 1
      } else {
        daysInMonth
      }
    }

    def days: Seq[Day] = (1 to daysInMonthForYear(year)).map(Day(this, _))

  }

  trait DayBuilder {
    def month: Year => Month
    def apply(day: Int, year: Int): Day = Day(apply(year), day)
    def apply(year: Int): Month = month(Year(year))
  }
  
  case class January(_year: Year) extends Month(_year, 1, 31, false)
  object January extends DayBuilder {def month = year => January(year)}
  case class February(val _year: Year) extends Month(_year, 2, 28, true)
  object February extends DayBuilder {def month = year => February(year)}
  case class March(val _year: Year) extends Month(_year, 3, 31, false)
  object March extends DayBuilder {def month = year => March(year)}
  case class April(val _year: Year) extends Month(_year, 4, 30, false)
  object April extends DayBuilder {def month = year => April(year)}
  case class May(val _year: Year) extends Month(_year, 5, 31, false)
  object May extends DayBuilder {def month = year => May(year)}
  case class June(val _year: Year) extends Month(_year, 6, 30, false)
  object June extends DayBuilder {def month = year => June(year)}
  case class July(val _year: Year) extends Month(_year, 7, 31, false)
  object July extends DayBuilder {def month = year => July(year)}
  case class August(val _year: Year) extends Month(_year, 8, 31, false)
  object August extends DayBuilder {def month = year => August(year)}
  case class September(val _year: Year) extends Month(_year, 9, 30, false)
  object September extends DayBuilder {def month = year => September(year)}
  case class October(val _year: Year) extends Month(_year, 10, 31, false)
  object October extends DayBuilder {def month = year => October(year)}
  case class November(val _year: Year) extends Month(_year, 11, 30, false)
  object November extends DayBuilder {def month = year => November(year)}
  case class December(val _year: Year) extends Month(_year, 12, 31, false)
  object December extends DayBuilder {def month = year => December(year)}

}