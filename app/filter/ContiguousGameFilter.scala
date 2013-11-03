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
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * A [[ContiguousGameFilter]] is used to filter games based, normally, on dates. Filtered games will always be contiguous.
 * Note that [[ContiguousGameFilter]]s are only really used as DTOs and the implementation of [[GameDao]] needs to understand
 * how to use them.
 * 
 * @author alex
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(Array(
    new Type(value = classOf[BetweenGameFilter], name = "between"),
    new Type(value = classOf[SinceGameFilter], name = "since"),
    new Type(value = classOf[UntilGameFilter], name = "until"),
    new Type(value = classOf[YearGameFilter], name = "year"),
    new Type(value = classOf[MonthGameFilter], name = "month"),
    new Type(value = classOf[DayGameFilter], name = "day")
))
sealed trait ContiguousGameFilter
  
/**
 * A game filter that matches any game between two days inclusive.
 */
case class BetweenGameFilter(
  /**
   * The earliest day to include.
   */
  from: DateTime,
  /**
   * The last day to include.
   */
  to: DateTime) extends ContiguousGameFilter

/**
 * A game filter that matches any game played since a given day.
 */
case class SinceGameFilter(
  /**
   * The earliest time a matched game can be played.
   */
  since: DateTime) extends ContiguousGameFilter

/**
 * A game filter that matches any game played until a given day.
 */
case class UntilGameFilter(
  /**
   * The earliest day a matched game can be played.
   */
  until: DateTime) extends ContiguousGameFilter

/**
 * A game filter that matches games played during a given year.
 */
case class YearGameFilter(
  /**
   * The year when matched games were played.
   */  
  val year: Int) extends ContiguousGameFilter

/**
 * A game filter that matches games played during a given month.
 */
case class MonthGameFilter(
  /**
   * The year when matched games were played.
   */  
  val year: Int,
  /**
   * The month when matched games were played.
   */  
  val month: Int) extends ContiguousGameFilter
/**
 * A game filter that matches games played during a given day.
 */
case class DayGameFilter(
  /**
   * The year when matched games were played.
   */  
  val year: Int,
  /**
   * The month when matched games were played.
   */  
  val month: Int, 
  /**
   * The day when matched games were played.
   */  
  val day: Int) extends ContiguousGameFilter
  
/**
 * A companion object for [[ContiguousGameFilter]] that allows filters to be serialised and deserialised
 * as simple strings that, thus, can be used in URLs.
 */
object ContiguousGameFilter {
  
  private val (yr, mn, dy) = ("""(\d\d\d\d)""", """(\d\d)""", """(\d\d)""")
  private val between = s"b$yr$mn$dy$yr$mn$dy".r
  private val since = s"s$yr$mn$dy".r
  private val until = s"u$yr$mn$dy".r
  private val year = s"d$yr".r
  private val month = s"d$yr$mn".r
  private val day = s"d$yr$mn$dy".r

  /**
   * Deserialise a filter.
   */
  def unapply(str: String): Option[ContiguousGameFilter] = {
    def dt(year: String, month: String, day: String): DateTime = new DateTime(year.toInt, month.toInt, day.toInt, 0, 0)
    str match {
      case(between(fromYear, fromMonth, fromDay, toYear, toMonth, toDay)) =>
        Some(BetweenGameFilter(
            dt(fromYear, fromMonth, fromDay), dt(toYear, toMonth, toDay)))
      case(since(year, month, day)) => Some(SinceGameFilter(dt(year, month, day)))
      case(until(year, month, day)) => Some(UntilGameFilter(dt(year, month, day)))
      case(year(year)) => Some(YearGameFilter(year.toInt))
      case(month(year, month)) => Some(MonthGameFilter(year.toInt, month.toInt))
      case(day(year, month, day)) => Some(DayGameFilter(year.toInt, month.toInt, day.toInt))
      case _ => None
    }
  }
  
  def apply(filter: ContiguousGameFilter): String = filter match {
    case BetweenGameFilter(from, to) => 
      "b%04d%02d%02d%04d%02d%02d".format(
        from.getYear, from.getMonthOfYear, from.getDayOfMonth, to.getYear, to.getMonthOfYear, to.getDayOfMonth)
    case SinceGameFilter(dt) => "s%04d%02d%02d".format(dt.getYear, dt.getMonthOfYear, dt.getDayOfMonth)
    case UntilGameFilter(dt) => "u%04d%02d%02d".format(dt.getYear, dt.getMonthOfYear, dt.getDayOfMonth)
    case YearGameFilter(year) => "d%04d".format(year)
    case MonthGameFilter(year, month) => "d%04d%02d".format(year, month)
    case DayGameFilter(year, month, day) => "d%04d%02d%02d".format(year, month, day)
  }
}