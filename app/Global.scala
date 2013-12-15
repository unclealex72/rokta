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

import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import org.squeryl.adapters.PostgreSqlAdapter
import org.squeryl.internals.DatabaseAdapter
import com.escalatesoft.subcut.inject.Injectable
import com.typesafe.scalalogging.slf4j.Logging
import controllers._
import play.api.Application
import play.api.GlobalSettings
import play.api.db.DB
import play.api.mvc.Controller
import scala.Some

/**
 * The [[GlobalSettings]] used to set up Squeryl and Subcut
 * @author alex
 *
 */
object Global extends GlobalSettings with Logging {

 object Context extends Injectable {
    implicit val bindingModule = RoktaBindingModule  // use the standard config by default

    var controllers = Map.empty[Class[_], Any]
    def register[T <: Controller](implicit m: scala.reflect.Manifest[T]): Unit = {
      controllers += m.runtimeClass -> inject(m)
    }
    
    register[StatsController]
    register[HomeController]
    register[PlayersController]
    register[ColoursController]
    register[NewGameController]
  }

  /**
   * Controllers must be resolved through the bindings. There is a special method of GlobalSettings
   * that we can override to resolve a given controller. This resolution is required by the Play router.
   */
  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    val controller = Context.controllers.get(controllerClass)
    controller match {
      case Some(controller) => controller.asInstanceOf[A]
      case _ => throw new RuntimeException(s"$controllerClass is not a valid controller class.")
    }
  }
  
  override def onStart(app: Application) {
    logger info "Setting up database access."
    // Set up Squeryl database access
    SessionFactory.concreteFactory = app.configuration.getString("db.default.driver") match {
      case Some("org.h2.Driver") => Some(() => getSession(new H2Adapter, app))
      case Some("org.postgresql.Driver") => Some(() => getSession(new PostgreSqlAdapter, app))
      case _ => sys.error("Database driver must be either org.h2.Driver or org.postgresql.Driver")
    }
  }

  def getSession(adapter: DatabaseAdapter, app: Application) = Session.create(DB.getConnection()(app), adapter)

}