package leafy.flow

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import leafy.core
import leafy.analysis._
import leafy.models.{Annotation, Bucket}

import scala.concurrent.ExecutionContext.Implicits.global

class FlowTest extends FlatSpec with Matchers with ScalaFutures {

  "Running a flow" should "process all analysis engines" in {
    val flow0 = Flow.run("Yolo ok", AE[WhitespaceTokenizer], AE[DumbAnalysisEngine])
    val flow1 = Flow.run("Yolo potaotes", AE[WhitespaceTokenizer], AE[WhitespaceTokenizer], AE[WhitespaceTokenizer])

    flow1.map(x => println(x))
    flow0.map(x => println(x))

    whenReady(flow0) { x =>
      assert(x === Bucket("Yolo ok", List(Annotation(0,4,"Yolo"), Annotation(5,7,"ok"), Annotation(1,2,"yolo"))))
    }
  }
}
