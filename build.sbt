name := "td2oracle_osusr_7wk_device"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= {
  val circeVersion = "0.12.3"
    Seq(
      "org.apache.kafka" % "kafka-streams" % "2.3.1" % "compile" withSources(),
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2" % "compile" withSources(),
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "compile" withSources(),
      "org.slf4j" % "log4j-over-slf4j" % "1.7.29" % "compile",
      "io.circe" %% "circe-parser" % circeVersion % "compile" withSources(),
      "io.circe" %% "circe-core" % circeVersion % "compile" withSources(),
      "io.circe" %% "circe-generic" % circeVersion % "compile" withSources(),
      "io.circe" %% "circe-generic-extras" % "0.12.2" % "compile" withSources(),
      "org.typelevel" %% "cats-effect" % "2.0.0" % "compile" withSources(),

      "org.scalatest" %% "scalatest" % "3.1.0" % "test",
      "org.apache.kafka" % "kafka-streams-test-utils" % "2.3.1" % Test
    )
}