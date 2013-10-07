name := "rokta"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-core" % "4.2.6.Final",
  jdbc,
  cache,
  "org.specs2" %% "specs2" % "2.2.3" % "test"
)     

resolvers ++= Seq("releases"  at "http://oss.sonatype.org/content/repositories/releases")

play.Project.playScalaSettings
