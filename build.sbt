import Dependencies._

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cats-sandbox",
    libraryDependencies ++= Seq(
      cats,
      scalaTest % Test,
      "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % "test"
    ),
    scalacOptions ++= Seq(
      "-Xfatal-warnings"
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
