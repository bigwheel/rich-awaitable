package com.github.bigwheel.util

import scala.concurrent.{Await, Awaitable}
import scala.concurrent.duration.Duration

package object richawaitable {

  implicit class RichAwaitable[T](awaitable: Awaitable[T]) {

    /**
     * Alias of Await.result
     */
    def safeResult(implicit atMost: Duration): T =
      Await.result(awaitable, atMost)

    /**
     * Alias of Await.ready
     */
    def safeReady(implicit atMost: Duration): Awaitable[T] =
      Await.ready(awaitable, atMost)
  }
}
