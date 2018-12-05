import Dependencies._

lazy val root = (project in file(".")).
  settings(
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "ynapdemo",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.typelevel" %% "cats-core" % "1.4.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3",
    scalacOptions ++= Seq(
      "-encoding", "UTF-8",   // source files are in UTF-8
      "-deprecation",         // warn about use of deprecated APIs
      "-unchecked",           // warn about unchecked type parameters
      "-feature",             // warn about misused language features
      "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
      "-Xlint",               // enable handy linter warnings
      "-Xfatal-warnings",     // turn compiler warnings into errors
      "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
    )
  )
