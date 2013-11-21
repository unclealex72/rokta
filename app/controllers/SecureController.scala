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

import com.escalatesoft.subcut.inject.injected
import com.escalatesoft.subcut.inject.AutoInjectable
import dao.Transactional
import securesocial.core.SecureSocial
import securesocial.core.Authorization
import securesocial.core.Identity
import com.escalatesoft.subcut.inject.Injectable
import com.typesafe.scalalogging.slf4j.Logging
import json.JsonResults

/**
 * A class that can be used as a base for controllers that require a valid user to be logged in.
 * @author alex
 *
 */
abstract class SecureController(_tx: Option[Transactional] = injected) extends SecureSocial 
with Authorization with Injectable {

  val tx = injectIfMissing(_tx)
  
  /**
   * Check to see if a user has a valid email address.
   */
  def validUser(email: String): Boolean = tx { playerDao => gameDao => 
    playerDao.allPlayers.exists(_.email == Some(email))
  }
  
  def isAuthorized(user: Identity) = {
    user.email match {
      case Some(email) => {
        user.identityId.providerId == "google" && validUser(email)
      }
      case None => false
    }
  }

  /**
   * Authorised actions require a valid google user.
   */
  def AuthorisedAjaxAction[A] = SecuredAction[A](true, this)
  
  /**
   * A simple callback that can be used to check if a user is authenticated. It returns 401 and 403 as expected
   * and 204 if the current user is authenticated.
   */
  def checkAuthorisation = AuthorisedAjaxAction { implicit request =>
    NoContent
  }
}