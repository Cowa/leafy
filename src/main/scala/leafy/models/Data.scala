package leafy.models

sealed trait Data
case class Annotation(begin: Int, end: Int, text: String) extends Data
