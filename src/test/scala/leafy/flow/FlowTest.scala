package leafy.flow

import leafy.models.Bucket
import org.scalatest._

import akka.actor.Props

import leafy.core
import leafy.analysis._

import scala.concurrent.ExecutionContext.Implicits.global

class FlowTest extends FlatSpec with Matchers {

  "Running a flow" should "process all analysis engines" in {
    val flow0 = Flow.run("Yolo ok", WhitespaceTokenizer(), Props[DumbAnalysisEngine])
    val flow1 = Flow.run("Yolo potaotes", WhitespaceTokenizer(), WhitespaceTokenizer(), WhitespaceTokenizer())

    flow0.mapTo[Bucket].map(x => println(x))
    flow1.mapTo[Bucket].map(x => println(x))
  }
}
