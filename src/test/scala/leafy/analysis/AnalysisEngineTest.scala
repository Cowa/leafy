package leafy.analysis

import org.scalatest._

import leafy.models.{Annotation, Bucket}

class AnalysisEngineTest extends FlatSpec with Matchers {

  def fixture = new {
    val bucket = Bucket("Touté ko")
  }

  "AnalysisEngine" should "process and return a bucket" in {
    val f = fixture
    val result = new DumbAnalysisEngine().process(f.bucket)

    assert(result === Bucket("Touté ko", List(Annotation(1, 2, "yolo"))))
  }

  "WhitespaceTokenizer" should "cut words on whitespace" in {
    val f = fixture
    val result = new WhitespaceTokenizer().process(f.bucket)

    assert(result === Bucket("Touté ko", List(Annotation(0, 5, "Touté"), Annotation(6, 8, "ko"))))
  }
}
