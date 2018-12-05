package io.underscore.ynapdemo.combine

//import cats.implicits._

object Main extends App {

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

  /*
  Exercise:
  The cutting edge SuperAdder v3.5a-32 is the world’s first choice for adding
  together numbers. The main function in the program has signature

  def add(items: List[Int]): Int

  In a tragic accident this code is deleted! Rewrite the method and save the day!
   */

  /*
  Exercise:
  Well done!
    SuperAdder’s market share continues to grow, and now
    there is demand for additional functionality. People now want to add
    List[Option[Int]] . Change add so this is possible. The SuperAdder code
    base is of the highest quality, so make sure there is no code duplication!
   */

  /*
  Exercise:
  SuperAdder is entering the POS (point-of-sale, not the other POS) market.
  Now we want to add up Orders:
    case class Order(totalCost: Double, quantity: Double)
  We need to release this code really soon so we can’t make any modifications
  to add. Make it so!
   */
}
