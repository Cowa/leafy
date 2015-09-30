package leafy.flow

import akka.actor._
import leafy.models.{Start, Bucket, Process, ProcessDone}

object Flow {
  def pipeline(engines: Props*)(implicit system: ActorSystem): ActorRef = system.actorOf(Props(new Flow(engines)))
  def run(flow: ActorRef, source: String) = flow ! Start(source)
}

class Flow(var engines: Seq[Props]) extends Actor with ActorLogging {

  def receive = {
    case Start(source) =>
      log.info("Flow started")
      processNext(Bucket(source))

    case ProcessDone(b) if engines.nonEmpty =>
      log.info("Process next...")
      processNext(b)

    case ProcessDone(b) =>
      log.info("All work done")
  }

  def processNext(b: Bucket) = {
    context.actorOf(engines.head) ! Process(b)
    engines = engines.drop(1)
  }
}
