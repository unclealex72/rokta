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
import argonaut._, Argonaut._, DecodeResult._

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
 * A trait for any object that is like a day. That is, it has a year, month and day.
 */
trait DayLike {
  val year: Int
  val month: Int
  val day: Int
}

object DayLikeImplicits {
  
  implicit def toDateTime(day: DayLike): DateTime = new DateTime(day.year, day.month, day.day, 0, 0)
  implicit val dayLikeEncodeJson: EncodeJson[DayLike] =
    jencode3L((d: DayLike) => (d.year, d.month, d.day))("year", "month", "day")
}
/**
 * A solid implementation of `DayLike`
 */
case class Day(year: Int, month: Int, day: Int) extends DayLike

/**
 * A game filter that matches any game between two days inclusive.
 */
case class BetweenGameFilter(
  /**
   * The earliest day to include.
   */
  from: DayLike,
  /**
   * The last day to include.
   */
  to: DayLike) extends ContiguousGameFilter

/**
 * A game filter that matches any game played since a given day.
 */
case class SinceGameFilter(year: Int, month: Int, day: Int) extends ContiguousGameFilter with DayLike
object SinceGameFilter {
  def apply(day: DayLike): SinceGameFilter = SinceGameFilter(day.year, day.month, day.day)
}
/**
 * A game filter that matches any game played until a given day.
 */
case class UntilGameFilter(year: Int, month: Int, day: Int) extends ContiguousGameFilter with DayLike
object UntilGameFilter {
  def apply(day: DayLike): UntilGameFilter = UntilGameFilter(day.year, day.month, day.day)
}

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
  val day: Int) extends ContiguousGameFilter with DayLike
object DayGameFilter {
  def apply(day: DayLike): DayGameFilter = DayGameFilter(day.year, day.month, day.day)
}
  
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
            Day(fromYear.toInt, fromMonth.toInt, fromDay.toInt), Day(toYear.toInt, toMonth.toInt, toDay.toInt)))
      case(since(year, month, day)) => Some(SinceGameFilter(year.toInt, month.toInt, day.toInt))
      case(until(year, month, day)) => Some(UntilGameFilter(year.toInt, month.toInt, day.toInt))
      case(year(year)) => Some(YearGameFilter(year.toInt))
      case(month(year, month)) => Some(MonthGameFilter(year.toInt, month.toInt))
      case(day(year, month, day)) => Some(DayGameFilter(year.toInt, month.toInt, day.toInt))
      case _ => None
    }
  }
  
  def apply(filter: ContiguousGameFilter): String = filter match {
    case BetweenGameFilter(from, to) => 
      "b%04d%02d%02d%04d%02d%02d".format(
        from.year, from.month, from.day, to.year, to.month, to.day)
    case SinceGameFilter(year, month, day) => "s%04d%02d%02d".format(year, month, day)
    case UntilGameFilter(year, month, day) => "u%04d%02d%02d".format(year, month, day)
    case YearGameFilter(year) => "d%04d".format(year)
    case MonthGameFilter(year, month) => "d%04d%02d".format(year, month)
    case DayGameFilter(year, month, day) => "d%04d%02d%02d".format(year, month, day)
  }
  
  implicit def ContiguousGameFilterEncodeJson: EncodeJson[ContiguousGameFilter] = {
    
    import DayLikeImplicits._
    
    def jType(ty: String) = ("type" := ty) ->: jEmptyObject
    def jDayLike(ty: String, year: Option[Int], month: Option[Int], day: Option[Int]) = 
      Seq(year, month, day).zip(Seq("year", "month", "day")).foldLeft(jType(ty)){(json, field) => 
        field._1.foldLeft(json)((json, value) => (field._2 := value) ->: json)
    }
    
    EncodeJson{(f: ContiguousGameFilter) => 
      f match {
        case YearGameFilter(year) => jDayLike("year", Some(year), None, None)
        case MonthGameFilter(year, month) => jDayLike("month", Some(year), Some(month), None)
        case DayGameFilter(year, month, day) => jDayLike("day", Some(year), Some(month), Some(day))
        case SinceGameFilter(year, month, day) => jDayLike("since", Some(year), Some(month), Some(day))
        case UntilGameFilter(year, month, day) => jDayLike("until", Some(year), Some(month), Some(day))
        case BetweenGameFilter(from, to) => 
          ("from" := from) ->: ("to" := to) ->: jType("between")
      }
    }   
  }

}