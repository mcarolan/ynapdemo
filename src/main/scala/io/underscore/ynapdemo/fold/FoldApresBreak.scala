package io.underscore.ynapdemo.fold

object Fold extends App {

  /*
  Generic types!

  * A generic result type
  *
  * Exercises:
  * Implement LinkedList, which has the same structure as IntList but can hold any type of element
  * Implement length for LinkedList
  * Can you implement sum and product for LinkedList?
  * Implement contains for LinkedList: the contains method takes a value,
  *   and returns a boolean indicating whether this value is in the list
   */


  object GenericResultTypeOnSuccess {
    sealed trait Result[T]
    final case class Success[T](value: T) extends Result[T]
    final case class Failure[T](reason: String) extends Result[T]

    val boom: Result[String] = Success[String]("hi")
    val calc: Result[Int]    = Success(123)
  }

  /*
  Back to IntList:
  Implement fold
  Implement sum, length, product in terms of fold
  Why can't we implement double in terms of fold?
  Change fold so that we can implement double
   */

//  sealed trait LinkedList[T] {
//
//    def fold[U](intListElem: (T, U) => U)(intListEnd: U): U =
//      this match {
//        case IntListElem(value, next) => intListElem(value, next.fold(intListElem)(intListEnd))
//        case IntListEnd()               => intListEnd
//      }
//
//
//
//    def length: Int =
//      fold((_, _) => 1)(0)
//  }
//
//  case class IntListElem[T](value: T, next: LinkedList[T]) extends LinkedList[T]
//  case class IntListEnd[T]() extends LinkedList[T]
//
//  val list = IntListElem(1, IntListElem(2, IntListEnd()))

//  val animals = IntListElem("Dog", IntListElem("Fish", IntListEnd()))

  /*
  Exercise:
    Implement fold for our generic LinkedList

    ****
    * For an algebraic datatype A , fold converts it to a generic type B . Fold is a structural recursion with:
    * one function parameter for each case in A
    * each function takes as parameters the fields for its associated class
    * if A is recursive, any function parameters that refer to a recursive field take a parameter of type B
    *
    * The right-hand side of pattern matching cases, or the polymorphic methods as appropriate, consists of
calls to the appropriate function.
    ***
   */


  /*
  Exercise:
  * Make your Tree implementation generic!
  * Implement fold for your tree
  *
  * Using fold convert the following tree to a string that says "To iterate is human, to reduce divine":
  * Node(Node(Leaf("To"), Leaf("iterate")),
  *   Node(Node(Leaf("is"), Leaf("human,")),
  *     Node(Leaf("to"), Node(Leaf("recurse"), Leaf("divine")))))
   */

  sealed trait Tree
  case class Node(left: Tree, right: Tree) extends Tree
  case class Leaf(value: Int) extends Tree

  /*
  * Recap product and sum types
  * Example product type: IntAndString
  * Exercise: implement a generic Pair
  * Tuples
  * Example sum type: Result
  * Exercise: implement a more generic result Sum, with a Left and Right subtype
  *
  *
  * 5.4.4.1 Exercise: Maybe that Was a Mistake
  *   Create a generic trait called Maybe of a generic type A with two subtypes,
  *   Full containing an A, and Empty containing no value.
  *
  Sum type: Maybe

  Full is-a Maybe has-a A
  Empty is-a Maybe

  * 5.4.6.2 Exercise: Folding maybe
  *   Implement fold for maybe
  * Exercise: Implement fold for Mauybe
   */


  sealed trait Maybe[T] {
    def fold[B](full: T => B, empty: B): B =
      this match {
        case Full(value) => full(value)
        case Empty()     => empty
      }

    def map[B](f: T => Maybe[B]): Maybe[B] = ???
  }
  case class Full[T](value: T) extends Maybe[T]
  case class Empty[T]() extends Maybe[T]

  case class IntAndString(intValue: Int, stringValue: String)

  val martinA = IntAndString(180, "Martin")

  case class Pair[A, B](first: A, second: B)

  val maritinB: Pair[Int, String] = Pair(10, "Martin")
  val maritinC: Pair[String, String] = Pair("Martin", "String")

  /*

  Sum type: Result

  Result is-a Success
    Success has-a A

  Result is-a Failure
    Failure has-a B
   */


  sealed trait Result[A, B]
  case class Success[A, B](value: A) extends Result[A, B]
  case class Failure[A, B](reason: B) extends Result[A, B]

  val martinD: Result[String, Int] = Success("String")
  val martinE: Result[Int, String] = Success(21343)


  /*
  * Introduce map (diagram)
  * Implement for LinkedList
   */

  final case class User(userId: Int, username: String)

  sealed trait LinkedList[T] {

    def map[U](f: T => U): LinkedList[U] =
      this match {
        case LinkedListElement(elem, next) => LinkedListElement(f(elem), next.map(f))
        case LinkedListEnd()               => LinkedListEnd[U]()
      }

  }
  case class LinkedListElement[T](elem: T, next: LinkedList[T]) extends LinkedList[T]
  case class LinkedListEnd[T]() extends LinkedList[T]

  val users: LinkedList[User] = LinkedListElement(User(1, "Martin"), LinkedListElement(User(2, "Denis"), LinkedListEnd()))

  val userIds: LinkedList[Int] = users.map(u => u.userId)

  /*
  * Introduce flatMap (diagram)
  * Implement for Maybe
   */
//  final case class Order(orderId: Int)
//  def firstOrder(user: User): Maybe[Order] =
//    ???
//  def lookupUser(userId: Int): Maybe[User] =
//    ???
//
//  val userId = 123
//  val firstOrder: Maybe[Order] = ???

  /*
  Terms: functor and monad
  Take away point: they allow computations to be sequenced, run one after another but stopping on failure

  Exercises:
  Implement map for LinkedList
  Use it with a LinkedList[Int] to:
    Produce a new list with each element doubled
    Produce a new list with each element incremented

   * Implement flatMap for Maybe
   * Implement map for Maybe
   * Try to implement map only using flatMap

   Using the following Scala list:
   val numbers: List(1, 2, 3)
   compute a new list, containing each number and its negation (order does not matter)
   val numbersAndNegation: List[Int] = ???

   From the following Scala list:
   val list = List(Maybe(1), Maybe(2), Maybe(3))
   compute a new list, which contains Empty if the number is odd, and the number in a Full if it is even


   Implement  map and flatMap for your Sum type
   */
}
