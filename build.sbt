name := "perceptlab"

organization := "com.github"

crossPaths := false

scalaVersion := "2.11.6"

mainClass in assembly := Some("perceptlab.Boot")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.6"  % "test"
