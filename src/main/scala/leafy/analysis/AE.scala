package leafy.analysis

import scala.reflect.ClassTag
import akka.actor.{Actor, Props}

object AE {
  // AE[MyActor] equivalent to Props[MyActor]
  def apply[T <: Actor: ClassTag]: Props = Props[T]
}
