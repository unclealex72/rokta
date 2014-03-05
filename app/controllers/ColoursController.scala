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
package controllers

import play.api.mvc.Action
import json.JsonResults
import model.Colour
import com.escalatesoft.subcut.inject._
import dates.Now
import dao.Transactional

/**
 * Present all known colours to the client.
 */
class ColoursController(_tx: Option[Transactional] = injected, _now: Option[Now] = injected) extends Etag with JsonResults with AutoInjectable {

  val tx = injectIfMissing(_tx)
  val now = injectIfMissing(_now)

  /**
   * Treat the colours as constant from installation time.
   */
  val nowEtag: String = now().toString

  def colours = ETag(nowEtag) {
    Action { request => json(Map("colours" -> Colour.values)) }
  }
}
