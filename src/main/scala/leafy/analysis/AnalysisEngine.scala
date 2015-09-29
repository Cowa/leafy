package leafy.analysis

import leafy.models.Bucket

trait AnalysisEngine {
  def process(b: Bucket): Bucket
}
