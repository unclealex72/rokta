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
import play.api.mvc.Result
import play.api.mvc.Request
import play.api.mvc.AnyContent
import play.api.mvc.Headers
import play.api.http.HeaderNames
import play.api.mvc.Results.NotModified
import com.typesafe.scalalogging.slf4j.Logging
import scala.concurrent.Future
import play.api.mvc.SimpleResult
import play.api.mvc.Controller
import scala.concurrent.ExecutionContext
import dao.Transactional
import play.api.libs.concurrent.Execution.Implicits._

/**
 * A trait for controllers that allows for ETag headers to be queried and a 304 No Content to be returned if
 * the resource has not changed.
 */
trait Etag extends Controller {

  def calculateETag(tx: Transactional): String = tx { personDao => gameDao =>
    gameDao.lastGamePlayed.map(dt => dt.getMillis().toString()).getOrElse("none")
  }

  def ETag[A](action: Action[A])(implicit tx: Transactional): Action[A] = ETag(calculateETag(tx))(action)

  def ETag[A](etag: String)(action: Action[A]): Action[A] =
    Action.async(action.parser) { implicit request =>
    val quotedETag = '"' + etag + '"'
    val modified = request.headers.get(HeaderNames.IF_NONE_MATCH) match {
      case None => {
        true
      }
      case Some(etag) => {
        etag != quotedETag
      }
    }
    val response: Future[SimpleResult] = 
    if (modified) {
      action(request)
    } else {
      Future(NotModified)
    }
    response.map(_.withHeaders(HeaderNames.ETAG -> quotedETag))
  }

}