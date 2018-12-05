package io.underscore.ynapdemo.typeclass

import scala.concurrent.Future

object Typeclasses extends App  {

  sealed trait Json
  case class JsonString(value: String) extends Json
  case class JsonNumber(value: Double) extends Json
  case class JsonObject(fields: Map[String, Json]) extends Json
  case object JsonNull extends Json

  object Json {
    def apply[A](value: A)(implicit jsonWritable: JsonWritable[A]): Json =
      jsonWritable.writeJson(value)
  }


  case class ReportingParameters(source: String, productType: String, customerNumber: Int)
  object ReportingParameters {

    implicit val ReportingParametersJsonWriteable: JsonWritable[ReportingParameters] =
      (value: ReportingParameters) => JsonObject(
        Map(
          "source" -> JsonString(value.source),
          "productType" -> JsonString(value.productType),
          "customerNumber" -> JsonNumber(value.customerNumber)
        )
      )

  }

  implicit class JsonWritableSyntax[A](val value: A)(implicit jsonWritable: JsonWritable[A]) {

    def asJson: Json =
      jsonWritable.writeJson(value)

  }

  def numberToJson(number: Int): Json = JsonNumber(number)

  val reportingParameters = ReportingParameters("MRPORTER", "COAT", 123)
  val reportingParametersJson: Json = reportingParameters.asJson
  val meaningOfLifeAsJson: Json = 42.asJson


  val reportingParametersOptJson: Json = (Some(reportingParameters): Option[ReportingParameters]).asJson

  trait QueuePublisher {

    def publish(json: Json): Future[Unit]

  }

  trait JsonWritable[-A] {
    def writeJson(value: A): Json
  }

  object JsonWritable {

    implicit val StringJsonWritable : JsonWritable[String] = (value: String) => JsonString(value)

    implicit val IntJsonWritable: JsonWritable[Int] = (value: Int) => JsonNumber(value)

    implicit def OptionJsonWritable[A](implicit jsonWritable: JsonWritable[A]): JsonWritable[Option[A]] =
      (value: Option[A]) => value.fold[Json](JsonNull)(jsonWritable.writeJson)

  }


//  implicit def intToBoolean(int: Int): Boolean =
//    if (int == 10) true else false
//
//  val boolean: Boolean = 42

    /*
    Motivation
    The 3 parts:
      Typeclass definition
      Typeclass instances

      Typeclass interfaces: interface objects
      Typeclass interfaces: implicit syntax
   */

  //interface object usage
  val jsonString: Json = Json("hello")
  val jsonString2: Json = "hello".asJson


  val blah = implicitly[JsonWritable[Int]]

  /*
  Debugging implicits: implicitly
   */


  /*
  Implicit resolution:
    by placing them in an arbitrary object ;
    by placing them in a trait;
    by placing them in the companion object of the type class;
    by placing them in the companion object of the parameter type.
   */

  /*
  Implicit methods (Option example)
  Warning: avoid implicit conversions
   */


  /*
  Variance
  Invariant - Maybe example
  Covariant
  Contravariant

  Invariance in cats:
    * Impact - option show
    * Why
    * How to work with it
   */

  sealed trait Fruit
  case class Apple() extends Fruit
  case class Pear() extends Fruit

  implicit val MixedFruitWritable: JsonWritable[Fruit] = (value: Fruit) => value match {
    case Apple() => JsonString("Apple")
    case Pear()  => JsonString("Pear")
  }

  val fruit: Apple = Apple()
  val fruitAsJson: Json = new JsonWritableSyntax[Apple](fruit)(MixedFruitWritable).asJson

  import cats.implicits._

  val name = "Bob".some
  println(name.show)

  /*
  Contravariance
  F[Fruit] is a sub type of F[Apple] if Apple is a sub type of fruit
   */

  /*
  Invariance
  F[Fruit] has no relation to an F[Apple] even though Apple is a sub type of fruit
   */


  /*
  Exercise:

  Define a type class Printable[A] containing a single method format.
  format should accept a value of type A and return a String

  Create an object PrintableInstances containing instances of
   Printable for String and Int

  Define an object Printable with two generic interface methods:
    format accepts a value of type A and a Printable of the corresponding type.
    It uses the relevant Printable to convert the A to a String.

    print accepts the same parameters as format and returns Unit.
    It prints the A value to the console using println.

  Create a Printable instance for:
    case class Cat(name: String, age: Int, color: String)
  Write a program that uses Printable to print a Cat object to the console

  Let's create some better syntax:
    1. Create an object called PrintableSyntax.
    2. Inside PrintableSyntax define an implicit class PrintableOps[A] to wrap up a value of type A.
    3. In PrintableOps define the following methods:
      format accepts an implicit Printable[A] and returns a String representation of the wrapped A
      print accepts an implicit Printable[A] and returns Unit. It prints the wrapped A to the console

  Use the extension methods to print the example Cat you created in the previous exercise.
   */

  /*
  Introduction to Show typeclass in Cats
  cats imports: all, instances, syntax

  Exercise - reimplement your solution using Show instead of Printable!
   */

  /*
  Introduction to the Eq typeclass in Cats

  Motivation
  Usage

  Exercise:
  Implement an instance of Eq for our running Cat example
  Use this to compare the following pairs of objects for equality and inequality:
  val cat1 = Cat("Garfield", 38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]
   */
}
