package leafy.analysis

import org.scalatest._

import leafy.models.{Annotation, Bucket}

class AnalysisEngineTest extends FlatSpec with Matchers {

  def fixture = new {
    val bucket = Bucket(List())
    val simple = new SimpleAnalysisEngineFixture
  }

  "AnalysisEngine" should "process and return a bucket" in {
    val f = fixture
    val result = f.simple.process(f.bucket)

    assert(result === Bucket(List(Annotation(1, 2, "yolo"))))
  }
}
