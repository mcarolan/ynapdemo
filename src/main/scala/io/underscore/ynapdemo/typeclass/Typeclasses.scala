package io.underscore.ynapdemo.typeclass

object Typeclasses extends App {

  sealed trait Json
  case class JsonString(value: String) extends Json
  case class JsonNumber(value: Double) extends Json
  case class JsonObject(fields: Map[String, Json]) extends Json
  case object JsonNull extends Json

}
