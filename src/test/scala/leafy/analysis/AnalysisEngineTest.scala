package leafy.analysis

import org.scalatest._

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}

import leafy.models.{Annotation, Bucket}

class AnalysisEngineTest extends TestKit(ActorSystem("testSystem")) with FlatSpecLike with Matchers {

  "AnalysisEngine" should "process and return a bucket" in {
    val engine = TestActorRef[DumbAnalysisEngine].underlyingActor

    assert(engine.process(Bucket("Touté ko")) === Bucket("Touté ko", List(Annotation(1, 2, "yolo"))))
  }

  "WhitespaceTokenizer" should "cut words on whitespace" in {
    val engine = TestActorRef[WhitespaceTokenizer].underlyingActor

    assert(engine.process(Bucket("Touté ko")) === Bucket("Touté ko", List(Annotation(0, 5, "Touté"), Annotation(6, 8, "ko"))))
  }
}
