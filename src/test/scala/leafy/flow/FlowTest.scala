package leafy.flow

import org.scalatest._

import akka.actor.Props

import leafy.core
import leafy.analysis._

import scala.concurrent.ExecutionContext.Implicits.global

class FlowTest extends FlatSpec with Matchers {

  "Running a flow" should "process all analysis engines" in {
    val flow0 = Flow.run("Yolo ok", Props[WhitespaceTokenizer], Props[DumbAnalysisEngine])
    val flow1 = Flow.run("Yolo potaotes", Props[WhitespaceTokenizer], Props[WhitespaceTokenizer], Props[WhitespaceTokenizer])

    flow1.map(x => println(x))
    flow0.map(x => println(x))
  }
}
