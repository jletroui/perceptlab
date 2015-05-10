name := "perceptlab"

organization := "com.github"

crossPaths := false

scalaVersion := "2.11.6"

mainClass in assembly := Some("perceptlab.Boot")

resolvers += "clojars" at "https://clojars.org/repo"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.6"  % "test"
)
