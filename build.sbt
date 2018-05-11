import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.fedeoasi",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT",
      autoScalaLibrary := false
    )),
    name := "Music Expert",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % scalaVersion.value % Test,
      scalaTest % Test),
    crossPaths := false,
    fork in run := true //Needed to get the MIDI system to load properly when calling `run`
  )
