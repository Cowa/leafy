package leafy.analysis

import akka.actor.{Actor, ActorLogging}
import leafy.models.{Process, ProcessDone, Bucket}

trait AnalysisEngine extends Actor with ActorLogging {
  def process(b: Bucket): Bucket

  def receive = {
    case Process(b) =>
      log.info(s"${this.getClass.getSimpleName} processing...")
      sender ! ProcessDone(process(b))
  }
}
