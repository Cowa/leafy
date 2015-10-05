package benchmark

import leafy._
import leafy.analysis._
import leafy.flow._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

object Benchmark {
  def main(args: Array[String]) {
    val source = Source.fromURL(getClass.getResource("/bench.txt")).getLines().mkString(" ")

    val t0 = System.nanoTime
    val m0 = Flow(source, AE[WhitespaceTokenizer])
    val m1 = Flow(source, AE[WhitespaceTokenizer])
    val m2 = Flow(source, AE[WhitespaceTokenizer])
    val m3 = Flow(source, AE[WhitespaceTokenizer])

    for {
      x <- m0
      y <- m1
      z <- m2
      w <- m3
    } {
      val t1 = System.nanoTime
      println(s"Done in: ${(t1 - t0) / 1e9}s")
      core.shutdown()
    }
  }
}
