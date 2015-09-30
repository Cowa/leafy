package leafy.analysis

import leafy.models.{Annotation, Bucket}

object SimpleAnalysisEngineFixture extends AnalysisEngine {
  def process(b: Bucket): Bucket = {
    b.add(Annotation(1, 2, "yolo"))
  }
}
