/**
 * Copyright 2013 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
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
 * @author unclealex72
 *
 */
package model

import org.squeryl.KeyedEntity

/**
 * The representation of a Rokta player.
 */
case class Person(
  /**
   * The synthetic ID of this element.
   */
  val id: Long,
  /**
   * The player's name.
   */
	val name: String,
	/**
	 * The Google email the player uses to log in.
	 */
  val email: String,
  /**
   * The name of the colour used to represent the player's results in any graphs.
   */
	val _colour:  String) extends KeyedEntity[Long] {
  
  def color: Colour = Colour(_colour).getOrElse({ 
    throw new IllegalStateException(s"${_colour} is not a valid colour.") })
}
