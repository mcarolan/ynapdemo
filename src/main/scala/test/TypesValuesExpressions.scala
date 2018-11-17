package test

object ValueTypeExpression extends App {

  /*
take home points:
  * (literal) expressions have types and produce values
  * types restrict allowed values
  * Scala has a static type system

  exercises page 22
 */

  val fruit: String = "Banana"
  val loudFruit: String = "Banana".toUpperCase

  println(fruit)
  println(loudFruit)

  /*
take home points:
  * all values in Scala are objects: groups of related data and methods with an identity
  * object method call syntax - infix / postfix
  * object literal syntax, with fields and method (specific cat example)

  exercises page 33
 */

  val meaningOfLife: Int = 42

  println(fruit take 10)

  object Person {
    val name: String = "Martin"
    val heights: Int = 2
    val weight: Int = 60

    def talk(): Unit =
      println("scala")
  }

  println(Person.name)

  object Oswald {
    val colour: String = "Black"
    val food: String = "Milk"
  }

  object Henderson {
    val colour: String = "Ginger"
    val food: String = "Chips"
  }

  object Quentin {
    val colour: String = "Tabby and white"
    val food: String = "Curry"
  }

  /*
take home points:
  * write method declaration to identify input and output types, using ??? for the body
  * write a test case
  * write the body (block syntax)

  exercises page 39
 */

  /*
take home points:
  * classes are templates for creating objects (convert specific cat example). highlight relatedness.
  * constructors allow us to pass values into the template
  * Scala's type hierarchy - Any, AnyRef, AnyVal, Nothing, Null, Unit

  exercises page 45
 */

  class Cat(val colour: String, val food: String)

  val oswald: Cat = new Cat("White", "Milk")
  val henderson: Cat = new Cat("Tabby and White", "Chips")
  val quentin: Cat = new Cat("Ginger", "Curry")

  println(oswald.food)
  println(henderson.food)


  class StrayCat(colour: String, food: String, val diseases: Set[String]) extends Cat(colour, food) {

    override def toString: String =
      s"This is a cat with ${diseases.mkString(", ")}"

  }

  val myCat = new StrayCat("Black", "Eggs", Set("Rabies", "Dissentry"))

  println(myCat.toString)


  def findCat(): Cat =
    throw new RuntimeException("I don't have a cat, I have a dog")

  findCat()

  /*
take home points:
  * function application syntax for objects (class Timestamp(val seconds: Long) with apply)
  * companion objects allow things to be associated with no particular instance of a class
  * case classes: fields, toString, equals/hashCode, copy. companion object: apply

  exercises page 50
 */

  /*
take home points:
  * pattern matching a case class instance
  * names, literals and underscores in a match expression
  * can be used to transform values into other values of different type

  exercises page 57
 */

}
