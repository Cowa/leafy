package leafy.analysis

import akka.actor.Actor
import leafy.models.{Process, ProcessDone, Bucket}

trait AnalysisEngine extends Actor {
  def process(b: Bucket): Bucket

  def receive = {
    case Process(b) => sender ! ProcessDone(process(b))
  }
}
