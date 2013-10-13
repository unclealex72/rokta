name := "rokta"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.squeryl" %% "squeryl" % "0.9.5-6",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.typesafe" %% "scalalogging-slf4j" % "1.0.1",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "joda-time" % "joda-time" % "2.2",
  "org.joda" % "joda-convert" % "1.3.1",
  // JSON
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.2.2",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.2.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.2",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.2.2",
  jdbc,
  cache,
  "org.specs2" %% "specs2" % "2.2.3" % "test"
)     

resolvers ++= Seq("releases"  at "http://oss.sonatype.org/content/repositories/releases")

play.Project.playScalaSettings
