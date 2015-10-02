import akka.actor.{Props, Actor, ActorSystem}

import scala.reflect.ClassTag

package object leafy {
  implicit lazy val core = ActorSystem()

  object AE {
    // Alias for Props[MyActor]
    def apply[T <: Actor: ClassTag]: Props = Props[T]
  }
}
