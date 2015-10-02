package leafy.analysis

import leafy.models.{Annotation, Bucket}

/**
 * Only for test purposes ;)
 */
class DumbAnalysisEngine extends AnalysisEngine {
  def process(b: Bucket): Bucket = {
    b.add(Annotation(1, 2, "yolo"))
  }
}
