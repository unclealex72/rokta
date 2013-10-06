name := "rokta"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-core" % "4.2.6.Final",
  jdbc,
  cache
)     

play.Project.playScalaSettings
