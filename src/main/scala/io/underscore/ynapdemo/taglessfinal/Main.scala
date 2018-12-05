package io.underscore.ynapdemo.taglessfinal

import cats.data.EitherT

import scala.concurrent.Future

object Main extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  type FEither[T] = EitherT[Future, String, T]
  object FEither {
    def apply[T](value: Future[Either[String, T]]): FEither[T] =
      EitherT(value)
  }

  case class User(username: String, password: String, loginCounter: Int)

  var allowedUser = User("admin", "letmein", 0)

  trait AuthenticationDao {
    def findPassword(username: String): FEither[Option[String]]
    def getLoginCount(username: String): FEither[Int]
    def setLoginCount(username: String, value: Int): FEither[Unit]
  }

  object SlickAuthenticationDao extends AuthenticationDao {
    override def findPassword(username: String): FEither[Option[String]] =
      FEither(
        Future {
          if (username == allowedUser.username)
            Right(Some(allowedUser.password))
          else
            Right(None)
        })

    override def getLoginCount(username: String): FEither[Int] =
      if (username == allowedUser.username)
        FEither(Future.successful(Right(allowedUser.loginCounter)))
      else
        FEither(Future.successful(Left("Woah, login count for undefined user")))

    override def setLoginCount(username: String, value: Int): FEither[Unit] =
      if (username == allowedUser.username)
        FEither(Future.successful(Right({
          allowedUser = allowedUser.copy(loginCounter = value)
          ()
        })))
      else
        FEither(Future.successful(Left("Woah, login count for undefined user")))
  }

  class Authenticator(authenticationDao: AuthenticationDao) {
    def login(username: String, password: String): FEither[Boolean] =
      ???
  }


}
