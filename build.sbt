name := "leafy"

version := "1.0"

scalaVersion := "2.11.7"

assemblyJarName in assembly := "leafy.jar"

scalacOptions ++= Seq(
  "-Xlint",
  "-deprecation",
  "-Xfatal-warnings",
  "-feature"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.14",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.0" % "test"
)
