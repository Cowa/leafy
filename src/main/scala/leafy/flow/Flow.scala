package leafy.flow

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import leafy.models._

import scala.concurrent.Future
import scala.concurrent.duration._

object Flow {
  def run(source: String, engines: Props*)(implicit s: ActorSystem): Future[Bucket] = {
    val flow = s.actorOf(Props(new Flow(engines)))
    flow.ask(Start(source))(Timeout(5 hours)).mapTo[Bucket]
  }
}

class Flow(var engines: Seq[Props]) extends Actor with ActorLogging {
  var origin: ActorRef = ActorRef.noSender

  def receive = {
    case Start(source) =>
      origin = sender()
      log.info("Flow started")
      processNext(Bucket(source))

    case ProcessDone(b) if engines.nonEmpty =>
      log.info("Process next...")
      processNext(b)

    case ProcessDone(b) =>
      log.info("All work done")
      origin ! b
  }

  def processNext(b: Bucket) = {
    context.actorOf(engines.head) ! Process(b)
    engines = engines.drop(1)
  }
}
