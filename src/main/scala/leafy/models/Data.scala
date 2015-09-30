package leafy.models

trait Data
case class Annotation(begin: Int, end: Int, text: String) extends Data
