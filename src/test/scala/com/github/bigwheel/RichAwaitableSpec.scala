package com.github.bigwheel

import org.scalatest.{FunSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class RichAwaitableSpec extends FunSpec with Matchers {

  it ("normal await") {
    val futureInt = Future(42)

    Await.result(futureInt, Duration.Inf) should be(42)
  }

  it ("normal ready") {
    var maybeInt: Int = 0 // caution! this is not smart code
    val setIntFuture = Future { maybeInt = 42 }

    Await.ready(setIntFuture, Duration.Inf)
    maybeInt should be(42)
  }

  it ("rich await") {
    val futureInt = Future(42)

    import com.github.bigwheel.util.richawaitable._
    futureInt.safeResult(Duration.Inf) should be(42)
  }

  it ("rich ready") {
    var maybeInt: Int = 0 // caution! this is not smart code
    val setIntFuture = Future { maybeInt = 42 }

    import com.github.bigwheel.util.richawaitable._
    setIntFuture.safeReady(Duration.Inf)
    maybeInt should be(42)
  }

  it ("rich await with implicit atMost") {
    val futureInt = Future(42)

    import com.github.bigwheel.util.richawaitable._
    implicit val atMost: Duration = Duration.Inf
    futureInt.safeResult should be(42)
  }

  it ("rich ready implicit atMost") {
    var maybeInt: Int = 0 // caution! this is not smart code
    val setIntFuture = Future { maybeInt = 42 }

    import com.github.bigwheel.util.richawaitable._
    implicit val atMost: Duration = Duration.Inf
    setIntFuture.safeReady
    maybeInt should be(42)
  }

}
