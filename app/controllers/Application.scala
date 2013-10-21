package controllers

import play.api._
import play.api.mvc._
import java.io.StringWriter
import java.io.PrintWriter
import org.squeryl.SessionFactory
import org.squeryl.adapters.PostgreSqlAdapter

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def schema = Action {
    import dao.RoktaSchema
    import dao.RoktaSchema._
    import dao.EntryPoint._
    
    Class forName "org.postgresql.Driver"
    SessionFactory.concreteFactory = Some(() =>
      org.squeryl.Session.create(
        java.sql.DriverManager.getConnection("jdbc:postgresql://localhost/rokta", "rokta", "r0tk@"),
        new PostgreSqlAdapter))

    val ddl = inTransaction {
      val buffer = new StringWriter
      RoktaSchema.printDdl(new PrintWriter(buffer))
      buffer.toString
    }
    Ok(ddl)
  }
}