package leafy.analysis

import Function.tupled
import leafy.models.{Annotation, Bucket}

object WhitespaceTokenizer extends AnalysisEngine {
  def process(b: Bucket): Bucket = {
    b.add(b.source.split(" ").zipWithIndex.map (tupled((x, i) => Annotation(i, x.length, x))).toList)
  }
}
