package leafy.flow

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import leafy.models._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

object Flow {
  def apply(source: String, engines: Props*)(implicit s: ActorSystem): Future[Bucket] = start(Bucket(source), engines)

  def start(bucket: Bucket, engines: Seq[Props])(implicit s: ActorSystem): Future[Bucket] =
    s.actorOf(Props(new Flow(engines))).ask(Start(bucket))(Timeout(24 hours)).mapTo[Bucket]

  def branch(bucket: Future[Bucket], engines: Props*)(implicit s: ActorSystem): Future[Bucket] =
    bucket.flatMap(b => start(b, engines))

  def merge(buckets: Future[Bucket]*): Future[Bucket] =
    Future.sequence(buckets).map(_.foldLeft(Bucket(""))((acc, b) => acc.merge(b)))
}

class Flow(var engines: Seq[Props]) extends Actor {
  var origin: ActorRef = ActorRef.noSender

  def receive = {
    case Start(b) =>
      origin = sender()
      processNext(b)
    case ProcessDone(b) if engines.nonEmpty => processNext(b)
    case ProcessDone(b) => origin ! b
  }

  def processNext(b: Bucket) = {
    context.actorOf(engines.head) ! Process(b)
    engines = engines.drop(1)
  }
}
