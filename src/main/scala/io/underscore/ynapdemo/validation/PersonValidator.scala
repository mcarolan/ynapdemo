package io.underscore.ynapdemo.validation

import cats.data.ValidatedNel
import cats.implicits._

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

/*
validation using option
validation using either (sequencing)
validation using validatednel (applicative)

build a validator for orders entered using the command line

an order has:
- an ID, which is a positive integer
- a store, which is Mr Porter or Net a Porter. This could be a string or an ADT
- quantity, which is a positive integer

the order should  be printed out if it is valid, otherwise all validation failures should be printed out
 */

case class Person(name: String, numberOfPets: Int)

object PersonValidatorOption extends App {

  /*
  this implementation fails fast, and doesn't say why
   */

  type Result[T] = Option[T]

  def nonEmptyString(input: String): Result[String] =
    if (input.nonEmpty)
      Some(input)
    else
      None

  def positiveInteger(number: String): Result[Int] =
    Try(number.toInt) match {
      case Failure(exception) => None
      case Success(value) if value >= 0 => Some(value)
      case _ => None
    }

  val nameString: String = StdIn.readLine("Name: ")
  val numberOfPetsString: String = StdIn.readLine("Number of pets: ")

  val result: Result[Person] =
    for {
      name <- nonEmptyString(nameString)
      numberOfPets <- positiveInteger(numberOfPetsString)
    }
      yield Person(name, numberOfPets)

  println(result)

}

object PersonValidatorEither extends App {

  /*
  this implementation fails fast, but at least says why
   */

  type Result[T] = Either[String, T]

  def nonEmptyString(input: String): Result[String] =
    if (input.nonEmpty)
      Right(input)
    else
      Left("empty string")

  def positiveInteger(number: String): Result[Int] =
    Try(number.toInt) match {
      case Failure(exception) => Left("that isn't a number")
      case Success(value) if value >= 0 => Right(value)
      case _ => Left("you entered a negative number")
    }

  val nameString: String = StdIn.readLine("Name: ")
  val numberOfPetsString: String = StdIn.readLine("Number of pets: ")

  val result: Result[Person] =
    for {
      name <- nonEmptyString(nameString)
      numberOfPets <- positiveInteger(numberOfPetsString)
    }
      yield Person(name, numberOfPets)

  println(result)

}


object PersonValidatorValidated extends App {

  /*
  this implementation does not fail fast, and says why. it's awesome.
   */

  type Result[T] = ValidatedNel[String, T]

  def nonEmptyString(input: String): Result[String] =
    if (input.nonEmpty)
      input.validNel
    else
      "empty string".invalidNel

  def positiveInteger(input: String): Result[Int] =
    Try(input.toInt) match {
      case Failure(_) => "isn't a number".invalidNel
      case Success(value) if value >= 0 => value.validNel
      case _ => "you entered a negative number".invalidNel
    }

  val nameString: String = StdIn.readLine("Name: ")
  val numberOfPetsString: String = StdIn.readLine("Number of pets: ")

  val result: Result[Person] =
    (nonEmptyString(nameString), positiveInteger(numberOfPetsString)).mapN(Person)

  println(result)

}

object Main extends App {
  sealed trait TrafficLight
  case class LightColour(base: String) extends TrafficLight
  case class DarkColour(base: String) extends TrafficLight
  class Black() extends DarkColour("Black")

  val light: TrafficLight = new Black()

  light match {
    case LightColour(base) => println(s"dark $base")
    case DarkColour(base) => println(s"dark $base")
  }
}