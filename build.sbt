name := "rokta"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.squeryl" %% "squeryl" % "0.9.5-6",
  "com.typesafe" %% "scalalogging-slf4j" % "1.0.1",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  jdbc,
  cache,
  "org.specs2" %% "specs2" % "2.2.3" % "test"
)     

resolvers ++= Seq("releases"  at "http://oss.sonatype.org/content/repositories/releases")

play.Project.playScalaSettings
