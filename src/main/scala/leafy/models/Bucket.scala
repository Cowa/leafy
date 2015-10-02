package leafy.models

object Bucket {
  def apply(source: String): Bucket = Bucket(source, List())
}

case class Bucket(source: String, data: List[Data]) {
  import scala.reflect.ClassTag

  def add(d: Data) = Bucket(source, data :+ d)
  def add(d: List[Data]) = Bucket(source, data ++ d)
  def select[T: ClassTag] = data.collect({case t: T => t})
  def merge(b: Bucket) = Bucket(source, (data ++ b.data).distinct)
}
