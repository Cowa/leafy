import akka.actor.{Props, Actor, ActorSystem}

package object leafy {
  implicit lazy val core = ActorSystem()

  object AE {
    import scala.reflect.ClassTag

    // Alias for Props[MyActor]
    def apply[T <: Actor: ClassTag]: Props = Props[T]
  }
}
