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

import scala.concurrent.ExecutionContext.Implicits.global

import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.injected

import dao.PlayerDao
import dates.Now
import json.Json._
import play.api.mvc._
import securesocial.core.SecureSocial
import securesocial.core.java.SecureSocial.UserAwareAction
import stats.SnapshotsFactory
import stats.StatsFactory

/**
 * The controller used to serve the main Rokta page.
 * @author alex
 *
 */
class HomeController extends Controller with SecureSocial {

  def index = UserAwareAction { implicit request =>
    val username = request.user.map(_.fullName)
    Ok(views.html.index(username))
  }

}