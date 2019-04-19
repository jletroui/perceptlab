name := "perceptlab"

organization := "com.github"

crossPaths := false

scalaVersion := "2.12.8"

mainClass in assembly := Some("perceptlab.Boot")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5"  % "test"
)
