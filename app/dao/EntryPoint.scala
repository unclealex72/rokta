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

package dao

import java.sql.Timestamp
import scala.collection.immutable.SortedSet
import org.squeryl.PrimitiveTypeMode
import org.squeryl.Query
import org.squeryl.dsl.DateExpression
import org.squeryl.dsl.StringExpression
import org.squeryl.dsl.ast.FunctionNode
import org.squeryl.internals.OutMapper
import org.squeryl.dsl.NumericalExpression
import org.joda.time.DateTime

/**
 * @author alex
 *
 */
object EntryPoint extends PrimitiveTypeMode {
    // implicits for queries
  
  object QueryImplicits {
    implicit def queryList[E](q: Query[E]): List[E] = q.iterator.foldLeft(List.empty[E])(_ :+ _)
  
    implicit def querySortedSet[E](q: Query[E])(implicit ord: Ordering[E]): SortedSet[E] = q.iterator.foldLeft(SortedSet.empty[E])(_ + _)
  }

  /**
   * Date time extraction functions.
   */
  class YEAR(e: DateExpression[Timestamp], m:OutMapper[Int]) 
    extends FunctionNode[Int]("year", Some(m), Seq(e)) with NumericalExpression[Int]

  def year(e: DateExpression[Timestamp])(implicit m:OutMapper[Int]) = new YEAR(e,m)

  class MONTH(e: DateExpression[Timestamp], m:OutMapper[Int]) 
    extends FunctionNode[Int]("month", Some(m), Seq(e)) with NumericalExpression[Int]

  def month(e: DateExpression[Timestamp])(implicit m:OutMapper[Int]) = new MONTH(e,m)

  class DAY_OF_MONTH(e: DateExpression[Timestamp], m:OutMapper[Int]) 
    extends FunctionNode[Int]("day_of_month", Some(m), Seq(e)) with NumericalExpression[Int]

  def dayOfMonth(e: DateExpression[Timestamp])(implicit m:OutMapper[Int]) = new DAY_OF_MONTH(e,m)

  class WEEK_OF_YEAR(e: DateExpression[Timestamp], m:OutMapper[Int]) 
    extends FunctionNode[Int]("week", Some(m), Seq(e)) with NumericalExpression[Int]

  def weekOfYear(e: DateExpression[Timestamp])(implicit m:OutMapper[Int]) = new WEEK_OF_YEAR(e,m)
}
