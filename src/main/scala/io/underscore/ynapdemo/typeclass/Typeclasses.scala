package io.underscore.ynapdemo.typeclass

import scala.concurrent.Future

object Typeclasses extends App {

  sealed trait Json
  case class JsonString(value: String) extends Json
  case class JsonNumber(value: Double) extends Json
  case class JsonObject(fields: Map[String, Json]) extends Json
  case object JsonNull extends Json

  case class ReportingParameters(resourceType: String, source: Int)

  sealed trait Store
  case object MrPorter extends Store
  case object NetAPorter extends Store

  object Store {
    def buildAJson(store: Store): Json =
      store match {
        case MrPorter   => JsonString("MrPorter")
        case NetAPorter => JsonString("NetAPorter")
      }
  }

  val name: String = "Martin"


  object MessagePublisher {

    def publish(store: Store): Future[Unit] = ???

    def publish(store: ReportingParameters): Future[Unit] = ???

    def publish(store: String): Future[Unit] = ???


  }

    /*
    Motivation
    The 3 parts:
      Typeclass definition
    */

    trait JsonWritable[T] {
      def write(value: T): Json
    }


   /*
    Typeclass instances
   */

    object JsonWritable {
      implicit val reportingParametersJsonWritable: JsonWritable[ReportingParameters] =
        (value: ReportingParameters) => JsonObject(Map("resourceType" -> JsonString(value.resourceType), "source" -> JsonNumber(value.source)))
    }

    object Blah {
      implicit val stringJsonWritable: JsonWritable[String] =
        (value: String) => JsonString(value)
    }

   /*
    Typeclass interfaces: interface objects
   */

    object Json {
      def apply[T](value: T)(implicit jsonWritable: JsonWritable[T]): Json =
        jsonWritable.write(value)
    }

    val reportingParameters: ReportingParameters = ReportingParameters(resourceType = "wibble", source = 42)
    val reportingParametersJson: Json = Json(reportingParameters)

  /*
    Typeclass interfaces: implicit syntax
   */

  implicit class JsonWritableSyntax[T](val value: T) extends AnyVal {

    def toJson(implicit jsonWritable: JsonWritable[T]): Json =
      jsonWritable.write(value)

  }

  val reportingParameters2: ReportingParameters = ReportingParameters(resourceType = "wibble", source = 42)
  val reportingParametersJson2: Json = reportingParameters2.toJson

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
