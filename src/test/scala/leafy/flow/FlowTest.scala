package leafy.flow

import org.scalatest._

import akka.actor.{Props, ActorSystem}

import leafy.analysis.{WhitespaceTokenizer, DumbAnalysisEngine}

class FlowTest extends FlatSpec with Matchers {

  "Running a flow" should "process all analysis engines" in {
    implicit val system = ActorSystem()
    val flow0 = Flow.pipeline(Props[WhitespaceTokenizer], Props[DumbAnalysisEngine])
    val flow1 = Flow.pipeline(Props[WhitespaceTokenizer], Props[WhitespaceTokenizer], Props[WhitespaceTokenizer])

    Flow.run(flow0, "Yolo ok")
    Flow.run(flow1, "Yolo potatoes")
  }
}
