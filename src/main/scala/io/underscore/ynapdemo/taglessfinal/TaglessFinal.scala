package io.underscore.ynapdemo.taglessfinal

import cats.MonadError
import cats.data.EitherT

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import cats._
import cats.implicits._

object Attempt1 {
  type FEither[T] = EitherT[Future, String, T]

  object Calculator {

    def division(a: Int, b: Int): FEither[Int] =
      if (b != 0)
        EitherT[Future, String, Int](Future.successful(Right(a / b)))
      else
        EitherT[Future, String, Int](Future.successful(Left(s"Division of $a by 0")))

  }

  assert(Await.result(Calculator.division(10, 2).value, 10.seconds) == Right(5))
  assert(Await.result(Calculator.division(10, 0).value, 10.seconds).isLeft)

  val calculation: FEither[Int] = Calculator.division(1, 2)
}

object Attempt2 extends App {
  /*
  Important, ensure an implicit executioncontext is available -
  or you'll get an implicit resolution failure trying to find a
  MonadError instance
   */
  import scala.concurrent.ExecutionContext.Implicits.global

  type FEither[T] = EitherT[Future, String, T]

  class Calculator[F[_]](implicit F: MonadError[F, String]) {

    def division(a: Int, b: Int): F[Int] =
      if (b != 0)
        F.point(a / b)
      else
        F.raiseError(s"Division of $a by 0")

    def add(a: F[Int], b: F[Int]): F[Int] =
      F.flatMap(a)(a => F.map(b)(b => a + b))

  }

  object Calculator {
    def apply[F[_]](implicit F: MonadError[F, String]): Calculator[F] =
      new Calculator[F]
  }

  assert(Await.result(Calculator[FEither].division(10, 2).value, 10.seconds) == Right(5))
  assert(Await.result(Calculator[FEither].division(10, 0).value, 10.seconds).isLeft)

  val calculation: FEither[Int] = Calculator[FEither].division(1, 2)
}

object Attempt3 extends App {
  /*
  Important, ensure an implicit executioncontext is available -
  or you'll get an implicit resolution failure trying to find a
  MonadError instance
   */
  import scala.concurrent.ExecutionContext.Implicits.global

  type FEither[T] = EitherT[Future, String, T]
  type Test[T] = Either[String, T]

  class Calculator[F[_]](implicit F: MonadError[F, String]) {

    def division(a: Int, b: Int): F[Int] =
      if (b != 0)
        F.point(a / b)
      else
        F.raiseError(s"Division of $a by 0")

    def add(a: F[Int], b: F[Int]): F[Int] =
      F.flatMap(a)(a => F.map(b)(b => a + b))

    def multiply(fa: F[Int], fb: F[Int]): F[Int] =
      for {
        a <- fa
        b <- fb
      }
        yield a * b

  }

  object Calculator {
    def apply[F[_]](implicit F: MonadError[F, String]): Calculator[F] =
      new Calculator[F]
  }

  assert(Calculator[Test].division(10, 2) == Right(5))
  assert(Calculator[Test].division(10, 0).isLeft)

  val calculation: FEither[Int] = Calculator[FEither].division(1, 2)
}
