package leafy.models

object Bucket {
  def apply(source: String): Bucket = Bucket(source, List())
}

case class Bucket(source: String, data: List[Data]) {
  def add(d: Data) = Bucket(source, data :+ d)
  def add(d: List[Data]) = Bucket(source, data ++ d)
}
