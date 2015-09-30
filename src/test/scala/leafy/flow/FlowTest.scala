package leafy.flow

import org.scalatest._

import leafy.models.{Annotation, Bucket}
import leafy.analysis.{WhitespaceTokenizer, SimpleAnalysisEngineFixture}

class FlowTest extends FlatSpec with Matchers {

  "Running a flow" should "process all analysis engines" in {
    val result = Flow.run("Touté ko", WhitespaceTokenizer, SimpleAnalysisEngineFixture)

    assert(result === Bucket("Touté ko", List(Annotation(0, 5, "Touté"), Annotation(6, 8, "ko"), Annotation(1,2,"yolo"))))
  }
}
