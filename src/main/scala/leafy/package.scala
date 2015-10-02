import akka.actor.ActorSystem

package object leafy {
  implicit val core = ActorSystem()
}
