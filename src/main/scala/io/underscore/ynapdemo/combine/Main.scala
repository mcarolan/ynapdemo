package io.underscore.ynapdemo.combine

import cats.kernel.Monoid

//import cats.Monoid

//import cats.implicits._



object Main extends App {

//  trait Semigroup[A] {
//    def combine(a: A, b: A): A
//  }
//
//  trait Monoid[A] extends Semigroup[A] {
//    def identity: A
//  }
//
//  object AdditionMonoid extends Monoid[Int] {
//    override def combine(a: Int, b: Int): Int = a + b
//    override def identity: Int = 0
//  }
//
//  object StringMonoid extends Monoid[String] {
//    override def combine(a: String, b: String): String = a + b
//
//    override def identity: String = ""
//  }
//
//  def lists[A]: Monoid[List[A]] = new Monoid[List[A]] {
//    override def combine(a: List[A], b: List[A]): List[A] = a ++ b
//    override def identity: List[A] = List.empty[A]
//  }

  /*
  associativity

  forall a, b, c
   */

//  StringMonoid.combine(a, StringMonoid.combine(b, c)) == StringMonoid.combine(StringMonoid.combine(a, b) c)

  /*
  commutivity:
  forall a, b
   */

//  StringMonoid.combine(a, b) == StringMonoid.combine(b, a)

  /*
  identity law

   */

//  val x: Int = ???
//  AdditionMonoid.combine(x, AdditionMonoid.identity) == x

  /*
  Ints:
  Addition
  Multiplication

  Strings

   */

  /*
  Monoids and semigroups

  Motivation -
    combining values of the same type
   + integers
   + strings
   + containers

   Definition of a monoid:
   + associative operation
   + zero/identity value
   - what operation over integers is _not_ a monoid?

   Semigroups:
   + something with a combine operation but no identity
   + example of where useful

   */

  /*
  Exercise:
  What monoids and semigroups are there for sets?
   */

  /*
  Interacting with monoid instances in cats:
  * companion object
  * |+| syntax:
    On option
    On map
   */

//  val intMonoid: Monoid[String] = Monoid[String]

  import cats.implicits._

  val combinedInts: Int = 1 |+| 2 |+| 3
  val combinedStrings: String = "hello " |+| "world"

  val shoppingBasketA: Map[String, Int] = Map("apple" -> 3)
  val shoppingBasketB: Map[String, Int] = Map("apple" -> 2, "banana" -> 1)

  println(shoppingBasketA ++ shoppingBasketB) // Map("apple" -> 2, "banana" -> 1)
  println(shoppingBasketA |+| shoppingBasketB) // Map("apple" -> 5, "banana" -> 1)


  val optA: Option[String] = Some("Hello ")
  val opt: Option[String] = None
  val optB: Option[String] = Some("world")

  println(optA |+| opt |+| optB)

  /*
  Exercise:
  The cutting edge SuperAdder v3.5a-32 is the world’s first choice for adding
  together numbers. The main function in the program has signature

  def add(items: List[Int]): Int

  In a tragic accident this code is deleted! Rewrite the method and save the day!
   */

  object Exercise1 extends App {
    import cats.implicits._

    def add(items: List[Int]): Int =
      items.combineAll

  }

  /*
  Exercise:
  Well done!
    SuperAdder’s market share continues to grow, and now
    there is demand for additional functionality. People now want to add
    List[Option[Int]] . Change add so this is possible. The SuperAdder code
    base is of the highest quality, so make sure there is no code duplication!
   */

  object Exercise2 extends App {
    import cats.implicits._

    def add(items: List[Option[Int]]): Option[Int] =
      items.combineAll

  }

  /*
  Exercise:
  SuperAdder is entering the POS (point-of-sale, not the other POS) market.
  Now we want to add up Orders:
    case class Order(totalCost: Double, quantity: Double)
  We need to release this code really soon so we can’t make any modifications
  to add. Make it so!
   */

  object Exercise3 extends App {
    import cats.implicits._

    case class Order(totalCost: Double, quantity: Double)

    object Order {
      implicit val OrderMonoid: Monoid[Order] = new Monoid[Order] {
        override def empty: Order = Order(0, 0)

        override def combine(x: Order, y: Order): Order = Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
      }
    }

    def add(items: List[Order]): Order =
      items.combineAll

  }
}
