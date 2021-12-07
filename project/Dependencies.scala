import sbt._

object Dependencies {
  lazy val cats = "org.typelevel" %% "cats-core" % "2.1.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.8"
  lazy val scalaTestPlus = "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % "test"
}
