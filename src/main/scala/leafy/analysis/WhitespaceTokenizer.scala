package leafy.analysis

import leafy.models.{Annotation, Bucket}

class WhitespaceTokenizer extends AnalysisEngine {
  def process(b: Bucket): Bucket = {
    var cursor = 0
    var tokens = List[Annotation]()

    b.source.split(" ").foreach { x =>
      tokens = tokens :+ Annotation(cursor, cursor + x.length, x)
      cursor = cursor + x.length + 1
    }

    b.add(tokens)
  }
}
