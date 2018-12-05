package io.underscore.ynapdemo.taglessfinal

import scala.concurrent.Future

object Main extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  case class User(username: String, password: String, loginCounter: Int)

  trait AuthenticationDao {
    def findPassword(username: String): Future[Option[String]]
    def getLoginCount(username: String): Future[Int]
    def setLoginCount(username: String, value: Int): Future[Unit]
  }

  class SoapAuthenticationConnector extends AuthenticationDao {
    var allowedUser = User("admin", "letmein", 0)

    override def findPassword(username: String): Future[Option[String]] =
      Future {
        if (username == allowedUser.username)
          Some(allowedUser.password)
        else
          None
      }

    override def getLoginCount(username: String): Future[Int] =
      if (username == allowedUser.username)
        Future.successful(allowedUser.loginCounter)
      else
        Future.failed(new RuntimeException("Woah, login count for undefined user"))

    override def setLoginCount(username: String, value: Int): Future[Unit] =
      if (username == allowedUser.username)
        Future {
          allowedUser = allowedUser.copy(loginCounter = value)
          ()
        }
      else
        Future.failed(new RuntimeException("Woah, login count for undefined user"))
  }

  class Authenticator(authenticationDao: AuthenticationDao) {
    def login(username: String, password: String): Future[Boolean] =
      ???
  }


  /*
  Exercise

  The big number calculation service will use cloud computing to do large computations that may fail

  The specification you've been given for this implementation is

  division by zero should give a failed future

   */

  type Result[T] = Future[T]

  trait CalculationService {
    def number(value: Int): Result[Int]
    def add(a: Result[Int], b: Result[Int]): Result[Int]
    def divide(a: Result[Int], b: Result[Int]): Result[Int]
    def plus10DividedBy2Plus12(a: Result[Int]): Result[Int]
  }

  /*
  Build an object that implements this trait

  Write a main method that waits for the result of plus10DividedBy2Plus12(number(10)) and prints it to the console
  use Await.result to wait on the result
   */

  /*
  People have complained it's hard to write a test for something that returns Future[Int]
  Convert your object to a class that uses monadic abstraction
  Write a test on an instance of the class constructed with TestResult:

  type TestResult[T] = Either[Throwable, T]
   */
}