/*
 * Copyright 2014 Alex Jones
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
 * specific language governing permissions and limitation
 * under the License.
 */

import securesocial.core.{LoginEvent, Event, EventListener}
import play.api.mvc.{Session, RequestHeader}
import play.api.Application

/**
 * An event listener that will update a player's avatar when they log in.
 * Created by alex on 29/03/14.
 */
class AvatarEventListener(app: Application) extends EventListener {
  override def id: String = "avatar_event_listener"

  def onEvent(event: Event, request: RequestHeader, session: Session): Option[Session] = {
    event match {
      case e: LoginEvent => {
        val user = e.user
        user.avatarUrl.foreach { avatarUrl =>
        }
      }
      case _ => // Do nothing
    }
    None
  }
}