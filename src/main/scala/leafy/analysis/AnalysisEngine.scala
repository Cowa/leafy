package leafy.analysis

import leafy.models.{Process, ProcessDone, Bucket}

import akka.actor.{Actor, ActorLogging}

trait AnalysisEngine extends Actor with ActorLogging {
  def process(b: Bucket): Bucket

  def receive = {
    case Process(b) =>
      log.info(s"${this.getClass.getSimpleName} processing...")
      sender() ! ProcessDone(process(b))
  }
}
