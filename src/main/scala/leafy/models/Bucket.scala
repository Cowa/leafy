package leafy.models

case class Bucket(data: List[Data]) {
  def add(d: Data) = Bucket(data :+ d)
}

sealed trait Data

case class Annotation(begin: Int, end: Int, text: String) extends Data
