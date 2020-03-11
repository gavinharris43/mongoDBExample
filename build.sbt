name := "mongoDBExample"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "mysql" % "mysql-connector-java" % "8.0.11",
  "com.h2database" % "h2" % "1.4.197"
)
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0"

