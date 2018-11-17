package io.underscore.ynapdemo.adt

import java.time.LocalDate

import scala.xml.Elem


object Main extends App {

  /*
  Take home points:
  * Classes are templates for relating objects, traits are templates for relating classes
  * Trait declaration and usage
  *
  * Exercises:
  * Shaping Up With Traits (page 62)
  *
   */

  trait User {
    def id: String
  }

  case class AnonymousUser(id: String, timeLastSeen: LocalDate) extends User

  case class RegisteredUser(id: String, email: String, timeLastSeen: LocalDate, fullName: String) extends User

  val anonymousUser1 = AnonymousUser("njdzfbiadznh", LocalDate.now().minusMonths(1))

  val anonymousUser2 = AnonymousUser("4759862689jngiowreiohnrwoie", LocalDate.now().minusMonths(6))

  val registeredUser1 = RegisteredUser("83j0889aern h98a", "bob@ynap.com", LocalDate.now(), "Bob Smith")

  def generateAuditEvent(user: User): Elem =
    <auditEvent>
      <id>{user.id}</id>
    </auditEvent>

  println(generateAuditEvent(registeredUser1))

  sealed trait Shape {
    def sides: Int
    def area: Double
    def perimeter: Double
  }

  case class Circle(radius: Int) extends Shape {
    override def sides: Int = 0

    def area: Double = Math.PI * (radius * radius)

    override def perimeter: Double = 2 * Math.PI * radius
  }

  case class Rectangle(width: Int, height: Int) extends Shape {
    override def sides: Int = 4

    def area: Double = width * height

    override def perimeter: Double = 2 * width + 2 * height
  }

  case class Square(size: Int) extends Shape {
    override def sides: Int = 4

    def area: Double = size * size

    override def perimeter: Double = 4 * size
  }

  /*
  Take home points:
  * sealed traits and final classes allow us to restrict extension
  * the compiler then can help us when pattern matching
  *
  * Exercises:
  * Printing Shapes (page 64)
  * A short division exercise (page 65)
   */

  def talkAboutTheArea(shape: Shape): String =
    shape match {
      case circle@Circle(_) =>
        s"The area is ${circle.area}"
      case rect@Rectangle(_, _) =>
        s"The area is ${rect.area}"
      case square@Square(_) =>
        s"The area is ${square.area}"
    }

  println(talkAboutTheArea(Circle(10)))
  println(talkAboutTheArea(Rectangle(10, 2)))


  object Draw {

    def apply(shape: Shape): String =
      shape match {
        case Circle(radius)           => s"This is a circle of radius $radius"
        case Rectangle(width, height) => s"This is a rectangle ${width}x${height}"
        case Square(size)             => s"This is a square of size $size"
      }

  }

  println(Draw(Circle(10)))
  println(Draw(Rectangle(10, 2)))

  sealed trait DivisionResult // algebraic data type, sum type
  case object Infinite extends DivisionResult
  case class Finite(answer: Int) extends DivisionResult // product type

  object divide {
    def apply(numerator: Int, denominator: Int): DivisionResult =
      if (denominator == 0)
        Infinite
      else
        Finite(numerator / denominator)
  }

  def describe(divisionResult: DivisionResult): String =
    divisionResult match {
      case Infinite     => "You are being ridiculous"
      case Finite(answer) => s"Your answer is $answer"
    }

  val divisionResult1: DivisionResult = divide(1, 0)
  val divisionResult2: DivisionResult = divide(1, 2)

  println(describe(divisionResult1))
  println(describe(divisionResult2))

  /*
  Take home points:
  * algebraic data types use the product and sum type patterns
  * product type patterns model a "has-a" shape
  * sum type patterns model a "is-a" shape
  *
  * Exercises:
  * Stop on a dime (page 68)
  * Calculator (page 68)
  * Water, water, everywhere (page 68)
   */

}
