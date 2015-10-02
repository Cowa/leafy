package leafy.models

import org.scalatest._

case class DataTypeTest(b: Boolean) extends Data

class BucketTest extends FlatSpecLike with Matchers {

  "Bucket select method" should "return all the specified data type" in {
    val b = Bucket("Ok", List(Annotation(0, 2, "Ok"), Annotation(2, 4, "Ko"), DataTypeTest(true)))

    assert(b.select[Annotation] === List(Annotation(0, 2, "Ok"), Annotation(2, 4, "Ko")))
  }
}
