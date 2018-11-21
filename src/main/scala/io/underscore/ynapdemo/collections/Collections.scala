package io.underscore.ynapdemo.collections

object Collections extends App {

  /*
  Intro: building a Seq (apply and ::)

  Create a Seq containing the String s "cat" , "dog" , and "penguin" . Bind it to the name animals.
  Append the element "tyrannosaurus" to animals and prepend the element "mouse" .

  apply
  head
  headOption
  tail

   */

  case class Film(
    name: String,
    yearOfRelease: Int,
    imdbRating: Double)

  case class Director(
    firstName: String,
    lastName: String,
    yearOfBirth: Int,
    films: Seq[Film])

  val memento = new Film("Memento", 2000, 8.5)
  val darkKnight = new Film("Dark Knight", 2008, 9.0)
  val inception = new Film("Inception", 2010, 8.8)

  val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7)
  val outlawJoseyWales = new Film("The Outlaw Josey Wales", 1976, 7.9)
  val unforgiven = new Film("Unforgiven", 1992, 8.3)
  val granTorino = new Film("Gran Torino", 2008, 8.2)
  val invictus = new Film("Invictus", 2009, 7.4)

  val predator = new Film("Predator", 1987, 7.9)
  val dieHard = new Film("Die Hard", 1988, 8.3)
  val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6)
  val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8)

  val eastwood = new Director("Clint", "Eastwood", 1930,
    Seq(highPlainsDrifter, outlawJoseyWales, unforgiven, granTorino, invictus))

  val mcTiernan = new Director("John", "McTiernan", 1951,
    Seq(predator, dieHard, huntForRedOctober, thomasCrownAffair))

  val nolan = new Director("Christopher", "Nolan", 1970,
    Seq(memento, darkKnight, inception))

  val someGuy = new Director("Just", "Some Guy", 1990,
    Seq())

  val directors = Seq(eastwood, mcTiernan, nolan, someGuy)

  /*
    Accept a parameter numberOfFilms of type Int —find all directors who have directed more than
numberOfFilms

    Accept a parameter year of type Int —find a director who was born before that year

    Accept two parameters, year and numberOfFilms, and return a list of directors who were born before
year who have also directed more than than numberOfFilms

    Accept a parameter ascending of type Boolean that defaults to true . Sort the directors by age in the
specified order

   */

  /*
  Other notable things about sequencing collections:
    Concept: foldLeft vs foldRight
    Concept: foreach
   */


  /*
  Nolan Films:
  Staring with the definiton of nolan , create a list containing the names of the films directed by Christopher
Nolan.

  Cinephile:
  Startng with the definiton of directors , create a list containing the names of all films by all directors.

  McTiernan:
  Startng with mcTiernan, find the date of the earliest McTiernan film.

  High Score Table:
  Starting with directors, find all films sorted by descending IMDB rating:
  Starting with directors again, find the average score across all films:

  Tonight's listings:
  Starting with directors, print the following for every film: "Tonight only! FILM NAME by DIRECTOR!"

  From the archives:
  Finally, starting with directors again, find the earliest film by any director:
   */


  /*
  Minimum:
  Write a method to find the smallest element of a Seq[Int].

  Unique:
  Given Seq(1, 1, 2, 4, 3, 4) create the sequence containing each number only once. Order is not impor-
tant, so Seq(1, 2, 4, 3) or Seq(4, 3, 2, 1) are equally valid answers. Hint: Use contains to check if a
sequence contains a value.

  Reverse:
  Write a func on that reverses the elements of a sequence. Your output does not have to use the same concrete
implementation as the input. Hint: use foldLeft .

  map:
  Write map in terms of foldRight.

  fold left:
  Write your own implementation of foldLeft that uses foreach and mutable state
   */


  /*

   For comprehensions:
    syntactic sugar for map / flatMap
    patterns, generators


  Options
  Write a method addOptions that accepts two parameters of type Option[Int] and adds them together. Use
a for comprehension to structure your code.

  Write a second version of your code using map and flatMap instead of a for comprehension.

  Overload addOptions with another implementation that accepts three Option[Int] parameters and adds
them all together.

  Write a second version of your code using map and flatMap instead of a for comprehension.
   */

}
