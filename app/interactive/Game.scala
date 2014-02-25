package interactive

import akka.actor.Actor
import play.api.libs.iteratee.{Enumerator, Iteratee, Concurrent}
import play.api.libs.concurrent.Execution.Implicits._
import dao.Transactional
import stats.ExemptPlayerFactory
import model.{Player, UploadableGame, Hand}
import dates.Now

/**
 * Created by alex on 18/02/14.
 */
class Game(val tx: Transactional, val exemptPlayerFactory: ExemptPlayerFactory, val now: Now) extends Actor {

  val (enumerator, channel) = Concurrent.broadcast[Message]

  /**
   * Small fish here, so just store state as an in-memory list.
   */
  var state: List[State] = List(NotStarted)

  def alterState(newStates: List[State]) {
    state = newStates
    channel.push(CurrentState(state.head))
  }

  def addState(newState: State) {
    if (newState != state.head) {
      alterState(newState :: state)
    }
  }

  override def receive = (action: Any) => action.asInstanceOf[Action] match {
    case Hello => {
      val iteratee = Iteratee.foreach[IncomingMessage] { message =>
        state.head.consume(message) match {
          case Left(newState) => addState(newState)
          case Right(action) => self ! action
        }
      }
      sender ! (iteratee, Enumerator[Message](CurrentState(state.head)) >>> enumerator)
    }
    case Start(instigator) => {
      addState(WaitingForPlayers(exemptPlayerFactory().map(_.name), instigator))
    }
    case Undo => {
      if (state.nonEmpty) {
        alterState(state.tail)
      }
    }
    case Quit => {
      if (state.headOption != Some(NotStarted)) {
        alterState(List(NotStarted))
      }
    }
    case UploadGame(instigator, players, rounds) => tx { playerDao => gameDao =>
      val allPlayers = playerDao.allPlayers.groupBy(_.name).mapValues(_.head)
      def player: String => Player = name => allPlayers.get(name).getOrElse(
        throw new IllegalArgumentException(s"name is not a valid player's email address.")
      )
      val uploadableGame =
        UploadableGame(
          player(instigator),
          players.toList.map(player),
          rounds.toList.map { round =>
            round.foldLeft(Map.empty[Player, Hand])((phs, ph) => phs + (player(ph._1) -> ph._2))
          })
      gameDao.uploadGame(now(), uploadableGame)
    }
  }
}

/**
 * The trait for actions that the game actor can perform upon.
 */
sealed trait Action

/**
 * Tell the actor that a new player has logged in.
 * @param player
 */
case object Hello extends Action

/**
 * The action used to start a new game.
 */
case class Start(instigator: String) extends Action
/**
 * Tell the actor to upload the game to the database.
 */
case class UploadGame(instigator: String, players: Set[String], rounds: Vector[Map[String, Hand]]) extends Action

/**
 * Tell the actor to undo the last state.
 */
case object Undo extends Action

/**
 * Tell the actor to quit the current game.
 */
case object Quit extends Action
