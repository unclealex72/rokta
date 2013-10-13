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

import org.joda.time.DateTime
import org.specs2.mutable.Specification
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import model.PersistedGame
import model.PersistedPlayer
import scala.collection.immutable.SortedSet
import filter.BetweenGameFilter
import filter.SinceGameFilter
import dates.DaysAndTimes._
import filter.UntilGameFilter
import filter.YearGameFilter
import filter.MonthGameFilter
import filter.DayGameFilter
import model.Game
import model.PersistedGame._
/**
 * @author alex
 *
 */
class SquerylDaoSpec extends Specification {

  val DAYS_IN_YEAR : Int = 365
  
  /**
   * Wrap tests with database creation and transactions
   */
  def txn[B](block: SquerylDao => Seq[PersistedGame] => B) = {

    import dao.RoktaSchema._
    import dao.EntryPoint._

    Class forName "org.h2.Driver"
    SessionFactory.concreteFactory = Some(() =>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:mem:", "", ""),
        new H2Adapter))
    inTransaction {
      RoktaSchema.create
      val freddie  = PersistedPlayer(0, "Freddie", "freddie@queen.com", "BLACK")
      freddie.save
      
      val squerylDao = new SquerylDao
      val gameFactory: DateTime => PersistedGame = dt => PersistedGame(freddie, dt)
      val games: SortedSet[PersistedGame] = (0 until DAYS_IN_YEAR * 2).foldLeft(SortedSet.empty[PersistedGame]){ (games, days) =>
        val dt = new DateTime(2013, 1, 1, 10, 0, 0).plusDays(days)
        val early = gameFactory(dt)
        val late = gameFactory(dt.plusHours(1))
        games + early + late
      }
      block(squerylDao)(games.toSeq)
    }
  }

  def matchFilter(filter: PersistedGame => Boolean)(implicit games: Seq[PersistedGame]) = contain(exactly(games.filter(filter): _*)).inOrder
  
  def playedOn(days: Day*): PersistedGame => Boolean = { game => days.foldLeft(false){(pred, day) =>
    pred || {
        val month = day.month
        val year = month.year
        game.datePlayed.getYear() == year.year && 
        game.datePlayed.getMonthOfYear() == month.month && 
        game.datePlayed.getDayOfMonth() == day.day
      }
    }
  }
  
  "Getting games with no filters at all" should {
    "return all games" in txn { squerylDao => implicit games =>
      squerylDao.games(None) must matchFilter(g => true)
    }
  } 

  "Getting games between two dates" should {
    "return all games between those two dates" in txn { squerylDao => implicit games =>
      squerylDao.games(
        Some(BetweenGameFilter(January(31, 2013).at(0, 0), February(2, 2013).at(23, 59)))) must matchFilter(
          playedOn(January(31, 2013), February(1, 2013), February(2, 2013)))
    }
  } 

  "Getting games since a date" should {
    "return all games between since that date" in txn { squerylDao => implicit games =>
      squerylDao.games(
        Some(SinceGameFilter(December(29, 2014).at(0, 0)))) must matchFilter(
          playedOn(December(29, 2014), December(30, 2014), December(31, 2014)))
    }
  }

  "Getting games until a date" should {
    "return all games between since that date" in txn { squerylDao => implicit games =>
      squerylDao.games(
        Some(UntilGameFilter(January(3, 2013).at(23, 59)))) must matchFilter(
          playedOn(January(1, 2013), January(2, 2013), January(3, 2013)))
    }
  }

  "Getting games for a year" should {
    "return all games in that year" in txn { squerylDao => implicit games =>
      squerylDao.games(
        Some(YearGameFilter(2013))) must matchFilter(g => g.datePlayed.getYear() == 2013)
    }
  }

  "Getting games for a month" should {
    "return all games in that month" in txn { squerylDao => implicit games =>
      squerylDao.games(
        Some(MonthGameFilter(2013, 2))) must matchFilter(g => g.datePlayed.getYear() == 2013 && g.datePlayed.getMonthOfYear() == 2)
    }
  }

  "Getting games for a day" should {
    "return all games in that day" in txn { squerylDao => implicit games =>
      squerylDao.games(
        Some(DayGameFilter(2013, 7, 6))) must matchFilter(
            g => g.datePlayed.getYear() == 2013 && g.datePlayed.getMonthOfYear() == 7 && g.datePlayed.getDayOfMonth() == 6)
    }
  }
}