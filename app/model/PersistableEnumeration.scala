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

package model

import scala.collection.mutable.{ Map, Seq }

/**
 * A base class for enumeration type objects that can be persisted in a database
 * @author alex
 *
 */
trait PersistableEnumeration[E] {

  /**
   * Find an enumeration by its persistable token.
   */
  def apply(persistableToken: String) = valueMap.get(persistableToken)

  trait Value { self: E =>

    /**
     *  The index used for ordering.
     */
    val index = _values.length
    /**
     * The token that is persisted to the database.
     */
    val persistableToken: String

    _values :+= this
    _valueMap += (this.persistableToken -> this)

    implicit def ordering = Ordering.by((v: Value) => v.index)
  }

  /**
   * A list of all the registered instances of this type.
   */
  private var _values = List.empty[E]
  def values: List[E] = _values

  /**
   * A map of all registered instances of this type, keyed by the persistable token.
   */
  private var _valueMap = Map.empty[String, E]
  def valueMap: Map[String, E] = _valueMap
}
