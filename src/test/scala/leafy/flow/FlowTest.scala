package leafy.flow

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import leafy._
import leafy.analysis._
import leafy.models.{Annotation, Bucket}

class FlowTest extends FlatSpec with Matchers with ScalaFutures {

  "Running a flow" should "process all analysis engines" in {
    val flow0 = Flow("Yolo ok", AE[WhitespaceTokenizer], AE[DumbAnalysisEngine])

    whenReady(flow0) { x =>
      assert(x === Bucket("Yolo ok", List(Annotation(0,4,"Yolo"), Annotation(5,7,"ok"), Annotation(1,2,"yolo"))))
    }
  }

  "Combining multiple flow" should "be possible" in {
    val flow0 = Flow("Yolo ok", AE[WhitespaceTokenizer])
    val flow1 = Flow.combine(flow0, AE[WhitespaceTokenizer], AE[DumbAnalysisEngine])

    whenReady(flow1) { x =>
      assert(x === Bucket("Yolo ok", List(Annotation(0,4,"Yolo"), Annotation(5,7,"ok"), Annotation(0,4,"Yolo"), Annotation(5,7,"ok"), Annotation(1,2,"yolo"))))
    }
  }
}
