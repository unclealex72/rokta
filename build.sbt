name := "rokta"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.squeryl" %% "squeryl" % "0.9.5-6",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.typesafe" %% "scalalogging-slf4j" % "1.0.1",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "joda-time" % "joda-time" % "2.2",
  "org.joda" % "joda-convert" % "1.3.1",
  "com.escalatesoft.subcut" %% "subcut" % "2.0",
  "securesocial" %% "securesocial" % "2.1.2",
  // JSON
  "io.argonaut" %% "argonaut" % "6.0.1",
  jdbc,
  cache,
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "org.specs2" %% "specs2" % "2.2.3" % "test"
)     

addCompilerPlugin("com.escalatesoft.subcut" %% "subcut" % "2.0")

resolvers += "releases" at "http://oss.sonatype.org/content/repositories/releases"

resolvers += Resolver.url("sbt-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

play.Project.playScalaSettings

Keys.fork in Test := false
