package leafy.flow

import leafy.models.Bucket
import leafy.analysis.AnalysisEngine

object Flow {
  def run(source: String, engines: AnalysisEngine*): Bucket = {
   val bucket = Bucket(source)

    engines.foldLeft(bucket)((acc, e) => e.process(acc))
  }
}
