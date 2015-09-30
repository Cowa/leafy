package leafy.flow

import org.scalatest._

import akka.actor.{Props, ActorSystem}

import leafy.models.Start
import leafy.analysis.{WhitespaceTokenizer, DumbAnalysisEngine}

class FlowTest extends FlatSpec with Matchers {

  "Running a flow" should "process all analysis engines" in {
    implicit val system = ActorSystem()
    val flow0 = Flow(Props[WhitespaceTokenizer], Props[DumbAnalysisEngine])

    val flow1 = Flow(Props[WhitespaceTokenizer], Props[WhitespaceTokenizer], Props[WhitespaceTokenizer])

    flow0 ! Start("Yolo ok")
    flow1 ! Start("Yolo potatoes")
  }
}
