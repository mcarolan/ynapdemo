package io.underscore.ynapdemo.polypattern

object PolyPattern extends App {

  sealed trait Animal {
    def makeNoise: String =
      this match {
        case Cow     => "Moo"
        case Chicken => "I am an animal"
      }
  }

  case object Cow extends Animal
  case object Chicken extends Animal

  val animal: Animal = Cow
  val noise: String = animal.makeNoise

    println(noise)


  /*
  take away points:
  * structural recursion using polymorphic dispatch
  * structural recursion using pattern matching (in trait, and in object)
  *
  * exercises (page 74)
  * Traffic Lights
  * Calculation
   */

  sealed trait TrafficLight

  object TrafficLight {
    def next(trafficLight: TrafficLight): TrafficLight =
      trafficLight match {
        case Red    => ???
        case Green  => ???
        case Yellow => ???
      }
  }

  final case object Red extends TrafficLight
  final case object Green extends TrafficLight
  final case object Yellow extends TrafficLight


  sealed trait Calculation
  final case class Success(result: Int) extends Calculation
  final case class Failure(reason: String) extends Calculation

  object Calculator {
    def +(calculation: Calculation, int: Int): Calculation =
      calculation match {
        case Success(result) => Success(result + int)
        case failure => failure
      }

    def -(calculation: Calculation, int: Int): Calculation =
      calculation match {
        case Success(result) => Success(result - int)
        case failure => failure
      }

    def /(calculation: Calculation, int: Int): Calculation = {
      calculation match {
        case Success(result) =>
          if (int == 0)
            Failure("Division by zero")
          else
            Success(result / int)
        case failure => failure
      }
    }
  }

  assert(Calculator.+(Success(1), 1) == Success(2))
  assert(Calculator.-(Success(1), 1) == Success(0))
  assert(Calculator.+(Failure("Badness"), 1) == Failure("Badness"))








  /*
  case study:
  recursively defined ADT
  the implementation of a list:
    * sum implementation
    * mob on product implementation
    * individually: length
    * individually: double
   */

  /*
  define an int list
  1, 2, 3
  5, 6, 29, 34589, 28
   */


  sealed trait IntList

  object IntList {
    def sum(list: IntList): Int =
      list match {
        case Elem(value, next) => value + sum(next)
        case End               => 0
      }

    def product(list: IntList): Int =
      list match {
        case Elem(value, next) => value * sum(next)
        case End               => 1
      }

    def length(list: IntList): Int =
      list match {
        case Elem(_, next)      => 1 + length(next)
        case End               => 0
      }

    def double(list: IntList): IntList =
      list match {
        case Elem(value, next) => Elem(value * 2, double(next))
        case end => end
      }

  }

  case class Elem(value: Int, next: IntList) extends IntList
  case object End extends IntList

  val list1 = Elem(5, Elem(6, Elem(29, Elem(34589, Elem(28, End)))))
  val list2 = Elem(1, Elem(2, Elem(3, End)))

  assert(IntList.length(list2) == 3)
  println(IntList.double(list2))


  /*
  exercise:
  The Forest of Trees
  ADT, sum and double
   */

}
